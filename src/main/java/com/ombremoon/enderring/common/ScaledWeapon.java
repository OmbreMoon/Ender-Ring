package com.ombremoon.enderring.common;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.ombremoon.enderring.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class ScaledWeapon implements INBTSerializable<CompoundTag> {
    protected Base base = new Base();
    protected Damage damage = new Damage();
    protected Scaling scaling = new Scaling();

    public Base getBaseStats() {
        return this.base;
    }
    public Damage getDamage() { return this.damage; }
    public Scaling getScale() { return this.scaling; }

    public static class Base implements INBTSerializable<CompoundTag> {
        private int maxUpgrades;
        private boolean infusable;
        private boolean twoHandBonus;
        private int elementID;
        private ReinforceType reinforceType;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("MaxUpgrades", this.maxUpgrades);
            nbt.putBoolean("Infusable", this.infusable);
            nbt.putBoolean("TwoHandBonus", this.twoHandBonus);
            nbt.putInt("ElementID", this.elementID);

            //TODO: NEEDS TO BE CHANGED
            nbt.putString("ReinforceType", reinforceType != null ? this.reinforceType.getTypeId().toString() : "");
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
            if (nbt.contains("ReinforceType", 8)) {
                this.reinforceType = ReinforceType.getTypeFromLocation(ResourceLocation.tryParse(nbt.getString("ReinforceType")));
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.maxUpgrades > 0, "Max upgrades must be greater than 0");
            Preconditions.checkArgument(this.maxUpgrades < 26, "Max upgrades must be less than or equal to 25");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("maxUpgrades", this.maxUpgrades);
            if (this.infusable) jsonObject.addProperty("infusable", true);
            if (this.twoHandBonus) jsonObject.addProperty("twoHandBonus", true);
            jsonObject.addProperty("elementID", this.elementID);
            jsonObject.addProperty("reinforceType", this.reinforceType.getTypeId().toString());
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

        public ReinforceType getReinforceType() {
            return this.reinforceType;
        }

        public Base copy() {
            Base base = new Base();
            base.maxUpgrades = this.maxUpgrades;
            base.infusable = this.infusable;
            base.twoHandBonus = this.twoHandBonus;
            base.elementID = this.elementID;
            base.reinforceType = this.reinforceType;
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

    public static class Scaling implements INBTSerializable<CompoundTag> {
        private int strScale;
        private int dexScale;
        private int intScale;
        private int faiScale;
        private int arcScale;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("StrScale", this.strScale);
            nbt.putInt("DexScale", this.dexScale);
            nbt.putInt("IntScale", this.intScale);
            nbt.putInt("FaiScale", this.faiScale);
            nbt.putInt("ArcScale", this.arcScale);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (nbt.contains("StrScale", 99)) {
                this.strScale = nbt.getInt("StrScale");
            }
            if (nbt.contains("DexScale", 99)) {
                this.dexScale = nbt.getInt("DexScale");
            }
            if (nbt.contains("IntScale", 99)) {
                this.intScale = nbt.getInt("IntScale");
            }
            if (nbt.contains("FaiScale", 99)) {
                this.faiScale = nbt.getInt("FaiScale");
            }
            if (nbt.contains("ArcScale", 99)) {
                this.arcScale = nbt.getInt("ArcScale");
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.strScale >= 0, "Strength scaling must be greater than or equal to 0");
            Preconditions.checkArgument(this.dexScale >= 0, "Dexterity scaling must be greater than or equal to 0");
            Preconditions.checkArgument(this.intScale >= 0, "Intelligence scaling must be greater than or equal to 0");
            Preconditions.checkArgument(this.faiScale >= 0, "Faith scaling must be greater than or equal to 0");
            Preconditions.checkArgument(this.arcScale >= 0, "Arcane scaling must be greater than or equal to 0");
            JsonObject jsonObject = new JsonObject();
            if (this.strScale > 0) jsonObject.addProperty("strScale", this.strScale);
            if (this.dexScale > 0) jsonObject.addProperty("dexScale", this.dexScale);
            if (this.intScale > 0) jsonObject.addProperty("intScale", this.intScale);
            if (this.faiScale > 0) jsonObject.addProperty("faiScale", this.faiScale);
            if (this.arcScale > 0) jsonObject.addProperty("arcScale", this.arcScale);
            return jsonObject;
        }

        public int getStrScale() {
            return this.strScale;
        }

        public int getDexScale() {
            return this.dexScale;
        }

        public int getIntScale() {
            return this.intScale;
        }

        public int getFaiScale() {
            return this.faiScale;
        }

        public int getArcScale() {
            return this.arcScale;
        }

        public Scaling copy() {
            Scaling scaling = new Scaling();
            scaling.strScale = this.strScale;
            scaling.dexScale = this.dexScale;
            scaling.intScale = this.intScale;
            scaling.faiScale = this.faiScale;
            scaling.arcScale = this.arcScale;
            return scaling;
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
        nbt.put("Scaling", this.scaling.serializeNBT());
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
        if (nbt.contains("Scaling", 10)) {
            this.scaling.deserializeNBT(nbt.getCompound("Scaling"));
        }
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("base", this.base.toJsonObject());
        jsonObject.add("damage", this.damage.toJsonObject());
        jsonObject.add("scaling", this.scaling.toJsonObject());
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
        scaledWeapon.scaling = this.scaling.copy();
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

        public Builder reinforceType(ReinforceType reinforceType) {
            this.scaledWeapon.base.reinforceType = reinforceType;
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

        public Builder weaponScaling(int strScale, int dexScale, int intScale, int faiScale, int arcScale) {
            this.scaledWeapon.scaling.strScale = strScale;
            this.scaledWeapon.scaling.dexScale = dexScale;
            this.scaledWeapon.scaling.intScale = intScale;
            this.scaledWeapon.scaling.faiScale = faiScale;
            this.scaledWeapon.scaling.arcScale = arcScale;
            return this;
        }
    }
}
