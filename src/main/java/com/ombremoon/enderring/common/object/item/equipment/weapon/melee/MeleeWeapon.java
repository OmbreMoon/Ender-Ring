package com.ombremoon.enderring.common.object.item.equipment.weapon.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.logging.LogUtils;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.render.ERArmorRenderer;
import com.ombremoon.enderring.client.render.ERWeaponRenderer;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.data.Saturations;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.common.object.world.effect.buildup.BuildUpStatusEffect;
import com.ombremoon.enderring.compat.epicfight.util.EFMUtil;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.eventlistener.SkillConsumeEvent;

import java.util.List;
import java.util.function.Consumer;

public class MeleeWeapon extends AbstractWeapon implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public MeleeWeapon(float attackSpeed, Properties pProperties) {
        super(pProperties.stacksTo(1));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            ServerPlayerPatch serverPlayerPatch = EpicFightCapabilities.getEntityPatch(pPlayer, ServerPlayerPatch.class);
            pPlayer.addEffect(new MobEffectInstance(((BuildUpStatusEffect) StatusEffectInit.SLEEP.get()).setScaledWeapon(this.getModifiedWeapon(itemStack)), 20));
            ((BuildUpStatusEffect) StatusEffectInit.SLEEP.get()).setScaledWeapon(this.getModifiedWeapon(itemStack)).applyInstantaneousEffect(null, null, pPlayer);
//            Constants.LOG.info(String.valueOf(this.getModifiedWeapon(itemStack).serializeNBT()));
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(pAttacker, PlayerPatch.class);
        SkillContainer container = playerPatch.getSkill(EpicFightSkills.BASIC_ATTACK);
        int combo = container.getDataManager().getDataValue(SkillDataKeys.COMBO_COUNTER.get());

        float motionValue = 1.0F;
        CapabilityItem capability = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        if (capability instanceof ExtendedWeaponCapability weaponCapability) {
            List<Float> motionValues = weaponCapability.getAutoMotionValues(playerPatch);
            motionValue = motionValues.get(combo);
        }

        if (pTarget.hasEffect(StatusEffectInit.TWINBLADE_TALISMAN.get())
                && combo == capability.getAutoAttckMotion(playerPatch).size() - 3) motionValue *= 1.45F;

        ServerPlayer player = (ServerPlayer) pAttacker;
        IDynamicStackHandler stacks = CurioHelper.getTalismanStacks(player);
        for (int i = 0; i < stacks.getSlots(); i++) {
            ItemStack stack = stacks.getStackInSlot(i);
            if (stack.is(ItemInit.WINGED_SWORD_INSIGNIA.get()) || stack.is(ItemInit.MILLICENTS_PROSTHESIS.get())    ) {
                addWingedSwordEffect((ServerPlayer) pAttacker, combo == 1, stacks.getStackInSlot(i));
                break;
            }
        }
        LogUtils.getLogger().debug("Combo: " + combo);

        DamageUtil.conditionalHurt(pStack, this.getModifiedWeapon(pStack), pAttacker, pTarget, motionValue);
        return true;
    }

    private static void addWingedSwordEffect(ServerPlayer player, boolean firstAttack, ItemStack talisman) {
        if (player.hasEffect(StatusEffectInit.ATTACK_POWER_BUFF.get())) {
            MobEffectInstance instance = player.getEffect(StatusEffectInit.ATTACK_POWER_BUFF.get());
            if (instance.getAmplifier()+1 % 3 != 0) {
                player.addEffect(new MobEffectInstance(StatusEffectInit.ATTACK_POWER_BUFF.get(),
                        30,
                        instance.getAmplifier() + 1,
                        false,
                        false));
            } else player.addEffect(new MobEffectInstance(instance.getEffect(), 30,
                    instance.getAmplifier(), false, false));
        } else if (!firstAttack) {
            int amplifier = 0;
            if (talisman.is(ItemInit.WINGED_SWORD_INSIGNIA.get())) {
                int amp = talisman.getTag().getInt("tier");
                amplifier = amp*3;
            } else if (talisman.is(ItemInit.MILLICENTS_PROSTHESIS.get())) amplifier = 6;

            player.addEffect(new MobEffectInstance(StatusEffectInit.ATTACK_POWER_BUFF.get(), 30,
                    amplifier, false, false));
        }
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (!level.isClientSide) {
            if (stack.getTag() != null) {
                for (WeaponDamage weaponDamage : WeaponDamage.values()) {
                    stack.getTag().putInt(weaponDamage.getCompoundName(), Mth.floor(weaponDamage.getDamageFunction().apply(this.getModifiedWeapon(stack), player, this.getWeaponLevel(stack))));
                }
                return;
            }

            Constants.LOG.warn("Shouldn't be running every tick!");
            stack.getOrCreateTag();
        }
    }

    public float getPhysDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("PhysicalDamage");
    }

    public float getMagicDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("MagicDamage");
    }

    public float getFireDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("FireDamage");
    }

    public float getLightDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("LightningDamage");
    }

    public float getHolyDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("HolyDamage");
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ERWeaponRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new ERWeaponRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
