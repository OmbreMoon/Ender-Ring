package com.ombremoon.enderring.common.object.item.equipment;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.render.ERArmorRenderer;
import com.ombremoon.enderring.common.ArmorResistance;
import com.ombremoon.enderring.common.data.ArmorResistanceManager;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.item.ArmorInit;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Resistance;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.Util;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumMap;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class ModdedArmor extends ArmorItem implements GeoItem, Resistance {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private ArmorResistance armorResistance = new ArmorResistance();
    private static final EnumMap<Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266744_) -> {
        p_266744_.put(ArmorItem.Type.BOOTS, UUID.fromString("8d4a600b-351a-4c3f-9fa3-363a6ea1253a"));
        p_266744_.put(ArmorItem.Type.LEGGINGS, UUID.fromString("9f4e34a9-17bb-4d71-b269-3098883fc1bf"));
        p_266744_.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("68dd9d5d-1078-42af-93d1-c8abf0b5f681"));
        p_266744_.put(ArmorItem.Type.HELMET, UUID.fromString("75445161-a8c7-43f5-9312-037e5c11e2cf"));
    });

    public ModdedArmor(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        Constants.LOG.info(String.valueOf(this.armorResistance.serializeNBT()));
        return super.use(pLevel, pPlayer, pHand);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new ERArmorRenderer();
                }

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

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

    public boolean hasFullSet(LivingEntity livingEntity) {
        Set<Item> armorSet = new ObjectOpenHashSet<>();

        for (ItemStack itemStack : livingEntity.getArmorSlots()) {
            if (itemStack.isEmpty())
                continue;

            armorSet.add(itemStack.getItem());
        }

        for (Item item : armorSet) {
            if (!(item instanceof ModdedArmor armor && armor.getMaterial() == this.getMaterial())) {
                return false;
            }
        }
        return armorSet.size() == 4;
    }

    @Override
    public ArmorResistance getArmor() {
        return this.armorResistance;
    }

    @Override
    public void setWeapon(ArmorResistanceManager.Wrapper wrapper) {
        this.armorResistance = wrapper.getArmorResistance();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == this.type.getSlot()) {
            UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(this.type);
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(EntityAttributeInit.IMMUNITY.get(), new AttributeModifier(uuid, "Armor resistance", this.armorResistance.getResistance().getImmunity(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.ROBUSTNESS.get(), new AttributeModifier(uuid, "Armor resistance", this.armorResistance.getResistance().getRobustness(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.FOCUS.get(), new AttributeModifier(uuid, "Armor resistance", this.armorResistance.getResistance().getFocus(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.VITALITY.get(), new AttributeModifier(uuid, "Armor resistance", this.armorResistance.getResistance().getVitality(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.PHYS_NEGATE.get(), new AttributeModifier(uuid, "Armor negation", this.armorResistance.getNegation().getPhysNegation(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.STRIKE_NEGATE.get(), new AttributeModifier(uuid, "Armor negation", this.armorResistance.getNegation().getStrikeNegation(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.SLASH_NEGATE.get(), new AttributeModifier(uuid, "Armor negation", this.armorResistance.getNegation().getSlashNegation(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.PIERCE_NEGATE.get(), new AttributeModifier(uuid, "Armor negation", this.armorResistance.getNegation().getPierceNegation(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.MAGIC_NEGATE.get(), new AttributeModifier(uuid, "Armor negation", this.armorResistance.getNegation().getMagicNegation(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.FIRE_NEGATE.get(), new AttributeModifier(uuid, "Armor negation", this.armorResistance.getNegation().getFireNegation(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.LIGHT_NEGATE.get(), new AttributeModifier(uuid, "Armor negation", this.armorResistance.getNegation().getLightNegation(), AttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributeInit.HOLY_NEGATE.get(), new AttributeModifier(uuid, "Armor negation", this.armorResistance.getNegation().getHolyNegation(), AttributeModifier.Operation.ADDITION));
            return builder.build();
        } else {
            return super.getAttributeModifiers(slot, stack);
        }
    }
}
