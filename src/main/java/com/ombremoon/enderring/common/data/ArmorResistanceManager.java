package com.ombremoon.enderring.common.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Resistance;
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
public class ArmorResistanceManager extends SimplePreparableReloadListener<Map<Resistance, ArmorResistance>> {
    private static final int FILE_TYPE_LENGTH_VALUE = ".json".length();
    private static final Gson GSON = new Gson();
    private static ArmorResistanceManager instance;

    private Map<ResourceLocation, ArmorResistance> registeredArmors = new HashMap<>();

    @Override
    protected Map<Resistance, ArmorResistance> prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<Resistance, ArmorResistance> map = new HashMap<>();
        ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof Resistance).forEach(item -> {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
            if (id != null) {
                List<ResourceLocation> locations = new ArrayList<>(pResourceManager.listResources("armor_resistances", location -> location.getPath().endsWith(id.getPath() + ".json")).keySet());
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
                            ArmorResistance armorResistance = GsonHelper.fromJson(GSON, reader, ArmorResistance.class);
                            map.put((Resistance) item, armorResistance);
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
    protected void apply(Map<Resistance, ArmorResistance> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        ImmutableMap.Builder<ResourceLocation, ArmorResistance> builder = ImmutableMap.builder();
        pObject.forEach(((resistance, armorResistance) -> {
            builder.put(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey((Item) resistance)), armorResistance);
            resistance.setWeapon(new ArmorResistanceManager.Wrapper(armorResistance));
        }));
        this.registeredArmors = builder.build();
    }

    public static class Wrapper {
        private ArmorResistance armorResistance;

        public Wrapper(ArmorResistance armorResistance) {
            this.armorResistance = armorResistance;
        }

        public ArmorResistance getArmorResistance() {
            return this.armorResistance;
        }
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        ArmorResistanceManager manager = new ArmorResistanceManager();
        event.addListener(manager);
        ArmorResistanceManager.instance = manager;
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ArmorResistanceManager.instance = null;
    }

    public static ArmorResistanceManager getInstance() {
        return instance;
    }
}
