package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.common.object.world.effect.stacking.EffectType;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.List;

public class ShabririWoeEffect extends StatusEffect {
    public ShabririWoeEffect(EffectType type, int pColor) {
        super(type, pColor, null, null, null, MobEffectCategory.HARMFUL);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        Level level = pLivingEntity.level();
        if (!level.isClientSide) {
            List<Entity> entityList = level.getEntities(pLivingEntity, pLivingEntity.getBoundingBox().inflate(10.0F));
            for (Entity entity : entityList) {
                if (entity instanceof Monster monster) {
                    monster.setLastHurtByMob(pLivingEntity);
                    monster.setTarget(pLivingEntity);
                }
            }
        }
    }
}
