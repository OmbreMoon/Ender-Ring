package com.ombremoon.enderring.client.model.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ombremoon.enderring.CommonClass;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class TorrentModel<T extends LivingEntity> extends HierarchicalModel<T> {
	private final ModelPart torrent;

	public TorrentModel(ModelPart root) {
		this.torrent = root.getChild("torrent");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torrent = partdefinition.addOrReplaceChild("torrent", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, -4.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tail = torrent.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 17.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, -15.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition neck = torrent.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(40, 0).addBox(-2.5F, -2.5147F, -1.4142F, 5.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, 6.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r1 = neck.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(80, 4).addBox(-2.5F, -1.0F, -7.0F, 4.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -3.5147F, 6.5858F, -0.2182F, 0.0F, 0.0F));

		PartDefinition rein1 = neck.addOrReplaceChild("rein1", CubeListBuilder.create().texOffs(0, 105).addBox(-0.0033F, -2.8855F, -15.1425F, 0.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, 7.8353F, 14.0358F, -0.0085F, -0.0861F, 0.0787F));

		PartDefinition rein2 = neck.addOrReplaceChild("rein2", CubeListBuilder.create().texOffs(0, 105).mirror().addBox(-0.0712F, -3.1408F, -14.9509F, 0.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.9F, 8.0853F, 13.8358F, -0.0067F, 0.0783F, -0.07F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(72, 25).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(72, 38).addBox(-2.0F, -1.0F, 6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5147F, 14.5858F, -1.309F, 0.0F, 0.0F));

		PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.75F, -1.0F, 2.3126F, 0.0F, 0.0F));

		PartDefinition ear2 = ears.addOrReplaceChild("ear2", CubeListBuilder.create().texOffs(89, 30).mirror().addBox(-5.0F, -1.0F, -3.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition ear1 = ears.addOrReplaceChild("ear1", CubeListBuilder.create().texOffs(89, 30).addBox(0.0F, -1.0F, -3.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

		PartDefinition horns = head.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offset(3.0F, -0.75F, 0.5F));

		PartDefinition horn1 = horns.addOrReplaceChild("horn1", CubeListBuilder.create().texOffs(88, 38).addBox(0.0F, -1.0F, -2.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition bone = horn1.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(88, 42).addBox(0.0F, -0.5F, -1.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 0.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition horn2 = horns.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(88, 38).mirror().addBox(-7.0F, -1.0F, -2.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition bone3 = horn2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(88, 42).mirror().addBox(-7.0F, -0.5F, -1.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 0.0F, -0.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition leg1 = torrent.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 37).addBox(1.0F, -21.0F, -67.0F, 4.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, 0.0F, 52.0F));

		PartDefinition bone6 = leg1.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(0, 55).addBox(0.75F, -12.0F, -69.0F, 4.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg2 = torrent.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(1.0F, -21.0F, -67.0F, 4.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.1F, 0.0F, 52.0F));

		PartDefinition bone2 = leg2.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 55).mirror().addBox(1.25F, -12.0F, -69.0F, 4.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm1 = torrent.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(22, 37).addBox(1.0F, -18.0F, -48.0F, 4.0F, 18.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(22, 37).mirror().addBox(-5.0F, -18.0F, -48.0F, 4.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 52.0F));

		PartDefinition body = torrent.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -27.0F, -65.0F, 8.0F, 13.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 52.0F));

		PartDefinition bone4 = body.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 84).addBox(-4.5F, -22.0F, -64.0F, 9.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(30, 109).addBox(-5.0F, -28.0F, -59.0F, 10.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 106).addBox(-5.5F, -30.5F, -68.0F, 11.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(33, 84).mirror().addBox(-7.5F, -25.5F, -66.0F, 4.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(33, 84).addBox(3.5F, -25.5F, -66.0F, 4.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		torrent.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.torrent;
	}
}