package com.ombremoon.enderring.client.render.entity.projectile;

import com.ombremoon.enderring.common.object.entity.projectile.ThrowingPot;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ThrowingPotRenderer extends ThrownItemRenderer<ThrowingPot> {
    public ThrowingPotRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
