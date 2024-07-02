package com.ombremoon.enderring.common.object.world.effect.buildup;

import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class FrostbiteEffect extends BuildUpStatusEffect {
    private static final String FROSTBITE = "15da453f-ad73-4b26-9171-3cbdb51e2c1e";

    public FrostbiteEffect(int pColor) {
        super((i, j) -> true, pColor);
    }

    @Override
    public void applyInstantaneousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity) {
        float f = pLivingEntity instanceof Player ? 0.1F : 0.07F;
        pLivingEntity.hurt(DamageUtil.moddedDamageSource(pLivingEntity.level(), ModDamageTypes.FROSTBITE), pLivingEntity.getMaxHealth() * f + 2.0F);
        this.addAttributeModifier(EpicFightAttributes.STAMINA_REGEN.get(), FROSTBITE, -0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
