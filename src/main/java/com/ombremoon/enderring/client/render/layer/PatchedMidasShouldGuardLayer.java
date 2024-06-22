package com.ombremoon.enderring.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class PatchedMidasShouldGuardLayer<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends HumanoidModel<E>, AM extends HumanoidMesh> extends PatchedLayer<E, T, M, MidasShoulderGuardLayer<E, M>, AM> {
    public PatchedMidasShouldGuardLayer() {
        super(null);
    }

    @Override
    protected void renderLayer(T entityPatch, E livingEntity, MidasShoulderGuardLayer<E, M> layer, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, OpenMatrix4f[] openMatrix4fs, float bob, float yRot, float xRot, float partialTicks) {
        if (layer.shouldRender(livingEntity.getItemBySlot(EquipmentSlot.MAINHAND), livingEntity)) {
            layer.getParentModel().copyPropertiesTo(layer.shoulderGuardModel);
            OpenMatrix4f matrix4f = new OpenMatrix4f();
            HumanoidArmature armature = (HumanoidArmature) entityPatch.getArmature();
            OpenMatrix4f transform = openMatrix4fs[armature.armR.getId()];
            matrix4f.translate(new Vec3f(-0.37F, -0.1F, 0.063F)).rotate(-23 * Mth.DEG_TO_RAD, Vec3f.X_AXIS).rotate(180 * Mth.DEG_TO_RAD, Vec3f.Y_AXIS).mulFront(transform);
            poseStack.pushPose();
            OpenMatrix4f transpose = OpenMatrix4f.transpose(matrix4f, null);
            MathUtils.translateStack(poseStack, matrix4f);
            MathUtils.rotateStack(poseStack, transpose);
            MathUtils.scaleStack(poseStack, transpose);
            layer.render(poseStack, multiBufferSource, packedLight, livingEntity, livingEntity.walkAnimation.position(), livingEntity.walkAnimation.speed(), partialTicks, bob, yRot, xRot);
            poseStack.popPose();
        }
    }
}
