package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.ChannelledSpell;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;

public class CometAzurSorcery extends ChannelledSpell {

    @SuppressWarnings("unchecked")
    public static Builder<CometAzurSorcery> createCometAzurSorcery() {
        return createChannelledSpellBuilder()
                .setMagicType(MagicType.SORCERY)
                .setFPCost(40)
                .setFPTickCost(10)
                .setRequirements(WeaponScaling.INT, 60);
    }

    public CometAzurSorcery() {
        super(SpellInit.COMET_AZUR.get(), createCometAzurSorcery());
    }

    public CometAzurSorcery(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
    }

    @Override
    protected DamageInstance createDamageInstance() {
        return new DamageInstance(ModDamageTypes.MAGICAL, this.getScaledDamage());
    }

    @Override
    public int getCastTime() {
        return 10;
    }
}
