package com.ombremoon.enderring.common.object.spell.incantation;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;

public abstract class AbstractIncantation extends AbstractSpell {
    public AbstractIncantation(SpellType<?> spellType, int duration, int requiredFP, int requiredFai) {
        this(spellType, duration, requiredFP, 0, requiredFai);
    }

    public AbstractIncantation(SpellType<?> spellType, int duration, int requiredFP, int requiredInt, int requiredFai) {
        this(spellType, duration, requiredFP, requiredInt, requiredFai, 0);
    }

    public AbstractIncantation(SpellType<?> spellType, int duration, int requiredFP, int requiredInt, int requiredFai, int requiredArc) {
        super(spellType, MagicType.INCANTATION, duration, requiredFP, requiredInt, requiredFai, requiredArc);
    }
}
