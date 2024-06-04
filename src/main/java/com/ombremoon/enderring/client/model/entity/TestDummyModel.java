package com.ombremoon.enderring.client.model.entity;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class TestDummyModel<T extends LivingEntity> extends HierarchicalModel<T> {
	private final ModelPart excalibur;

	public TestDummyModel(ModelPart root) {
		this.excalibur = root.getChild("excalibur");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition excalibur = partdefinition.addOrReplaceChild("excalibur", CubeListBuilder.create(), PartPose.offset(4.0F, 17.0F, 0.0F));

		PartDefinition left_foot = excalibur.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(27, 0).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-2.0F, -5.0F, 0.0F));

		PartDefinition right_foot = excalibur.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(27, 0).mirror().addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(-6.0F, -5.0F, 0.0F));

		PartDefinition body = excalibur.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(-4.0F, -17.0F, 0.0F));

		PartDefinition left_arm = excalibur.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 18).addBox(0.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition cube_r1 = left_arm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 34).addBox(-3.25F, -5.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.75F, 8.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_arm = excalibur.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 18).mirror().addBox(-4.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)).mirror(false), PartPose.offset(-8.0F, -15.0F, 0.0F));

		PartDefinition cube_r2 = right_arm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 34).mirror().addBox(-0.75F, -5.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.75F, 8.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head = excalibur.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -9.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.275F)), PartPose.offset(-4.0F, -17.0F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(33, 27).addBox(-2.0F, -2.0F, -5.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -6.5F, -4.5F, -1.0472F, 0.0F, 0.0F));

		PartDefinition left_leg = excalibur.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 34).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(-2.0F, -5.0F, 0.0F));

		PartDefinition cube_r4 = left_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(36, 8).addBox(-1.0F, -3.0F, -2.5F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition right_leg = excalibur.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.275F)).mirror(false), PartPose.offset(-6.0F, -5.0F, 0.0F));

		PartDefinition cube_r5 = right_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(36, 8).mirror().addBox(-1.0F, -3.0F, -2.5F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 5.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		excalibur.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.excalibur;
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

	}
}