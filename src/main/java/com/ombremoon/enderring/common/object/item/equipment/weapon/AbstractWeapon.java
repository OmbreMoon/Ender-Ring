package com.ombremoon.enderring.common.object.item.equipment.weapon;

import com.google.common.collect.Maps;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.ScaledWeaponManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.Map;

public class AbstractWeapon extends Item implements Scalable {
    public static Logger LOGGER = Constants.LOG;
    private ScaledWeapon weapon = new ScaledWeapon();
    private final Map<ReinforceType, ScaledWeapon> affinities = Maps.newHashMap();

    public AbstractWeapon(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setWeapon(ScaledWeaponManager.Wrapper wrapper) {
        this.weapon = wrapper.getWeapon();
    }

    @Override
    public Map<ReinforceType, ScaledWeapon> getAffinity() {
        return this.affinities;
    }

    @Override
    public void setAffinities(ReinforceType type, ScaledWeaponManager.Wrapper wrapper) {
        this.affinities.put(type, wrapper.getWeapon());
    }

    @Override
    public ScaledWeapon getWeapon() {
        return this.weapon;
    }

    public int getWeaponLevel(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getInt("WeaponLevel");
    }

    public void setWeaponLevel(ItemStack itemStack, int weaponLevel) {
        itemStack.getOrCreateTag().putInt("WeaponLevel", weaponLevel);
    }/*

    @Override
    public ScaledWeapon getModifiedWeapon(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("Weapon", 10)) {
            return ScaledWeapon.create(nbt.getCompound("Weapon"));
        }
        return this.weapon;
    }*/

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return false;
    }
}
