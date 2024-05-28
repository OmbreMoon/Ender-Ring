package com.ombremoon.enderring.common.object.spell.incantation.twofingers;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SimpleAnimationSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.spell.incantation.HealSpell;
import net.minecraft.resources.ResourceLocation;

public class UrgentHealIncantation extends HealSpell {
    //TODO: CHANGE
    protected static ResourceLocation HEAL_ANIMATION = CommonClass.customLocation("biped/spell/heal");

    public static SimpleAnimationSpell.Builder createUrgentHealBuilder() {
        return createSimpleSpellBuilder().setMagicType(MagicType.INCANTATION).setFPCost(16).setDuration(76).setRequirements(WeaponScaling.FAI, 8).setMotionValue(1.15F).setAnimations(HEAL_ANIMATION);
    }

    public UrgentHealIncantation(Builder builder) {
        this(SpellInit.URGENT_HEAL.get(), builder);
    }

    public UrgentHealIncantation(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
    }
}
