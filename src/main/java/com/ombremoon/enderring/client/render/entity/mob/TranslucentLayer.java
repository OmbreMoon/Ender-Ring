package com.ombremoon.enderring.client.render.entity.mob;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;

public class TranslucentLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public TranslucentLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityTranslucentEmissive(this.getTextureLocation(pLivingEntity)));
        this.getParentModel().renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.RED_OVERLAY_V, 1f, 1f, 1f, 0.5f);
    }
}
