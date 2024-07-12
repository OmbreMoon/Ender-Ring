package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.Classifications;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.ChanneledSpell;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class CometAzurSorcery extends ChanneledSpell {

    public static Builder<ChanneledSpell> createCometAzurSorcery() {
        return createChannelledSpellBuilder()
                .setClassification(Classifications.PRIMEVAL)
                .setFPCost(40)
                .setFPTickCost(10)
                .setRequirements(WeaponScaling.INT, 60);
    }

    public CometAzurSorcery() {
        this(SpellInit.COMET_AZUR.get(), createCometAzurSorcery());
    }

    public CometAzurSorcery(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
    }

    @Override
    protected void onSpellTick(LivingEntityPatch<?> livingEntityPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellTick(livingEntityPatch, level, blockPos, weapon);
        livingEntityPatch.getOriginal().sendSystemMessage(Component.literal("BEAM!!!!"));
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
