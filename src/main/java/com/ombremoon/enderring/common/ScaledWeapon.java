package com.ombremoon.enderring.common;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class ScaledWeapon implements INBTSerializable<CompoundTag> {
    protected Base base = new Base();
    protected Damage damage = new Damage();
    protected Scale scale = new Scale();

    public Base getBaseStats() {
        return this.base;
    }
    public Damage getDamage() { return this.damage; }
    public Scale getScale() { return this.scale; }

    public static class Base implements INBTSerializable<CompoundTag> {
        private int maxUpgrades;
        private boolean infusable;
        private boolean twoHandBonus;
        private int elementID;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("MaxUpgrades", this.maxUpgrades);
            nbt.putBoolean("Infusable", this.infusable);
            nbt.putBoolean("TwoHandBonus", this.twoHandBonus);
            nbt.putInt("ElementID", this.elementID);
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
            if (nbt.contains("ElementID", 99)) {
                this.elementID = nbt.getInt("ElementID");
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.maxUpgrades > 0, "Max upgrades must be greater than 0");
            Preconditions.checkArgument(this.maxUpgrades < 26, "Max upgrades must be less than or equal to 25");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("maxUpgrades", this.maxUpgrades);
            if (this.infusable) jsonObject.addProperty("infusable", true);
            if (this.twoHandBonus) jsonObject.addProperty("twoHandBonus", true);
            jsonObject.addProperty("element", this.elementID);
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

        public int getElementID() {
            return this.elementID;
        }

        public Base copy() {
            Base base = new Base();
            base.maxUpgrades = this.maxUpgrades;
            base.infusable = this.infusable;
            base.twoHandBonus = this.twoHandBonus;
            base.elementID = this.elementID;
            return base;
        }
    }

    public static class Damage implements INBTSerializable<CompoundTag> {
        private int physDamage;
        private int magDamage;
        private int fireDamage;
        private int lightDamage;
        private int holyDamage;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("PhysDamage", this.physDamage);
            nbt.putInt("MagDamage", this.magDamage);
            nbt.putInt("FireDamage", this.fireDamage);
            nbt.putInt("LightDamage", this.lightDamage);
            nbt.putInt("HolyDamage", this.holyDamage);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (nbt.contains("PhysDamage", 99)) {
                this.physDamage = nbt.getInt("PhysDamage");
            }
            if (nbt.contains("MagDamage", 99)) {
                this.magDamage = nbt.getInt("MagDamage");
            }
            if (nbt.contains("FireDamage", 99)) {
                this.fireDamage = nbt.getInt("FireDamage");
            }
            if (nbt.contains("LightDamage", 99)) {
                this.lightDamage = nbt.getInt("LightDamage");
            }
            if (nbt.contains("HolyDamage", 99)) {
                this.holyDamage = nbt.getInt("HolyDamage");
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.physDamage >= 0, "Physical damage must be greater than or equal to 0");
            Preconditions.checkArgument(this.magDamage >= 0, "Magical damage must be greater than or equal to 0");
            Preconditions.checkArgument(this.fireDamage >= 0, "Fire damage must be greater than or equal to 0");
            Preconditions.checkArgument(this.lightDamage >= 0, "Lightning damage must be greater than or equal to 0");
            Preconditions.checkArgument(this.holyDamage >= 0, "Holy damage must be greater than or equal to 0");
            JsonObject jsonObject = new JsonObject();
            if (this.physDamage > 0) jsonObject.addProperty("physDamage", this.physDamage);
            if (this.magDamage > 0) jsonObject.addProperty("magDamage", this.magDamage);
            if (this.fireDamage > 0) jsonObject.addProperty("fireDamage", this.fireDamage);
            if (this.lightDamage > 0) jsonObject.addProperty("lightDamage", this.lightDamage);
            if (this.holyDamage > 0) jsonObject.addProperty("holyDamage", this.holyDamage);
            return jsonObject;
        }

        public int getPhysDamage() {
            return this.physDamage;
        }

        public int getMagDamage() {
            return this.magDamage;
        }

        public int getFireDamage() {
            return this.fireDamage;
        }

        public int getLightDamage() {
            return this.lightDamage;
        }

        public int getHolyDamage() {
            return this.holyDamage;
        }

        public Damage copy() {
            Damage damage = new Damage();
            damage.physDamage = this.physDamage;
            damage.magDamage = this.magDamage;
            damage.fireDamage = this.fireDamage;
            damage.lightDamage = this.lightDamage;
            damage.holyDamage = this.holyDamage;
            return damage;
        }
    }

    public static class Scale implements INBTSerializable<CompoundTag> {
        private boolean imDone;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putBoolean("Done", this.imDone);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (nbt.contains("Done", 99)) {
                this.imDone = nbt.getBoolean("Done");
            }
        }

        public JsonObject toJsonObject() {
//            Preconditions.checkArgument(this.strScale >= 0, "Str scaling must be greater than or equal to 0");
            JsonObject jsonObject = new JsonObject();
            if (this.imDone) jsonObject.addProperty("done", this.imDone);
            return jsonObject;
        }

        public boolean getImDone() {
            return this.imDone;
        }

        public Scale copy() {
            Scale scale = new Scale();
            scale.imDone = this.imDone;
            return scale;
        }
    }

    public static class Guard implements INBTSerializable<CompoundTag> {

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {

        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("Base", this.base.serializeNBT());
        nbt.put("Damage", this.damage.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("Base", 10)) {
            this.base.deserializeNBT(nbt.getCompound("Base"));
        }
        if (nbt.contains("Damage", 10)) {
            this.damage.deserializeNBT(nbt.getCompound("Damage"));
        }
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("base", this.base.toJsonObject());
        jsonObject.add("damage", this.damage.toJsonObject());
        return jsonObject;
    }

    public static ScaledWeapon create(CompoundTag nbt) {
        ScaledWeapon weapon = new ScaledWeapon();
        weapon.deserializeNBT(nbt);
        return weapon;
    }

    public ScaledWeapon copy() {
        ScaledWeapon scaledWeapon = new ScaledWeapon();
        scaledWeapon.base = this.base.copy();
        scaledWeapon.damage = this.damage.copy();
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

        public Builder elementID(int elementId) {
            this.scaledWeapon.base.elementID = elementId;
            return this;
        }

        public Builder scale() {
            this.scaledWeapon.scale.imDone = true;
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

        public Builder weaponDamage(int physDamage, int magDamage, int fireDamage, int lightDamage, int holyDamage) {
            this.scaledWeapon.damage.physDamage = physDamage;
            this.scaledWeapon.damage.magDamage = magDamage;
            this.scaledWeapon.damage.fireDamage = fireDamage;
            this.scaledWeapon.damage.lightDamage = lightDamage;
            this.scaledWeapon.damage.holyDamage = holyDamage;
            return this;
        }
    }
}
