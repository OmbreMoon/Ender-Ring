package com.ombremoon.enderring.client.render.entity;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.client.model.entity.TestDummyModel;
import com.ombremoon.enderring.common.object.entity.npc.TestDummy;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class TestDummyRenderer extends MobRenderer<TestDummy, TestDummyModel<TestDummy>> {
    private static ResourceLocation TEST_DUMMY_TEXTURE = CommonClass.customLocation("textures/entity/test_dummy.png");

    public TestDummyRenderer(EntityRendererProvider.Context pContext, TestDummyModel<TestDummy> pModel) {
        super(pContext, pModel, 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(TestDummy pEntity) {
        return TEST_DUMMY_TEXTURE;
    }
}
