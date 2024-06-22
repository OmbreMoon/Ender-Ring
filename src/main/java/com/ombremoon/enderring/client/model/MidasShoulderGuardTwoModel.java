package com.ombremoon.enderring.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class MidasShoulderGuardTwoModel<T extends LivingEntity> extends AgeableListModel<T> {
	public final ModelPart main;

	public MidasShoulderGuardTwoModel(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0, 0.0F, 0.0F));

		PartDefinition upperSArmor = main.addOrReplaceChild("upperSArmor", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, -0.5F));

		PartDefinition Shoulder = upperSArmor.addOrReplaceChild("Shoulder", CubeListBuilder.create().texOffs(0, 0).addBox(-2.75F, -3.25F, -4.25F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(21, 0).addBox(-3.25F, -3.75F, -3.0F, 4.0F, 6.0F, 4.5F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.75F, 1.25F));

		PartDefinition crown = upperSArmor.addOrReplaceChild("crown", CubeListBuilder.create().texOffs(18, 43).addBox(-2.4142F, -4.9142F, -5.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 43).addBox(-2.6642F, -4.9142F, -5.75F, 1.25F, 4.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(24, 51).addBox(-2.4142F, -3.9142F, -3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.3358F, 2.9142F, 7.25F));

		PartDefinition cube_r1 = crown.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 0).addBox(-0.5F, 0.0F, -0.25F, 1.25F, 0.0F, 7.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2358F, -6.4142F, -3.475F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r2 = crown.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(29, 51).addBox(-0.5F, 0.0239F, -1.001F, 1.25F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2358F, 0.2967F, -9.4595F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r3 = crown.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(30, 19).addBox(-0.5F, 7.75F, -6.0F, 1.25F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2358F, -6.8847F, -2.8245F, -0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r4 = crown.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(29, 53).addBox(-0.5F, 0.0239F, -1.001F, 1.25F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2358F, -6.1642F, -9.5F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r5 = crown.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 12).addBox(-0.625F, 0.0F, -3.0F, 1.25F, 0.0F, 6.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.2108F, -2.9542F, -8.1202F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition cube_r6 = crown.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(30, 12).addBox(1.75F, 0.0F, -3.9F, 1.25F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6137F, -2.058F, -10.4952F, 0.0F, -1.5708F, -1.6144F));

		PartDefinition cube_r7 = crown.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(15, 12).addBox(1.75F, 12.1F, -4.0F, 1.25F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2108F, -11.9532F, -10.4952F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r8 = crown.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 26).addBox(0.375F, 0.0F, -3.5F, 0.75F, 0.0F, 6.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.4186F, -3.2051F, -7.2606F, -1.597F, -1.309F, 3.1416F));

		PartDefinition cube_r9 = crown.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(15, 26).addBox(0.375F, 7.0F, -3.25F, 0.75F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3688F, -6.7063F, -8.0778F, 0.0F, -1.8326F, 0.0F));

		PartDefinition cube_r10 = crown.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(30, 26).addBox(0.375F, 0.0F, -3.25F, 0.75F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2108F, -5.9532F, -8.1202F, 0.0F, -1.8326F, 0.0F));

		PartDefinition cube_r11 = crown.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 19).addBox(1.75F, 0.075F, -4.0F, 1.25F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2108F, -6.0532F, -10.4952F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r12 = crown.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(15, 19).addBox(-0.5F, -0.25F, -6.0F, 1.25F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2358F, -6.4142F, -3.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r13 = crown.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(14, 51).addBox(-0.5F, -3.0F, -3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 51).addBox(-6.45F, -2.5F, -5.0F, 0.5F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 56).addBox(0.25F, -2.5F, -5.0F, 0.5F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 43).addBox(-0.5F, -3.0F, -5.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r14 = crown.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(35, 35).mirror().addBox(-0.5F, -3.25F, -1.75F, 1.25F, 4.25F, 2.5F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.1036F, 0.1036F, -4.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r15 = crown.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(39, 51).addBox(-2.975F, 2.925F, -0.5F, 0.5F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, -4.5F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r16 = crown.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(45, 26).addBox(-0.75F, -1.25F, -3.75F, 1.25F, 2.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, -0.5607F, -4.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r17 = crown.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(24, 35).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 51).addBox(-0.5F, -1.0F, 0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3536F, -0.5607F, -4.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r18 = crown.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(7, 51).addBox(-0.5F, -1.0F, 0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(13, 35).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3536F, -5.2678F, -4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r19 = crown.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 35).addBox(-0.75F, -1.25F, -3.75F, 1.25F, 2.25F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4268F, -5.091F, -4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r20 = crown.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(35, 35).addBox(-0.75F, -3.25F, -1.75F, 1.25F, 4.25F, 2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0732F, -5.7552F, -4.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r21 = crown.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(25, 43).addBox(-0.5F, -3.0F, -1.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 51).addBox(-0.5F, -3.0F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.8284F, -4.0F, 0.0F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.main);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

	}
}