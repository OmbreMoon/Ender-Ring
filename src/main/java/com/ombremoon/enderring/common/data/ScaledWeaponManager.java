package com.ombremoon.enderring.common.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ScaledWeaponManager extends SimplePreparableReloadListener<Map<AbstractWeapon, ScaledWeapon>> {
    private static final int FILE_TYPE_LENGTH_VALUE = ".json".length();
    private static final JsonDeserializer<ResourceLocation> RESOURCE_LOCATION = (json, typeOfT, context) -> new ResourceLocation(json.getAsString());
    private static final Gson GSON_INSTANCE = Util.make(() -> {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ResourceLocation.class, RESOURCE_LOCATION);
        return builder.create();
    });
    private static ScaledWeaponManager instance;

    private Map<ResourceLocation, ScaledWeapon> registeredWeapons = new HashMap<>();

    @Override
    protected Map<AbstractWeapon, ScaledWeapon> prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<AbstractWeapon, ScaledWeapon> map = new HashMap<>();
        ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof AbstractWeapon).forEach(item -> {
            ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(item);
            if (resourceLocation != null) {
                List<ResourceLocation> locations = new ArrayList<>(pResourceManager.listResources("scaled_weapons", location -> location.getPath().endsWith(resourceLocation.getPath() + ".json")).keySet());
                locations.sort((loc1, loc2) -> {
                    if (loc1.getNamespace().equals(loc2.getNamespace())) return 0;
                    return loc2.getNamespace().equals(Constants.MOD_ID) ? 1 : -1;
                });
                locations.forEach(id -> {
                    String name = id.getPath().substring(0, id.getPath().length() - FILE_TYPE_LENGTH_VALUE);
                    String[] splitName = name.split("/");

                    if (!resourceLocation.getPath().equals(splitName[splitName.length - 1]))
                        return;

                    if (!resourceLocation.getNamespace().equals(id.getNamespace()))
                        return;

                    pResourceManager.getResource(id).ifPresent(resource -> {
                        try (Reader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                            ScaledWeapon scaledWeapon = GsonHelper.fromJson(GSON_INSTANCE, reader, ScaledWeapon.class);
                            if (scaledWeapon != null) {
                                map.put((AbstractWeapon) item, scaledWeapon);
                            } else {
                                Constants.LOG.error("Couldn't load data file {} as it is missing or malformed. Using default weapon data", id);
                            }
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
    protected void apply(Map<AbstractWeapon, ScaledWeapon> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        ImmutableMap.Builder<ResourceLocation, ScaledWeapon> builder = ImmutableMap.builder();
        pObject.forEach(((abstractWeapon, weapon) -> {
            builder.put(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(abstractWeapon)), weapon);
            abstractWeapon.setWeapon(new Wrapper(weapon));
        }));
        this.registeredWeapons = builder.build();
    }

    public Map<ResourceLocation, ScaledWeapon> getRegisteredWeapons() {
        return this.registeredWeapons;
    }

    public static class Wrapper {
        private ScaledWeapon weapon;

        private Wrapper(ScaledWeapon weapon) {
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
    public static void onServerStopped(ServerStartedEvent event) {
        ScaledWeaponManager.instance = null;
    }

    public static ScaledWeaponManager getInstance() {
        return instance;
    }
}
