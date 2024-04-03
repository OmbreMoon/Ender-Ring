package com.ombremoon.enderring.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class ERArmorModel<T extends LivingEntity> extends HumanoidModel<T> {

	public ERArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBlueClothLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.275F))
		.texOffs(0, 0).addBox(-4.5F, -9.0F, -4.0F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 30).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(27, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 5).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_arm_shoulder_pad = left_arm.addOrReplaceChild("left_arm_shoulder_pad", CubeListBuilder.create().texOffs(51, 0).addBox(1.0F, -1.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(-2.0F, -1.5F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 14).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_arm_shoulder_pad = right_arm.addOrReplaceChild("right_arm_shoulder_pad", CubeListBuilder.create().texOffs(48, 28).addBox(-5.0F, -1.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(2.0F, -1.5F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 50).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public static LayerDefinition createFakeBlueClothLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.275F))
		.texOffs(0, 0).addBox(-4.5F, -9.0F, -4.0F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 30).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(27, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 5).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_arm_shoulder_pad = left_arm.addOrReplaceChild("left_arm_shoulder_pad", CubeListBuilder.create().texOffs(51, 0).addBox(1.0F, -1.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(-2.0F, -1.5F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 14).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_arm_shoulder_pad = right_arm.addOrReplaceChild("right_arm_shoulder_pad", CubeListBuilder.create().texOffs(48, 28).addBox(-5.0F, -1.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(2.0F, -1.5F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(48, 42).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition left_leg_cloak = left_leg.addOrReplaceChild("left_leg_cloak", CubeListBuilder.create().texOffs(32, 46).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 42).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition right_leg_cloak = right_leg.addOrReplaceChild("right_leg_cloak", CubeListBuilder.create().texOffs(0, 34).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.275F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}