package com.ombremoon.enderring.common.object.entity.npc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class NPCData {
    public static final Codec<NPCData> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Merchant.CODEC.fieldOf("merchant").orElseGet(() -> {
            return Merchant.NONE;
        }).forGetter(data -> {
            return data.merchant;
        })).apply(instance, NPCData::new);
    });
    private final Merchant merchant;

    public NPCData(Merchant merchant) {
        this.merchant = merchant;
    }

    public Merchant getMerchant() {
        return this.merchant;
    }

    public NPCData setMerchant(Merchant merchant) {
        return new NPCData(merchant);
    }
}
