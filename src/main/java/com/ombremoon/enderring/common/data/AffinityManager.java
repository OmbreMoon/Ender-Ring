package com.ombremoon.enderring.common.data;

import com.google.common.collect.ImmutableMap;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Scalable;
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
import java.nio.charset.StandardCharsets;
import java.util.*;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class AffinityManager extends SimplePreparableReloadListener<Map<Scalable, Map<ReinforceType, ScaledWeapon>>> {
    private static final int FILE_TYPE_LENGTH_VALUE = ".json".length();
    private static AffinityManager instance;

    @Override
    protected Map<Scalable, Map<ReinforceType, ScaledWeapon>> prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<Scalable, Map<ReinforceType, ScaledWeapon>> map = new HashMap<>();
        Map<ReinforceType, ScaledWeapon> map1 = new HashMap<>();
        ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof Scalable).forEach(item -> {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
            if (id != null) {
                ReinforceType.getRegistry().values().forEach(reinforceType -> {
                    List<ResourceLocation> locations = new ArrayList<>(pResourceManager.listResources(String.format("affinities/%s", reinforceType.getName()), location -> location.getPath().endsWith(id.getPath() + ".json")).keySet());
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
                                ScaledWeapon scaledWeapon = GsonHelper.fromJson(ScaledWeaponManager.GSON_INSTANCE, reader, ScaledWeapon.class);
                                map1.put(reinforceType, scaledWeapon);
                                map.put((Scalable) item, map1);
                            } catch (InvalidObjectException e) {
                                Constants.LOG.error("Missing required properties for {}", id);
                                e.printStackTrace();
                            } catch (IOException e) {
                                Constants.LOG.error("Couldn't parse data file {}", id);
                            }
                        });
                    });
                });
            }
        });
        return map;
    }

    @Override
    protected void apply(Map<Scalable, Map<ReinforceType, ScaledWeapon>> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        ImmutableMap.Builder<ResourceLocation, Map<ReinforceType, ScaledWeapon>> builder = ImmutableMap.builder();
        pObject.forEach(((scalable, map) -> {
            builder.put(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey((Item) scalable)), map);
            map.forEach(((reinforceType, scaledWeapon) -> {
                scalable.setAffinities(reinforceType, new ScaledWeaponManager.Wrapper(scaledWeapon));
            }));
        }));
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        AffinityManager manager = new AffinityManager();
        event.addListener(manager);
        AffinityManager.instance = manager;
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        AffinityManager.instance = null;
    }

    public static AffinityManager getInstance() {
        return instance;
    }
}
