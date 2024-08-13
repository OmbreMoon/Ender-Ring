package com.ombremoon.enderring.client.render.entity.mob;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.model.entity.mob.SpiritJellyfishModel;
import com.ombremoon.enderring.common.init.blocks.BlockInit;
import com.ombremoon.enderring.common.object.entity.ISpiritAsh;
import com.ombremoon.enderring.common.object.entity.mob.creature.SpiritJellyfish;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.List;

public class SpiritJellyfishRenderer extends MobRenderer<SpiritJellyfish, SpiritJellyfishModel<SpiritJellyfish>> {
    public SpiritJellyfishRenderer(EntityRendererProvider.Context pContext, SpiritJellyfishModel<SpiritJellyfish> pModel) {
        super(pContext, pModel, 1f);
    }

    @Override
    public void render(SpiritJellyfish pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
//        VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityTranslucentEmissive(this.getTextureLocation(pEntity)));
//        this.model.renderToBuffer(pMatrixStack, consumer, pPackedLight, OverlayTexture.RED_OVERLAY_V, 1f, 1f, 1f, 0.5f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(SpiritJellyfish pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
//        return RenderType.entityTranslucentEmissive(this.getTextureLocation(pLivingEntity));
        return super.getRenderType(pLivingEntity, pBodyVisible, pTranslucent, pGlowing);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiritJellyfish spiritJellyfish) {
        return new ResourceLocation(Constants.MOD_ID, "textures/entity/mob/spirit_jellyfish.png");
    }

}
