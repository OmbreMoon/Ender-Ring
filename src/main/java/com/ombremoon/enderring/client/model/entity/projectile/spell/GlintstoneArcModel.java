package com.ombremoon.enderring.client.model.entity.projectile.spell;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ombremoon.enderring.common.object.entity.projectile.spell.SpellProjectileEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class GlintstoneArcModel<T extends SpellProjectileEntity<?>> extends EntityModel<T> {
	private final ModelPart arc;

	public GlintstoneArcModel(ModelPart root) {
		this.arc = root.getChild("arc");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition arc = partdefinition.addOrReplaceChild("arc", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition vfx = arc.addOrReplaceChild("vfx", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		vfx.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, -11).addBox(0.0F, -12.5F, -5.5F, 0.0F, 25.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, 0.0F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		arc.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

	}
}