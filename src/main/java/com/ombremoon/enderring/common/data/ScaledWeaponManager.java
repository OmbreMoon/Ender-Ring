package com.ombremoon.enderring.common.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.object.PhysicalDamageType;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Scalable;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ScaledWeaponManager extends SimplePreparableReloadListener<Map<Scalable, ScaledWeapon>> {
    private static final int FILE_TYPE_LENGTH_VALUE = ".json".length();
    private static final JsonDeserializer<ResourceLocation> RESOURCE_LOCATION = (json, typeOfT, context) -> new ResourceLocation(json.getAsString());
    private static final JsonDeserializer<ReinforceType> REINFORCE_TYPE = (json, typeOfT, context) -> ReinforceType.getTypeFromLocation(ResourceLocation.tryParse(json.getAsString()));
    private static final JsonDeserializer<AttackElement> ATTACK_ELEMENT = (json, typeOfT, context) -> AttackElement.getElementFromId(json.getAsInt());
    private static final JsonDeserializer<Saturations> SATURATION = (json, typeOfT, context) -> Saturations.getSaturationById(json.getAsInt());
    private static final JsonDeserializer<PhysicalDamageType> PHYSICAL_DAMAGE = (json, typeOfT, context) -> PhysicalDamageType.valueOf(json.getAsString().toUpperCase(Locale.ROOT));
    private static final Gson GSON_INSTANCE = Util.make(() -> {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ResourceLocation.class, RESOURCE_LOCATION);
        builder.registerTypeAdapter(ReinforceType.class, REINFORCE_TYPE);
        builder.registerTypeAdapter(AttackElement.class, ATTACK_ELEMENT);
        builder.registerTypeAdapter(Saturations.class, SATURATION);
        builder.registerTypeAdapter(PhysicalDamageType.class, PHYSICAL_DAMAGE);
        builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
        return builder.create();
    });
    private static List<Scalable> clientWeapons = new ArrayList<>();
    private static ScaledWeaponManager instance;

    private Map<ResourceLocation, ScaledWeapon> registeredWeapons = new HashMap<>();

    @Override
    protected Map<Scalable, ScaledWeapon> prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<Scalable, ScaledWeapon> map = new HashMap<>();
        ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof Scalable).forEach(item -> {
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
                            map.put((Scalable) item, scaledWeapon);
                        } catch (InvalidObjectException e) {
                            Constants.LOG.error("Missing required properties for {}", id);
                            e.printStackTrace();
                        } catch (IOException e) {
                            Constants.LOG.error("Couldn't parse data file {}", id);
                        }
                    });
                });
            }
        });
        return map;
    }

    @Override
    protected void apply(Map<Scalable, ScaledWeapon> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        ImmutableMap.Builder<ResourceLocation, ScaledWeapon> builder = ImmutableMap.builder();
        pObject.forEach(((scalable, weapon) -> {
            builder.put(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey((Item) scalable)), weapon);
            scalable.setWeapon(new Wrapper(weapon));
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
                if (!(item instanceof Scalable))
                    return false;

                ((Scalable) item).setWeapon(new ScaledWeaponManager.Wrapper(entry.getValue()));
                clientWeapons.add((Scalable) item);
            }
            return true;
        }
        return false;
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
    public static void onServerStopped(ServerStoppedEvent event) {
        ScaledWeaponManager.instance = null;
    }

    public static ScaledWeaponManager getInstance() {
        return instance;
    }
}
