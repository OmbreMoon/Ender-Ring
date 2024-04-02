package com.ombremoon.enderring.common;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
;

public class ScaledWeapon implements INBTSerializable<CompoundTag> {
    protected Base base = new Base();
    protected Damage damage = new Damage();


    public Base getBaseStats() {
        return this.base;
    }

    public static class Base implements INBTSerializable<CompoundTag> {
        private int maxUpgrades;
        private boolean infusable;
        private boolean twoHandBonus;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("MaxUpgrades", this.maxUpgrades);
            nbt.putBoolean("Infusable", this.infusable);
            nbt.putBoolean("TwoHandBonus", this.twoHandBonus);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (nbt.contains("MaxUpgrades", 99)) {
                this.maxUpgrades = nbt.getInt("MaxUpgrades");
            }
            if (nbt.contains("Infusable", 99)) {
                this.infusable = nbt.getBoolean("Infusable");
            }
            if (nbt.contains("TwoHandBonus", 99)) {
                this.twoHandBonus = nbt.getBoolean("TwoHandBonus");
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.maxUpgrades > 0, "Max upgrades must be greater than 0");
            Preconditions.checkArgument(this.maxUpgrades < 26, "Max upgrades must be less than or equal to 25");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("maxUpgrades", this.maxUpgrades);
            if (this.infusable) jsonObject.addProperty("infusable", true);
            if (this.twoHandBonus) jsonObject.addProperty("twoHandBonus", true);
            return jsonObject;
        }

        public int getMaxUpgrades() {
            return this.maxUpgrades;
        }

        public boolean isInfusable() {
            return this.infusable;
        }

        public boolean hasTwoHandBonus() {
            return this.twoHandBonus;
        }

        public Base copy() {
            Base base = new Base();
            base.maxUpgrades = this.maxUpgrades;
            base.infusable = this.infusable;
            base.twoHandBonus = this.twoHandBonus;
            return base;
        }
    }

    public static class Damage implements INBTSerializable<CompoundTag> {

        @Override
        public CompoundTag serializeNBT() {
            return null;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {

        }
    }

    public static class Guard implements INBTSerializable<CompoundTag> {

        @Override
        public CompoundTag serializeNBT() {
            return null;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {

        }
    }

    public ScaledWeapon copy() {
        ScaledWeapon scaledWeapon = new ScaledWeapon();
        scaledWeapon.base = this.base.copy();
        return scaledWeapon;
    }

    public static class Builder {
        private final ScaledWeapon scaledWeapon;

        private Builder() {
            this.scaledWeapon = new ScaledWeapon();
        }

        public static Builder create() {
            return new Builder();
        }

        public ScaledWeapon build() {
            return this.scaledWeapon.copy();
        }

        public Builder defaultMaxUpgrades() {
            this.scaledWeapon.base.maxUpgrades = 25;
            return this;
        }

        public Builder uniqueMaxUpgrades() {
            this.scaledWeapon.base.maxUpgrades = 10;
            return this;
        }

        public Builder infusable() {
            this.scaledWeapon.base.infusable = true;
            return this;
        }

        public Builder twoHandBonus() {
            this.scaledWeapon.base.twoHandBonus = true;
            return this;
        }

        public Builder isUnique() {
            this.scaledWeapon.base.maxUpgrades = 10;
            this.scaledWeapon.base.infusable = false;
            return this;
        }

    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("Base", this.base.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("Base", 10)) {
            this.base.deserializeNBT(nbt.getCompound("Base"));
        }
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("base", this.base.toJsonObject());
        return jsonObject;
    }

}
