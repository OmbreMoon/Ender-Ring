package com.ombremoon.enderring.util;

import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.item.CrystalTearItem;
import com.ombremoon.enderring.common.object.item.equipment.FlaskItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;

public class FlaskUtil {
    private static final List<RegistryObject<Item>> FLASKS = List.of(ItemInit.WONDROUS_PHYSICK_FLASK, ItemInit.CRIMSON_FLASK, ItemInit.CERULEAN_FLASK);
    private static final String CRYSTAL_TEAR = "CrystalTears";

    public static ResourceLocation getEffectId(CompoundTag compoundTag) {
        return ResourceLocation.tryParse(compoundTag.getString("TearEffect"));
    }

    public static ResourceLocation getCrystalTear(CompoundTag compoundTag) {
        return ResourceLocation.tryParse(compoundTag.getString("CrystalTear"));
    }

    public static ListTag getCrystalTears(ItemStack itemStack) {
        return itemStack.getTag() != null ? itemStack.getTag().getList(CRYSTAL_TEAR, 10) : new ListTag();
    }

    public static int getPhysickSlot(Player player) {
        /*IDynamicStackHandler stackHandler = CurioHelper.getQuickAccessStacks(player);
        for (int i = 0; i < stackHandler.getSlots() - 1; i++) {
            ItemStack itemStack = stackHandler.getStackInSlot(i);
            if (itemStack.is(ItemInit.WONDROUS_PHYSICK_FLASK.get())) {
                return i;
            }
        }
        return 10;*/
        return CurioHelper.getItemSlot(player, ItemInit.WONDROUS_PHYSICK_FLASK.get());
    }

    public static int getCrimsonSlot(Player player) {
        return CurioHelper.getItemSlot(player, ItemInit.CRIMSON_FLASK.get());
    }

    public static int getCeruleanSlot(Player player) {
        return CurioHelper.getItemSlot(player, ItemInit.CERULEAN_FLASK.get());
    }

    public static ItemStack getPhysick(Player player) {
        return CurioHelper.getQuickAccessStacks(player).getStackInSlot(FlaskUtil.getPhysickSlot(player));
    }

    public static ItemStack getCrimsonFlask(Player player) {
        return CurioHelper.getQuickAccessStacks(player).getStackInSlot(FlaskUtil.getCrimsonSlot(player));
    }

    public static ItemStack getCeruleanFlask(Player player) {
        return CurioHelper.getQuickAccessStacks(player).getStackInSlot(FlaskUtil.getCeruleanSlot(player));
    }

    public static boolean hasPhysickInSlot(Player player) {
//        return CurioHelper.getQuickAccessStacks(player).getStackInSlot(FlaskUtil.getPhysickSlot(player)) != ItemStack.EMPTY;
        return getPhysickSlot(player) != 10;
    }

    public static boolean hasCrimsonInSlot(Player player) {
        return getCrimsonSlot(player) != 10;
    }

    public static boolean hasCeruleanInSlot(Player player) {
        return getCeruleanSlot(player) != 10;
    }

    public static void setCrystalTears(Container container,  ItemStack itemStack) {
        ListTag listTag = getCrystalTears(itemStack);

        for (int i = 0; i < 2; i++) {
            ItemStack stack = container.getItem(i);
            if (stack != ItemStack.EMPTY && listTag.size() <= 1) {
                CrystalTearItem crystalTear = (CrystalTearItem) stack.getItem();
                listTag.add(crystalTear.serializeNBT());
            }
        }

        if (listTag.isEmpty()) {
            itemStack.removeTagKey(CRYSTAL_TEAR);
        } else {
            itemStack.addTagElement(CRYSTAL_TEAR, listTag);
        }
    }

    public static boolean hasDuplicateTears(ItemStack flaskStack, Container container) {
        ListTag listTag = getCrystalTears(flaskStack);
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag compoundTag = listTag.getCompound(i);
            MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(getEffectId(compoundTag));
            for (int j = 0; j < 2; j++) {
                ItemStack itemStack = container.getItem(i);
                if (!itemStack.isEmpty()) {
                    CrystalTearItem item = (CrystalTearItem) itemStack.getItem();
                    if (mobEffect == item.getMobEffect()) {
                        return true;
                    }
                }
            }
        }
        return container.getItem(0).getItem() == container.getItem(1).getItem();
    }

    public static void setCharges(ItemStack itemStack, int charges) {
        itemStack.getOrCreateTag().putInt("Charges", charges);
    }

    public static int getMaxCharges(ItemStack itemStack, FlaskItem.Type type) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (type != FlaskItem.Type.PHYSICK) {
            return Math.min(2 + compoundTag.getInt("FlaskLevel"), 14);
        }
        return 1;
    }

    public static void resetFlaskCooldowns(Player player) {
        ItemCooldowns itemCooldowns = player.getCooldowns();
        for (RegistryObject<Item> item : FLASKS) {
            if (itemCooldowns.isOnCooldown(item.get())) {
                itemCooldowns.removeCooldown(item.get());
            }
        }
        setMaxCharges(player);
    }

    public static void setMaxCharges(Player player) {
        IDynamicStackHandler stackHandler = CurioHelper.getQuickAccessStacks(player);
        for (int i = 0; i < stackHandler.getSlots(); i++) {
            ItemStack stack = stackHandler.getStackInSlot(i);
            if (stack.getItem() instanceof FlaskItem flaskItem) {
                setCharges(stack, getMaxCharges(stack, flaskItem.getType()));
            }
        }
    }

    public static List<RegistryObject<Item>> getFlasks() {
        return FLASKS;
    }
}
