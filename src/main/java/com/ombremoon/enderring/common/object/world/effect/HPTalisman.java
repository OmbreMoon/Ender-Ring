//package com.ombremoon.enderring.common.object.world.effect;
//
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.Attribute;
//import net.minecraft.world.entity.ai.attributes.AttributeModifier;
//
//import java.util.List;
//import java.util.Map;
//import java.util.function.Supplier;
//
//public class HPTalisman extends ModifiedAttributeEffect {
//    private final boolean maxHp;
//    private boolean active;
//
//    public HPTalisman(MobEffectCategory category, int color, Map<AttributeModifier, List<Supplier<Attribute>>> attributes, Map<Integer, String> translations, Map<String, Map<Integer, Float>> tiers, boolean maxHp) {
//        super(category, color, attributes, translations, tiers, null, null);
//        this.maxHp = maxHp;
//        this.active = false;
//    }
//
//    @Override
//    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
//        super.applyEffectTick(pLivingEntity, pAmplifier);
//        float currentHp = pLivingEntity.getHealth();
//        float maximumHp = pLivingEntity.getMaxHealth();
//        boolean triggerEffect = (maxHp && currentHp == maximumHp)
//                || (!maxHp && currentHp <= maximumHp * 0.2F);
//
//        if (!active && triggerEffect) {
//            this.active = true;
//            for (Supplier<Attribute> attribute : attributes) {
//                addAttributeModifier(pLivingEntity,
//                        attribute,
//                        modifier.getId(),
//                        modifier.getName(),
//                        getAttributeModifierValue(pAmplifier, modifier),
//                        modifier.getOperation());
//            }
//        } else if (active){
//            for (Supplier<Attribute> attribute : attributes) {
//                removeModifier(pLivingEntity, attribute, modifier.getId());
//            }
//            this.active = false;
//        }
//
//    }
//}
