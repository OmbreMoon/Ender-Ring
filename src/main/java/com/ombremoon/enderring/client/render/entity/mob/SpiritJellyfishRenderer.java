package com.ombremoon.enderring.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.model.entity.mob.SpiritJellyfishModel;
import com.ombremoon.enderring.common.object.entity.mob.creature.SpiritJellyfish;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpiritJellyfishRenderer extends MobRenderer<SpiritJellyfish, SpiritJellyfishModel<SpiritJellyfish>> {
    public SpiritJellyfishRenderer(EntityRendererProvider.Context pContext, SpiritJellyfishModel<SpiritJellyfish> pModel) {
        super(pContext, pModel, 1f);
    }

    @Override
    public void render(SpiritJellyfish pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiritJellyfish spiritJellyfish) {
        return new ResourceLocation(Constants.MOD_ID, "textures/entity/mob/spirit_jellyfish.png");
    }

}
