package com.ombremoon.enderring.client.model.entity.mob;
// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SpiritJellyfishModel<T extends Entity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "spirit_jellyfish"), "main");
    private final ModelPart Root;



    public SpiritJellyfishModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.Root = root.getChild("Root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Base = Root.addOrReplaceChild("Base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftTentecles = Base.addOrReplaceChild("leftTentecles", CubeListBuilder.create(), PartPose.offset(0.0F, -36.0F, -3.0F));

        PartDefinition leftFrontTentacles = leftTentecles.addOrReplaceChild("leftFrontTentacles", CubeListBuilder.create().texOffs(100, 142).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition leftFrontTentaclesS2 = leftFrontTentacles.addOrReplaceChild("leftFrontTentaclesS2", CubeListBuilder.create().texOffs(20, 142).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 15.0F, 0.5F));

        PartDefinition leftFrontTentaclesS3 = leftFrontTentaclesS2.addOrReplaceChild("leftFrontTentaclesS3", CubeListBuilder.create().texOffs(0, 142).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(-0.02F)), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition leftBackTentacles = leftTentecles.addOrReplaceChild("leftBackTentacles", CubeListBuilder.create().texOffs(80, 142).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 5.0F));

        PartDefinition leftFrontTentaclesS4 = leftBackTentacles.addOrReplaceChild("leftFrontTentaclesS4", CubeListBuilder.create().texOffs(139, 137).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 15.0F, 0.5F));

        PartDefinition leftFrontTentaclesS5 = leftFrontTentaclesS4.addOrReplaceChild("leftFrontTentaclesS5", CubeListBuilder.create().texOffs(126, 61).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(-0.02F)), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition rightTentecles = Base.addOrReplaceChild("rightTentecles", CubeListBuilder.create(), PartPose.offset(0.0F, -36.0F, -3.0F));

        PartDefinition rightFrontTentacles = rightTentecles.addOrReplaceChild("rightFrontTentacles", CubeListBuilder.create().texOffs(100, 142).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition rightFrontTentaclesS2 = rightFrontTentacles.addOrReplaceChild("rightFrontTentaclesS2", CubeListBuilder.create().texOffs(20, 142).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 15.0F, 0.5F));

        PartDefinition rightFrontTentaclesS3 = rightFrontTentaclesS2.addOrReplaceChild("rightFrontTentaclesS3", CubeListBuilder.create().texOffs(0, 142).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(-0.02F)), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition rightBackTentacles = rightTentecles.addOrReplaceChild("rightBackTentacles", CubeListBuilder.create().texOffs(80, 142).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 5.0F));

        PartDefinition rightFrontTentaclesS4 = rightBackTentacles.addOrReplaceChild("rightFrontTentaclesS4", CubeListBuilder.create().texOffs(139, 137).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 15.0F, 0.5F));

        PartDefinition rightFrontTentaclesS5 = rightFrontTentaclesS4.addOrReplaceChild("rightFrontTentaclesS5", CubeListBuilder.create().texOffs(126, 61).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(-0.02F)), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition Head = Base.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 61).addBox(-21.5F, -21.5F, -20.0F, 43.0F, 21.0F, 40.0F, new CubeDeformation(1.01F))
                .texOffs(0, 0).addBox(-21.5F, -20.5F, -20.0F, 43.0F, 21.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -34.64F, 0.0F));

        PartDefinition FrontHeadFrills = Head.addOrReplaceChild("FrontHeadFrills", CubeListBuilder.create().texOffs(126, 20).addBox(-21.5F, -4.0F, 0.0F, 43.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -18.0F));

        PartDefinition BackHeadFrills = Head.addOrReplaceChild("BackHeadFrills", CubeListBuilder.create().texOffs(126, 0).addBox(-21.5F, -4.0F, 0.0F, 43.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 18.0F));

        PartDefinition leftHeadFrills = Head.addOrReplaceChild("leftHeadFrills", CubeListBuilder.create().texOffs(72, 86).addBox(0.0F, -4.0F, -18.0F, 0.0F, 20.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(20.5F, -0.5F, 0.0F));

        PartDefinition rightHeadFrills = Head.addOrReplaceChild("rightHeadFrills", CubeListBuilder.create().texOffs(0, 86).addBox(0.0F, -4.0F, -18.0F, 0.0F, 20.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.5F, -0.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.Root;
    }
}