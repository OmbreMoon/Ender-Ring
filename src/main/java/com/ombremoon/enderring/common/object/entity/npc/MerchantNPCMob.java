package com.ombremoon.enderring.common.object.entity.npc;

import com.ombremoon.enderring.common.object.entity.NPCMob;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class MerchantNPCMob extends NPCMob<MerchantNPCMob> {

    protected MerchantNPCMob(EntityType<? extends MerchantNPCMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!this.level().isClientSide && !this.isAngryAt(pPlayer)) {
//            this.startTrading(pPlayer);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    private void startTrading(Player player) {
        //Open Trade Screen

    }

}
