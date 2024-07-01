package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.ChanneledSpell;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class CometAzurSorcery extends ChanneledSpell {

    public static Builder<ChanneledSpell> createCometAzurSorcery() {
        return createChannelledSpellBuilder()
                .setMagicType(MagicType.SORCERY)
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
    protected void onSpellTick(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellTick(playerPatch, level, blockPos, weapon);
        playerPatch.getOriginal().sendSystemMessage(Component.literal("BEAM!!!!"));
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
