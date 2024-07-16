package com.ombremoon.enderring.common.object.block;

import com.mojang.datafixers.util.Either;
import com.ombremoon.enderring.client.gui.screen.GraceSiteScreen;
import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.common.object.block.entity.GraceSiteBlockEntity;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.EntityStatusUtil;
import com.ombremoon.enderring.util.FlaskUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.List;

public class GraceSiteBlock extends BaseEntityBlock implements EntityBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    public GraceSiteBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.CONSUME;
        } else {
            ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
            Vec3 vec3 = Vec3.atBottomCenterOf(pPos);
            List<Monster> list = pLevel.getEntitiesOfClass(Monster.class, new AABB(vec3.x() - 8.0D, vec3.y() - 5.0D, vec3.z() - 8.0D, vec3.x() + 8.0D, vec3.y() + 5.0D, vec3.z() + 8.0D));
            if (!list.isEmpty()) {
                pPlayer.displayClientMessage(Player.BedSleepingProblem.NOT_SAFE.getMessage(), true);
                return InteractionResult.PASS;
            } else {
                PlayerStatus playerStatus = EntityStatusUtil.getPlayerStatus(pPlayer);
                FlaskUtil.resetFlaskCooldowns(pPlayer);
                playerStatus.setFP(EntityStatusUtil.getMaxFP(pPlayer));
                pPlayer.heal(pPlayer.getMaxHealth());
                pPlayer.getFoodData().setFoodLevel(20);
                for (MobEffect mobEffect : ForgeRegistries.MOB_EFFECTS.getValues()) {
                    if (mobEffect.getCategory() == MobEffectCategory.HARMFUL) {
                        pPlayer.removeEffect(mobEffect);
                    }
                }
                serverPlayer.setRespawnPosition(pLevel.dimension(), pPos.north(), pPlayer.getYRot(), true, true);
                ModNetworking.openGraceSiteScreen(Component.literal("Grace"), (ServerPlayer) pPlayer);
                return InteractionResult.SUCCESS;
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GraceSiteBlockEntity(pPos, pState);
    }
}
