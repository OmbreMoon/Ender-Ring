package com.ombremoon.enderring.common.object.spell.incantation.firemonk;

import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.AnimatedSpell;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.compat.epicfight.gameassets.AnimationInit;

public class CatchFlameIncantation extends AnimatedSpell {

    public static Builder<AnimatedSpell> createCatchFlameBuilder() {
        return createSimpleSpellBuilder()
                .setMagicType(MagicType.INCANTATION)
                .setDuration(INSTANT_SPELL_DURATION)
                .setFPCost(10)
                .setRequirements(WeaponScaling.FAI, 8)
                .setMotionValue(2.25F)
                .setAnimation(() -> AnimationInit.CATCH_FLAME);
    }

    public CatchFlameIncantation() {
        this(SpellInit.CATCH_FLAME.get(), createCatchFlameBuilder());
    }

    public CatchFlameIncantation(SpellType<?> spellType, Builder<? extends AbstractSpell> builder) {
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
