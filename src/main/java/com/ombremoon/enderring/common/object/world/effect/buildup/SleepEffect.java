package com.ombremoon.enderring.common.object.world.effect.buildup;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

public class SleepEffect extends BuildUpStatusEffect {
    public SleepEffect(int pColor) {
        super((a, b) -> true, pColor);
    }

    @Override
    public void applyInstantaneousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, ScaledWeapon weapon, SpellType<?> spellType, int pAmplifier, double pHealth) {
        float time = 60.0F;
        if (pLivingEntity instanceof Player player) {
            time = 1.0F;
            EntityStatusUtil.consumeFP(player, (float) (EntityStatusUtil.getMaxFP(player) * 0.1F + 30.0F), null, true);
        }

        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(pLivingEntity, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            livingEntityPatch.applyStun(StunType.HOLD, time);
        }
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
