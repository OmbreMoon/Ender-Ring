package com.ombremoon.enderring.mixin;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Attributes.class)
public abstract class AttributesMixin {

//    @Shadow protected static abstract Attribute register(String pId, Attribute pAttribute);
}
