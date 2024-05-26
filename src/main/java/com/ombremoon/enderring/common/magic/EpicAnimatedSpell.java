package com.ombremoon.enderring.common.magic;

public abstract class EpicAnimatedSpell extends AbstractSpell {
    public EpicAnimatedSpell(SpellType<?> spellType, MagicType magicType, int duration, int requiredFP, int requiredInt, int requiredFai, int requiredArc) {
        super(spellType, magicType, duration, requiredFP, requiredInt, requiredFai, requiredArc);
    }
}
