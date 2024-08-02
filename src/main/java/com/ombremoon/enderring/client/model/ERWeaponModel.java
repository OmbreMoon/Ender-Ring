package com.ombremoon.enderring.client.model;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.model.GeoModel;

import java.util.Objects;

public class ERWeaponModel extends GeoModel<MeleeWeapon> {

    @Override
    public ResourceLocation getModelResource(MeleeWeapon animatable) {
        return CommonClass.customLocation("geo/item/" + getWeaponName(animatable) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MeleeWeapon animatable) {
        return CommonClass.customLocation("textures/item/" + getWeaponName(animatable) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(MeleeWeapon animatable) {
        return CommonClass.customLocation("animations/" + getWeaponName(animatable) + ".animation.json");
    }

    private String getWeaponName(MeleeWeapon weapon) {
        String name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(weapon)).toString();
        return name.substring(name.indexOf(":") + 1);
    }
}
