package com.ombremoon.enderring.common.object.spell.incantation.firemonk;

import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.Classifications;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.AnimatedSpell;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.compat.epicfight.gameassets.AnimationInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class CatchFlameIncantation extends AnimatedSpell {

    public static Builder<AnimatedSpell> createCatchFlameBuilder() {
        return createSimpleSpellBuilder()
                .setClassification(Classifications.FIRE_MONK)
                .setDuration(INSTANT_SPELL_DURATION)
                .setFPCost(10)
                .setStaminaCost(3)
                .setRequirements(WeaponScaling.FAI, 8)
                .setMotionValue(2.25F)
                .setAnimation(() -> AnimationInit.CATCH_FLAME);
    }

    public CatchFlameIncantation() {
        this(SpellInit.CATCH_FLAME.get(), createCatchFlameBuilder());
    }

    @Override
    protected void onSpellStart(LivingEntityPatch<?> livingEntityPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        this.catalystBoost = 0;
        super.onSpellStart(livingEntityPatch, level, blockPos, weapon);
    }

    public CatchFlameIncantation(SpellType<?> spellType, Builder<AnimatedSpell> builder) {
        super(spellType, builder);
    }

    @Override
    public DamageInstance createDamageInstance() {
        return new DamageInstance(ModDamageTypes.FIRE, this.getScaledDamage());
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }
}
