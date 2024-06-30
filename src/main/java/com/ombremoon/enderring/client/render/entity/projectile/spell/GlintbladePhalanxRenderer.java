package com.ombremoon.enderring.client.render.entity.projectile.spell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.client.ModelLocations;
import com.ombremoon.enderring.client.model.entity.projectile.spell.GlintbladePhalanxModel;
import com.ombremoon.enderring.client.model.entity.projectile.spell.GlintstoneArcModel;
import com.ombremoon.enderring.common.object.entity.projectile.spell.GlintbladePhalanxEntity;
import com.ombremoon.enderring.common.object.entity.projectile.spell.GlintstoneArcEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class GlintbladePhalanxRenderer extends EntityRenderer<GlintbladePhalanxEntity> {
    private static final ResourceLocation TEXTURE = CommonClass.customLocation("textures/entity/projectile/spell/glintstone_arc.png");
    private final GlintbladePhalanxModel<GlintbladePhalanxEntity> model;

    public GlintbladePhalanxRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new GlintbladePhalanxModel<>(pContext.bakeLayer(ModelLocations.GLINTSTONE_ARC));
    }

    @Override
    public void render(GlintbladePhalanxEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        pPoseStack.mulPose(Axis.YP.rotationDegrees(-pEntity.yRotO + 180.0F));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-pEntity.xRotO));
        pPoseStack.translate(0.0F, -1.25F, 0.0F);
        this.model.setupAnim(pEntity, pPartialTick, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer consumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GlintbladePhalanxEntity pEntity) {
        return TEXTURE;
    }
}
