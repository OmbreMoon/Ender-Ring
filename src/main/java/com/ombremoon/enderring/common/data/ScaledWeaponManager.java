package com.ombremoon.enderring.common.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ReinforceType;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ScaledWeaponManager extends SimplePreparableReloadListener<Map<AbstractWeapon, ScaledWeapon>> {
    private static final int FILE_TYPE_LENGTH_VALUE = ".json".length();
    private static final JsonDeserializer<ResourceLocation> RESOURCE_LOCATION = (json, typeOfT, context) -> new ResourceLocation(json.getAsString());
    private static final JsonDeserializer<ReinforceType> REINFORCE_TYPE = (json, typeOfT, context) -> ReinforceType.getTypeFromLocation(ResourceLocation.tryParse(json.getAsString()));
    private static final Gson GSON_INSTANCE = Util.make(() -> {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ResourceLocation.class, RESOURCE_LOCATION);
        builder.registerTypeAdapter(ReinforceType.class, REINFORCE_TYPE);
        builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
        return builder.create();
    });
    private static List<AbstractWeapon> clientWeapons = new ArrayList<>();
    private static ScaledWeaponManager instance;

    private Map<ResourceLocation, ScaledWeapon> registeredWeapons = new HashMap<>();

    @Override
    protected Map<AbstractWeapon, ScaledWeapon> prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<AbstractWeapon, ScaledWeapon> map = new HashMap<>();
        ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof AbstractWeapon).forEach(item -> {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
            if (id != null) {
                List<ResourceLocation> locations = new ArrayList<>(pResourceManager.listResources("scaled_weapons", location -> location.getPath().endsWith(id.getPath() + ".json")).keySet());
                locations.sort((loc1, loc2) -> {
                    if (loc1.getNamespace().equals(loc2.getNamespace())) return 0;
                    return loc2.getNamespace().equals(Constants.MOD_ID) ? 1 : -1;
                });
                locations.forEach(resourceLocation -> {
                    String name = resourceLocation.getPath().substring(0, resourceLocation.getPath().length() - FILE_TYPE_LENGTH_VALUE);
                    String[] splitName = name.split("/");

                    if (!id.getPath().equals(splitName[splitName.length - 1]))
                        return;

                    if (!id.getNamespace().equals(resourceLocation.getNamespace()))
                        return;

                    pResourceManager.getResource(resourceLocation).ifPresent(resource -> {
                        try(Reader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                            ScaledWeapon scaledWeapon = GsonHelper.fromJson(GSON_INSTANCE, reader, ScaledWeapon.class);
                            map.put((AbstractWeapon) item, scaledWeapon);
                            /*if (scaledWeapon != null && isValidObject(scaledWeapon)) {
                                map.put((AbstractWeapon) item, scaledWeapon);
                            } else {
                                Constants.LOG.error("Couldn't load data file {} as it is missing or malformed. Using default weapon data", id);
                                map.putIfAbsent((AbstractWeapon) item, new ScaledWeapon());
                            }*/
                        } catch (InvalidObjectException e) {
                            Constants.LOG.error("Missing required properties for {}", id);
                            e.printStackTrace();
                        } catch (IOException e) {
                            Constants.LOG.error("Couldn't parse data file {}", id);
                        }/* catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }*/
                    });
                });
            }
        });
        return map;
    }

    @Override
    protected void apply(Map<AbstractWeapon, ScaledWeapon> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        ImmutableMap.Builder<ResourceLocation, ScaledWeapon> builder = ImmutableMap.builder();
        pObject.forEach(((abstractWeapon, weapon) -> {
            builder.put(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(abstractWeapon)), weapon);
            abstractWeapon.setWeapon(new Wrapper(weapon));
        }));
        this.registeredWeapons = builder.build();
    }

    public void writeRegisteredWeapons(FriendlyByteBuf buf) {
        buf.writeVarInt(this.registeredWeapons.size());
        this.registeredWeapons.forEach(((resourceLocation, weapon) -> {
            buf.writeResourceLocation(resourceLocation);
            buf.writeNbt(weapon.serializeNBT());
        }));
    }

    public static ImmutableMap<ResourceLocation, ScaledWeapon> readRegisteredWeapons(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        if (size > 0) {
            ImmutableMap.Builder<ResourceLocation, ScaledWeapon> builder = ImmutableMap.builder();
            for (int i = 0; i < size; i++) {
                ResourceLocation resourceLocation = buf.readResourceLocation();
                ScaledWeapon weapon = ScaledWeapon.create(buf.readNbt());
                builder.put(resourceLocation, weapon);
            }
            return builder.build();
        }
        return ImmutableMap.of();
    }

    public static boolean updateRegisteredWeapons(Map<ResourceLocation, ScaledWeapon> registeredWeapons) {
        clientWeapons.clear();
        if (registeredWeapons != null) {
            for (Map.Entry<ResourceLocation, ScaledWeapon> entry : registeredWeapons.entrySet()) {
                Item item = ForgeRegistries.ITEMS.getValue(entry.getKey());
                if (!(item instanceof AbstractWeapon))
                    return false;

                ((AbstractWeapon) item).setWeapon(new ScaledWeaponManager.Wrapper(entry.getValue()));
                clientWeapons.add((AbstractWeapon) item);
            }
            return true;
        }
        return false;
    }

    public Map<ResourceLocation, ScaledWeapon> getRegisteredWeapons() {
        return this.registeredWeapons;
    }

    public static <T> boolean isValidObject(T t) throws IllegalAccessException, InvalidObjectException {
        Field[] fields = t.getClass().getDeclaredFields();
        for(Field field : fields) {

            field.setAccessible(true);

            if(field.get(t) == null) {
                throw new InvalidObjectException("Missing required property: " + field.getName());
            }

            if(!field.getType().isPrimitive() && field.getType() != String.class && !field.getType().isEnum()) {
                return isValidObject(field.get(t));
            }
        }
        return true;
    }

    public static class Wrapper {
        private ScaledWeapon weapon;

        public Wrapper(ScaledWeapon weapon) {
            this.weapon = weapon;
        }

        public ScaledWeapon getWeapon() {
            return this.weapon;
        }
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        ScaledWeaponManager manager = new ScaledWeaponManager();
        event.addListener(manager);
        ScaledWeaponManager.instance = manager;
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide) return;
        if (!(event.getEntity() instanceof Player)) return;
        ModNetworking.getInstance().updateWeaponData();
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            ModNetworking.getInstance().updateWeaponData();
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ScaledWeaponManager.instance = null;
    }

    public static ScaledWeaponManager getInstance() {
        return instance;
    }
}
