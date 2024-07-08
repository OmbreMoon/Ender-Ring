package com.ombremoon.enderring.event;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.PhysicalDamageType;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.CatalystWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import com.ombremoon.enderring.common.object.world.ModDamageSource;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.common.object.world.effect.StatusEffect;
import com.ombremoon.enderring.common.object.world.effect.stacking.EffectType;
import com.ombremoon.enderring.compat.epicfight.gameassets.SkillInit;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedSkillSlots;
import com.ombremoon.enderring.event.custom.EventFactory;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.skill.CapabilitySkill;

import java.util.Optional;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class CommonModEvents {
    public static final UUID PHYSICAL_UUID = UUID.fromString("81269420-674b-49a8-ba17-d56fd7b11c2e");
    public static final UUID MAGICAL_UUID = UUID.fromString("5245013a-d33b-442a-887a-d57aab061422");
    public static final UUID FIRE_UUID = UUID.fromString("ecab5e7d-3f32-4d1f-bd5b-9a08a8359051");
    public static final UUID LIGHTNING_UUID = UUID.fromString("fded75da-cb86-462e-ba89-7242d58f72ed");
    public static final UUID HOLY_UUID = UUID.fromString("cbe3a5a2-8205-475f-97cf-82d37d042a90");
    public static final UUID SCALING_UUID = UUID.fromString("5a4873e5-8d98-4482-b379-3623421f60fa");

    //TODO: ADD TO EXTENDED SERVERPLAYER PATCH
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        EntityPatch<Entity> entitypatch = EpicFightCapabilities.getEntityPatch(event.getEntity(), EntityPatch.class);
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            CapabilitySkill skill = playerPatch.getSkillCapability();
            skill.skillContainers[ExtendedSkillSlots.HEAVY_ATTACK.universalOrdinal()].setSkill(SkillInit.HEAVY_ATTACK);
        }
    }

    @SubscribeEvent
    public static void onEffectAdd(MobEffectEvent.Applicable event) {
        if (!(event.getEffectInstance().getEffect() instanceof StatusEffect newEffect)) return;
        if (newEffect.getEffectType() == EffectType.PERSISTENT
                || newEffect.getEffectType() == EffectType.UNIQUE
                || newEffect.getEffectType() == EffectType.BUILD_UP)
            return;

        for (MobEffectInstance effectInstance : event.getEntity().getActiveEffects()) {
            if (effectInstance.getEffect() instanceof StatusEffect currentEffect) {
                if (currentEffect.getEffectType().equals(newEffect.getEffectType())) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAttributeModification(ItemAttributeModifierEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof MeleeWeapon weapon && event.getSlotType() == EquipmentSlot.MAINHAND) {
            if (weapon.getPhysDamage(itemStack) > 0) event.addModifier(EntityAttributeInit.PHYS_DAMAGE.get(), new AttributeModifier(PHYSICAL_UUID, "Weapon modifier", weapon.getPhysDamage(itemStack), AttributeModifier.Operation.ADDITION));
            if (weapon.getMagicDamage(itemStack) > 0) event.addModifier(EntityAttributeInit.MAGIC_DAMAGE.get(), new AttributeModifier(MAGICAL_UUID, "Weapon modifier", weapon.getMagicDamage(itemStack), AttributeModifier.Operation.ADDITION));
            if (weapon.getFireDamage(itemStack) > 0) event.addModifier(EntityAttributeInit.FIRE_DAMAGE.get(), new AttributeModifier(FIRE_UUID, "Weapon modifier", weapon.getFireDamage(itemStack), AttributeModifier.Operation.ADDITION));
            if (weapon.getLightDamage(itemStack) > 0) event.addModifier(EntityAttributeInit.LIGHT_DAMAGE.get(), new AttributeModifier(LIGHTNING_UUID, "Weapon modifier", weapon.getLightDamage(itemStack), AttributeModifier.Operation.ADDITION));
            if (weapon.getHolyDamage(itemStack) > 0) event.addModifier(EntityAttributeInit.HOLY_DAMAGE.get(), new AttributeModifier(HOLY_UUID, "Weapon modifier", weapon.getHolyDamage(itemStack), AttributeModifier.Operation.ADDITION));
        }
        if (itemStack.getItem() instanceof CatalystWeapon catalyst && event.getSlotType() == EquipmentSlot.MAINHAND) {
            if (catalyst.getMagicScaling(itemStack) > 0) event.addModifier(catalyst.getMagicType().getAttribute(), new AttributeModifier(SCALING_UUID, "Weapon modifier", catalyst.getMagicScaling(itemStack), AttributeModifier.Operation.ADDITION));
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.hasEffect(StatusEffectInit.ANCESTRAL_SPIRITS_HORN.get())) EntityStatusUtil.increaseFP(player, 3);
            if (player.hasEffect(StatusEffectInit.TAKERS_CAMEO.get())) player.heal((player.getMaxHealth() * 0.03F) + 2F);
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (!(livingEntity instanceof Player)) {
            if (livingEntity.hasEffect(StatusEffectInit.SLEEP.get())) {
                livingEntity.removeEffect(StatusEffectInit.SLEEP.get());
            }
        }
    }

    @SubscribeEvent
    public static void test(LivingEquipmentChangeEvent event) {
//        Constants.LOG.info("Changed!");
    }

    @SubscribeEvent
    public static void onDamageDealt(LivingDamageEvent event) {
        if (event.getSource() instanceof ModDamageSource damageSource) {
            Optional<ResourceKey<DamageType>> damageType = event.getSource().typeHolder().unwrapKey();
            if (damageType.isPresent()) {
                if (damageType.get() == ModDamageTypes.FIRE) Constants.LOG.info("Hi");
                LivingEntity livingEntity = event.getEntity();
                for (WeaponDamage weaponDamage : WeaponDamage.values()) {
                    if (damageType.get() == weaponDamage.getDamageType()) {
                        if (livingEntity.getAttribute(weaponDamage.getDefenseAttribute()) != null) {
                            float negation = 0;
                            if (weaponDamage.getDamageType() == ModDamageTypes.PHYSICAL) {
                                var physicalDamageTypes = damageSource.getDamageTypes();
                                if (physicalDamageTypes.size() > 0) {
                                    float negateAvg = 0;
                                    for (PhysicalDamageType physicalDamage : physicalDamageTypes) {
                                        negateAvg += EntityStatusUtil.getEntityAttribute(livingEntity, physicalDamage.getAttribute()) / 100;
                                    }
                                    negation = 1 - (negateAvg / physicalDamageTypes.size());
                                }
                            } else {
                                negation = EventFactory.calculateEntityNegation(livingEntity, weaponDamage, (float) (1 - (EntityStatusUtil.getEntityAttribute(livingEntity, weaponDamage.getNegateAttribute()) / 100)));
                            }
                            float attr = (float) EntityStatusUtil.getEntityAttribute(livingEntity, weaponDamage.getDefenseAttribute());
                            float modifiedDamage = getDamageAfterDefense(event.getAmount(), attr);
                            event.setAmount(modifiedDamage * negation);
                            Constants.LOG.info(String.valueOf(event.getAmount()));
                        }
                        Constants.LOG.info(String.valueOf(event.getAmount()));
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof AbstractArrow && event.getSource().getEntity() instanceof Player player) {
            if (player.hasEffect(StatusEffectInit.ARROWS_STING_TALISMAN.get())) {
                event.setAmount(event.getAmount() * 1.1F);
            }
        }
    }

    private static float getDamageAfterDefense(float initialDamage, float defense) {
        float maapRatio = initialDamage / defense;
        float damageMult;
        if (maapRatio < 0.12) {
            damageMult = 0.1F;
        } else if (maapRatio < 1) {
            damageMult = (float) (0.1 + 0.3 * Math.pow((0.125 - maapRatio) / 0.88, 2));
        } else if (maapRatio < 2.5) {
            damageMult = (float) (0.7 - 0.3 * Math.pow((2.5 - maapRatio) / 1.5, 2));
        } else if (maapRatio < 8) {
            damageMult = (float) (0.9 - 0.2 * Math.pow((8 - maapRatio) / 5.5, 2));
        } else {
            damageMult = 0.9F;
        }
        return damageMult * initialDamage;
    }
}
