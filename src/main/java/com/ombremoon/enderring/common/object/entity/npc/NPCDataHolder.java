package com.ombremoon.enderring.common.object.entity.npc;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.Constants;
import net.minecraft.world.entity.VariantHolder;

public interface NPCDataHolder extends VariantHolder<MerchantVariant> {
    NPCData getNPCData();

    void setNPCData(NPCData data);

    default MerchantVariant getVariant() {
        return null;
    }

    default void setVariant(MerchantVariant merchantVariant) {
        Constants.LOG.info("Test");
    }
}
