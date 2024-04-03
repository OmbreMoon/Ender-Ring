package com.ombremoon.enderring.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ombremoon.enderring.common.object.item.equipment.ModdedArmor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

//TODO: Check if layers need armor material check

public class ERArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final A outerModel;
    private final A innerModel;
    private final ArmorMaterial armorMaterial;
    private final ResourceLocation textureLocation;

    public ERArmorLayer(RenderLayerParent<T, M> pRenderer, A outerModel, A innerModel, ResourceLocation textureLocation, ArmorMaterial armorMaterial) {
        super(pRenderer);
        this.armorMaterial = armorMaterial;
        this.textureLocation = textureLocation;
        this.outerModel = outerModel;
        this.innerModel = innerModel;
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.renderArmorPiece(pPoseStack, pBuffer, pLivingEntity, EquipmentSlot.CHEST, pPackedLight, this.getArmorModel(EquipmentSlot.CHEST));
        this.renderArmorPiece(pPoseStack, pBuffer, pLivingEntity, EquipmentSlot.LEGS, pPackedLight, this.getArmorModel(EquipmentSlot.LEGS));
        this.renderArmorPiece(pPoseStack, pBuffer, pLivingEntity, EquipmentSlot.FEET, pPackedLight, this.getArmorModel(EquipmentSlot.FEET));
        this.renderArmorPiece(pPoseStack, pBuffer, pLivingEntity, EquipmentSlot.HEAD, pPackedLight, this.getArmorModel(EquipmentSlot.HEAD));
    }

    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource bufferSource, T livingEntity, EquipmentSlot equipmentSlot, int packedLight, A model) {
        ItemStack itemStack = livingEntity.getItemBySlot(equipmentSlot);
        Item item = itemStack.getItem();
        if (item instanceof ModdedArmor moddedArmor && moddedArmor.getArmorMaterial() == getArmorMaterial()) {
            this.getParentModel().copyPropertiesTo(model);
            this.setPartVisibility(model, equipmentSlot);
            this.renderArmorModel(poseStack, bufferSource, packedLight, model, itemStack);
        }
    }

    private void renderArmorModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, A model, ItemStack frameStack) {
        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(getTextureLocation()), false, frameStack.hasFoil());
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    protected void setPartVisibility(A pModel, EquipmentSlot pSlot) {
        pModel.setAllVisible(false);
        switch (pSlot) {
            case HEAD -> pModel.head.visible = true;
            case CHEST -> {
                pModel.body.visible = true;
                pModel.rightArm.visible = true;
                pModel.leftArm.visible = true;
            }
            case LEGS, FEET -> {
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
            }
        }
    }

    private A getArmorModel(EquipmentSlot pSlot) {
        return (this.usesInnerModel(pSlot) ? this.innerModel : this.outerModel);
    }

    private boolean usesInnerModel(EquipmentSlot pSlot) {
        return pSlot == EquipmentSlot.LEGS;
    }

    public ArmorMaterial getArmorMaterial() {
        return this.armorMaterial;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }
}
