package com.ombremoon.enderring.event;

import com.mojang.datafixers.kinds.Const;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.compat.epicfight.gameassets.SkillInit;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedSkillSlots;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
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

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        EntityPatch<Entity> entitypatch = EpicFightCapabilities.getEntityPatch(event.getEntity(), EntityPatch.class);
        if (entitypatch != null && !entitypatch.isInitialized()) {
            if (entitypatch instanceof PlayerPatch<?> playerPatch) {
                CapabilitySkill skill = playerPatch.getSkillCapability();
                skill.skillContainers[ExtendedSkillSlots.HEAVY_ATTACK.universalOrdinal()].setSkill(SkillInit.HEAVY_ATTACK);
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
    }

    @SubscribeEvent
    public static void onDamageDealt(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            Optional<ResourceKey<DamageType>> damageType = event.getSource().typeHolder().unwrapKey();
            if (damageType.isPresent()) {
                for (WeaponDamage weaponDamage : WeaponDamage.values()) {
                    if (damageType.get() == weaponDamage.getDamageType()) {
                        event.setAmount(event.getAmount() - weaponDamage.getDefenseFunction().apply(player));
                        Constants.LOG.info(String.valueOf(event.getAmount()));
                    }
                }
            }
        }
    }
}
