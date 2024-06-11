package com.ombremoon.enderring.common.object.entity.npc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.ombremoon.enderring.CommonClass;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record Merchant(ResourceLocation resourceLocation) {
    private static final Map<ResourceLocation, Merchant> merchantMap = new HashMap<>();
    public static final Codec<Merchant> CODEC = ResourceLocation.CODEC.flatXmap(location -> {
        return Optional.ofNullable(getMerchantFromLocation(location)).map(DataResult::success).orElseGet(() -> {
            return DataResult.error(() -> {
                return "Unknown resource key: " + location;
            });
        });
    }, merchant -> {
        return Optional.ofNullable(merchant.resourceLocation()).map(DataResult::success).orElseGet(() -> {
            return DataResult.error(() -> {
                return "Unknown merchant: " + merchant;
            });
        });
    });

    public static final Merchant NONE = registerMerchant("none");
    public static final Merchant TEST = registerMerchant("test");

    //TODO: ADD EVENT
    static {
        registerMerchant(NONE);
        registerMerchant(TEST);
    }

    public static void registerMerchant(Merchant merchant) {
        merchantMap.putIfAbsent(merchant.resourceLocation(), merchant);
    }

    private static Merchant registerMerchant(String name) {
        return new Merchant(CommonClass.customLocation(name));
    }

    public static Merchant getMerchantFromLocation(ResourceLocation resourceLocation) {
        return merchantMap.getOrDefault(resourceLocation, NONE);
    }
}
