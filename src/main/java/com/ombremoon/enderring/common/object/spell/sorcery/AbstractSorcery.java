package com.ombremoon.enderring.common.object.spell.sorcery;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;

public abstract class AbstractSorcery extends AbstractSpell {
    public AbstractSorcery(SpellType<?> spellType, int duration, int requiredFP, int requiredInt) {
        this(spellType, duration, requiredFP, requiredInt, 0);
    }

    public AbstractSorcery(SpellType<?> spellType, int duration, int requiredFP, int requiredInt, int requiredFai) {
        this(spellType, duration, requiredFP, requiredInt, requiredFai, 0);
    }

    public AbstractSorcery(SpellType<?> spellType, int duration, int requiredFP, int requiredInt, int requiredFai, int requiredArc) {
        super(spellType, MagicType.SORCERY, duration, requiredFP, requiredInt, requiredFai, requiredArc);
    }
}
