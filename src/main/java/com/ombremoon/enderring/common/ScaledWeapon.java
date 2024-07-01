package com.ombremoon.enderring.common;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ombremoon.enderring.common.data.AttackElement;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.Saturations;
import com.ombremoon.enderring.common.object.PhysicalDamageType;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public class ScaledWeapon implements INBTSerializable<CompoundTag> {
    protected Base base = new Base();
    protected Damage damage = new Damage();
    protected Scaling scaling = new Scaling();
    protected Requirements requirements = new Requirements();
    protected Guard guard = new Guard();
    protected Status status = new Status();

    public Base getBaseStats() {
        return this.base;
    }
    public Damage getDamage() { return this.damage; }
    public Scaling getScale() { return this.scaling; }
    public Requirements getRequirements() { return this.requirements; }
    public Guard getGuard() { return this.guard; }
    public Status getStatus() { return this.status; }

    public static class Base implements INBTSerializable<CompoundTag> {
        private int maxUpgrades;
        private boolean infusable;
        private boolean twoHandBonus;
        private AttackElement elementID;
        private ReinforceType reinforceType;
        private Saturations[] saturations;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("MaxUpgrades", this.maxUpgrades);
            nbt.putBoolean("Infusable", this.infusable);
            nbt.putBoolean("TwoHandBonus", this.twoHandBonus);

            nbt.putIntArray("Saturations", this.saturations != null ? Arrays.stream(this.saturations).mapToInt(Saturations::ordinal).toArray() : new int[]{0, 0, 0, 0, 0});
            nbt.putInt("ElementID", this.elementID != null ? this.elementID.getElementId() : AttackElement.DEFAULT_ID);
            nbt.putString("ReinforceType", this.reinforceType != null ? this.reinforceType.getTypeId().toString() : ReinforceType.DEFAULT.getTypeId().toString());
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
                this.elementID = AttackElement.getElementFromId(nbt.getInt("ElementID"));
            }
            if (nbt.contains("ReinforceType", 8)) {
                this.reinforceType = ReinforceType.getTypeFromLocation(ResourceLocation.tryParse(nbt.getString("ReinforceType")));
            }
            if (nbt.contains("Saturations", 11)) {
                this.saturations = createSaturationArray(nbt.getIntArray("Saturations"));
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.maxUpgrades >= 0, "Max upgrades must be greater than or equal 0");
            Preconditions.checkArgument(this.maxUpgrades < 26, "Max upgrades must be less than or equal to 25");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("maxUpgrades", this.maxUpgrades);
            if (this.infusable) jsonObject.addProperty("infusable", true);
            if (this.twoHandBonus) jsonObject.addProperty("twoHandBonus", true);
            jsonObject.addProperty("elementID", this.elementID.getElementId());
            jsonObject.addProperty("reinforceType", this.reinforceType.getTypeId().toString());

            JsonArray jsonArray = new JsonArray(5);
            for (Saturations saturation : this.saturations) {
                jsonArray.add(saturation.ordinal());
            }
            jsonObject.add("saturations", jsonArray);

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

        public AttackElement getElementID() {
            return this.elementID;
        }

        public ReinforceType getReinforceType() {
            return this.reinforceType;
        }

        public Saturations[] getSaturations() {
            return this.saturations;
        }

        public Base copy() {
            Base base = new Base();
            base.maxUpgrades = this.maxUpgrades;
            base.infusable = this.infusable;
            base.twoHandBonus = this.twoHandBonus;
            base.elementID = this.elementID;
            base.reinforceType = this.reinforceType;
            base.saturations = this.saturations;
            return base;
        }
    }

    public static class Damage implements INBTSerializable<CompoundTag> {
        private int physDamage;
        private int magDamage;
        private int fireDamage;
        private int lightDamage;
        private int holyDamage;
        private PhysicalDamageType[] physDamageTypes = new PhysicalDamageType[]{};

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("PhysDamage", this.physDamage);
            nbt.putInt("MagDamage", this.magDamage);
            nbt.putInt("FireDamage", this.fireDamage);
            nbt.putInt("LightDamage", this.lightDamage);
            nbt.putInt("HolyDamage", this.holyDamage);
            nbt.putIntArray("PhysDamageTypes", this.physDamageTypes != null ? Arrays.stream(this.physDamageTypes).mapToInt(PhysicalDamageType::ordinal).toArray() : new int[]{0});
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
            if (nbt.contains("PhysDamageTypes", 11)) {
                this.physDamageTypes = createDamageTypeArray(nbt.getIntArray("PhysDamageTypes"));
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.physDamage >= 0, "Physical damage must be greater than or equal to 0");
            Preconditions.checkArgument(this.magDamage >= 0, "Magical damage must be greater than or equal to 0");
            Preconditions.checkArgument(this.fireDamage >= 0, "Fire damage must be greater than or equal to 0");
            Preconditions.checkArgument(this.lightDamage >= 0, "Lightning damage must be greater than or equal to 0");
            Preconditions.checkArgument(this.holyDamage >= 0, "Holy damage must be greater than or equal to 0");
//            Preconditions.checkArgument(this.physDamageTypes != null, "Weapon must have a physical damage type");
            JsonObject jsonObject = new JsonObject();
            if (this.physDamage > 0) jsonObject.addProperty("physDamage", this.physDamage);
            if (this.magDamage > 0) jsonObject.addProperty("magDamage", this.magDamage);
            if (this.fireDamage > 0) jsonObject.addProperty("fireDamage", this.fireDamage);
            if (this.lightDamage > 0) jsonObject.addProperty("lightDamage", this.lightDamage);
            if (this.holyDamage > 0) jsonObject.addProperty("holyDamage", this.holyDamage);

            JsonArray jsonArray = new JsonArray();
            for (PhysicalDamageType damageType : this.physDamageTypes) {
                jsonArray.add(damageType.getName());
            }
            jsonObject.add("physDamageTypes", jsonArray);
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

        public PhysicalDamageType[] getPhysDamageTypes() {
            return this.physDamageTypes;
        }

        public Map<WeaponDamage, Integer> getDamageMap() {
            Map<WeaponDamage, Integer> damageMap = new TreeMap<>();
            if (this.physDamage > 0) damageMap.put(WeaponDamage.PHYSICAL, this.physDamage);
            if (this.magDamage > 0) damageMap.put(WeaponDamage.MAGICAL, this.magDamage);
            if (this.fireDamage > 0) damageMap.put(WeaponDamage.FIRE, this.fireDamage);
            if (this.lightDamage > 0) damageMap.put(WeaponDamage.LIGHTNING, this.lightDamage);
            if (this.holyDamage > 0) damageMap.put(WeaponDamage.HOLY, this.holyDamage);
            return damageMap;
        }

        public Map<WeaponDamage, Float> getScaledDamageMap(ScaledWeapon weapon, LivingEntity livingEntity, int weaponLevel) {
            Map<WeaponDamage, Float> damageMap = new TreeMap<>();
            if (DamageUtil.getPhysicalAP(weapon, livingEntity, weaponLevel) > 0) damageMap.put(WeaponDamage.PHYSICAL, DamageUtil.getPhysicalAP(weapon, livingEntity, weaponLevel));
            if (DamageUtil.getMagicalAP(weapon, livingEntity, weaponLevel) > 0) damageMap.put(WeaponDamage.MAGICAL, DamageUtil.getMagicalAP(weapon, livingEntity, weaponLevel));
            if (DamageUtil.getFireAP(weapon, livingEntity, weaponLevel) > 0) damageMap.put(WeaponDamage.FIRE, DamageUtil.getFireAP(weapon, livingEntity, weaponLevel));
            if (DamageUtil.getLightningAP(weapon, livingEntity, weaponLevel) > 0) damageMap.put(WeaponDamage.LIGHTNING, DamageUtil.getLightningAP(weapon, livingEntity, weaponLevel));
            if (DamageUtil.getHolyAP(weapon, livingEntity, weaponLevel) > 0) damageMap.put(WeaponDamage.HOLY, DamageUtil.getHolyAP(weapon, livingEntity, weaponLevel));
            return damageMap;
        }

        public Damage copy() {
            Damage damage = new Damage();
            damage.physDamage = this.physDamage;
            damage.magDamage = this.magDamage;
            damage.fireDamage = this.fireDamage;
            damage.lightDamage = this.lightDamage;
            damage.holyDamage = this.holyDamage;
            damage.physDamageTypes = this.physDamageTypes;
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

        public Map<WeaponScaling, Integer> getScalingMap() {
            Map<WeaponScaling, Integer> scalingMap = new TreeMap<>();
            if (this.strScale > 0) scalingMap.put(WeaponScaling.STR, this.strScale);
            if (this.dexScale > 0) scalingMap.put(WeaponScaling.DEX, this.dexScale);
            if (this.intScale > 0) scalingMap.put(WeaponScaling.INT, this.intScale);
            if (this.faiScale > 0) scalingMap.put(WeaponScaling.FAI, this.faiScale);
            if (this.arcScale > 0) scalingMap.put(WeaponScaling.ARC, this.arcScale);
            return scalingMap;
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

    public static class Requirements implements INBTSerializable<CompoundTag> {
        private int strReq;
        private int dexReq;
        private int intReq;
        private int faiReq;
        private int arcReq;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("StrReq", this.strReq);
            nbt.putInt("DexReq", this.dexReq);
            nbt.putInt("IntReq", this.intReq);
            nbt.putInt("FaiReq", this.faiReq);
            nbt.putInt("ArcReq", this.arcReq);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (nbt.contains("StrReq", 99)) {
                this.strReq = nbt.getInt("StrReq");
            }
            if (nbt.contains("DexReq", 99)) {
                this.dexReq = nbt.getInt("DexReq");
            }
            if (nbt.contains("IntReq", 99)) {
                this.intReq = nbt.getInt("IntReq");
            }
            if (nbt.contains("FaiReq", 99)) {
                this.faiReq = nbt.getInt("FaiReq");
            }
            if (nbt.contains("ArcReq", 99)) {
                this.arcReq = nbt.getInt("ArcReq");
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.strReq >= 0, "Strength requirement must be greater than or equal to 0");
            Preconditions.checkArgument(this.dexReq >= 0, "Dexterity requirement must be greater than or equal to 0");
            Preconditions.checkArgument(this.intReq >= 0, "Intelligence requirement must be greater than or equal to 0");
            Preconditions.checkArgument(this.faiReq >= 0, "Faith requirement must be greater than or equal to 0");
            Preconditions.checkArgument(this.arcReq >= 0, "Arcane requirement must be greater than or equal to 0");
            JsonObject jsonObject = new JsonObject();
            if (this.strReq > 0) jsonObject.addProperty("strReq", this.strReq);
            if (this.dexReq > 0) jsonObject.addProperty("dexReq", this.dexReq);
            if (this.intReq > 0) jsonObject.addProperty("intReq", this.intReq);
            if (this.faiReq > 0) jsonObject.addProperty("faiReq", this.faiReq);
            if (this.arcReq > 0) jsonObject.addProperty("arcReq", this.arcReq);
            return jsonObject;
        }

        public int getStrReq() {
            return this.strReq;
        }

        public int getDexReq() {
            return this.dexReq;
        }

        public int getIntReq() {
            return this.intReq;
        }

        public int getFaiReq() {
            return this.faiReq;
        }

        public int getArcReq() {
            return this.arcReq;
        }

        public Map<WeaponScaling, Integer> getReqMap() {
            Map<WeaponScaling, Integer> reqMap = new TreeMap<>();
            if (this.strReq > 0) reqMap.put(WeaponScaling.STR, this.strReq);
            if (this.dexReq > 0) reqMap.put(WeaponScaling.DEX, this.dexReq);
            if (this.intReq > 0) reqMap.put(WeaponScaling.INT, this.intReq);
            if (this.faiReq > 0) reqMap.put(WeaponScaling.FAI, this.faiReq);
            if (this.arcReq > 0) reqMap.put(WeaponScaling.ARC, this.arcReq);
            return reqMap;
        }

        public boolean meetsRequirements(LivingEntity livingEntity, ScaledWeapon weapon, WeaponDamage weaponDamage) {
            final var list = weapon.getBaseStats().getElementID().getListMap().get(weaponDamage);
            if (getReqMap().size() != 0) {
                for (var scaling : list) {
                    if (EntityStatusUtil.getEntityAttribute(livingEntity, scaling.getAttribute()) < getReqMap().get(scaling)) {
                        return false;
                    }
                }
            }
            return true;
        }

        public boolean meetsRequirements(LivingEntity livingEntity) {
            for (var entry : this.getReqMap().entrySet()) {
                if (EntityStatusUtil.getEntityAttribute(livingEntity, entry.getKey().getAttribute()) < entry.getValue()) {
                    return false;
                }
            }
            return true;
        }

        public Requirements copy() {
            Requirements requirements = new Requirements();
            requirements.strReq = this.strReq;
            requirements.dexReq = this.dexReq;
            requirements.intReq = this.intReq;
            requirements.faiReq = this.faiReq;
            requirements.arcReq = this.arcReq;
            return requirements;
        }
    }

    public static class Guard implements INBTSerializable<CompoundTag> {
        private int physGuard;
        private int magicGuard;
        private int fireGuard;
        private int lightGuard;
        private int holyGuard;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("PhysGuard", this.physGuard);
            nbt.putInt("MagicGuard", this.magicGuard);
            nbt.putInt("FireGuard", this.fireGuard);
            nbt.putInt("LightGuard", this.lightGuard);
            nbt.putInt("HolyGuard", this.holyGuard);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (nbt.contains("PhysGuard", 99)) {
                this.physGuard = nbt.getInt("PhysGuard");
            }
            if (nbt.contains("MagicGuard", 99)) {
                this.magicGuard = nbt.getInt("MagicGuard");
            }
            if (nbt.contains("FireGuard", 99)) {
                this.fireGuard = nbt.getInt("FireGuard");
            }
            if (nbt.contains("LightGuard", 99)) {
                this.lightGuard = nbt.getInt("LightGuard");
            }
            if (nbt.contains("HolyGuard", 99)) {
                this.holyGuard = nbt.getInt("HolyGuard");
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.physGuard >= 0, "Physical guard must be greater than or equal to 0");
            Preconditions.checkArgument(this.magicGuard >= 0, "Magic guard must be greater than or equal to 0");
            Preconditions.checkArgument(this.fireGuard >= 0, "Fire guard must be greater than or equal to 0");
            Preconditions.checkArgument(this.lightGuard >= 0, "Lightning guard must be greater than or equal to 0");
            Preconditions.checkArgument(this.holyGuard >= 0, "Holy guard must be greater than or equal to 0");
            JsonObject jsonObject = new JsonObject();
            if (this.physGuard > 0) jsonObject.addProperty("physGuard", this.physGuard);
            if (this.magicGuard > 0) jsonObject.addProperty("magicGuard", this.magicGuard);
            if (this.fireGuard > 0) jsonObject.addProperty("fireGuard", this.fireGuard);
            if (this.lightGuard > 0) jsonObject.addProperty("lightGuard", this.lightGuard);
            if (this.holyGuard > 0) jsonObject.addProperty("holyGuard", this.holyGuard);
            return jsonObject;
        }

        public int getPhysGuard() {
            return this.physGuard;
        }

        public int getMagicGuard() {
            return this.magicGuard;
        }

        public int getFireGuard() {
            return this.fireGuard;
        }

        public int getLightGuard() {
            return this.lightGuard;
        }

        public int getHolyGuard() {
            return this.holyGuard;
        }

        public Map<WeaponDamage, Integer> getGuardMap() {
            Map<WeaponDamage, Integer> guardMap = new TreeMap<>();
            if (this.physGuard > 0) guardMap.put(WeaponDamage.PHYSICAL, this.physGuard);
            if (this.magicGuard > 0) guardMap.put(WeaponDamage.MAGICAL, this.magicGuard);
            if (this.fireGuard > 0) guardMap.put(WeaponDamage.FIRE, this.fireGuard);
            if (this.lightGuard > 0) guardMap.put(WeaponDamage.LIGHTNING, this.lightGuard);
            if (this.holyGuard > 0) guardMap.put(WeaponDamage.HOLY, this.holyGuard);
            return guardMap;
        }

        public Guard copy() {
            Guard guard = new Guard();
            guard.physGuard = this.physGuard;
            guard.magicGuard = this.magicGuard;
            guard.fireGuard = this.fireGuard;
            guard.lightGuard = this.lightGuard;
            guard.holyGuard = this.holyGuard;
            return guard;
        }
    }

    public static class Status implements INBTSerializable<CompoundTag> {
        private int poison;
        private int scarletRot;
        private int bloodLoss;
        private int frostBite;
        private int sleep;
        private int madness;
        private int deathBlight;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("Poison", this.poison);
            nbt.putInt("ScarletRot", this.scarletRot);
            nbt.putInt("BloodLoss", this.bloodLoss);
            nbt.putInt("FrostBite", this.frostBite);
            nbt.putInt("Sleep", this.sleep);
            nbt.putInt("Madness", this.madness);
            nbt.putInt("DeathBlight", this.deathBlight);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (nbt.contains("Poison", 99)) {
                this.poison = nbt.getInt("Poison");
            }
            if (nbt.contains("ScarletRot", 99)) {
                this.scarletRot = nbt.getInt("ScarletRot");
            }
            if (nbt.contains("BloodLoss", 99)) {
                this.bloodLoss = nbt.getInt("BloodLoss");
            }
            if (nbt.contains("FrostBite", 99)) {
                this.frostBite = nbt.getInt("FrostBite");
            }
            if (nbt.contains("Sleep", 99)) {
                this.sleep = nbt.getInt("Sleep");
            }
            if (nbt.contains("Madness", 99)) {
                this.madness = nbt.getInt("Madness");
            }
            if (nbt.contains("DeathBlight", 99)) {
                this.deathBlight = nbt.getInt("DeathBlight");
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.poison >= 0, "Poison build up must be greater than or equal to 0");
            Preconditions.checkArgument(this.scarletRot >= 0, "Scarlet rot build up must be greater than or equal to 0");
            Preconditions.checkArgument(this.bloodLoss >= 0, "Blood loss build up must be greater than or equal to 0");
            Preconditions.checkArgument(this.frostBite >= 0, "Frostbite build up must be greater than or equal to 0");
            Preconditions.checkArgument(this.sleep >= 0, "Sleep build up must be greater than or equal to 0");
            Preconditions.checkArgument(this.madness >= 0, "Madness build up must be greater than or equal to 0");
            Preconditions.checkArgument(this.deathBlight >= 0, "Death blight build up must be greater than or equal to 0");
            JsonObject jsonObject = new JsonObject();
            if (this.poison > 0) jsonObject.addProperty("poison", this.poison);
            if (this.scarletRot > 0) jsonObject.addProperty("scarletRot", this.scarletRot);
            if (this.bloodLoss > 0) jsonObject.addProperty("bloodLoss", this.bloodLoss);
            if (this.frostBite > 0) jsonObject.addProperty("frostBite", this.frostBite);
            if (this.sleep > 0) jsonObject.addProperty("sleep", this.sleep);
            if (this.madness > 0) jsonObject.addProperty("madness", this.madness);
            if (this.deathBlight > 0) jsonObject.addProperty("deathBlight", this.deathBlight);
            return jsonObject;
        }

        public int getPoison() {
            return this.poison;
        }

        public int getScarletRot() {
            return this.scarletRot;
        }

        public int getBloodLoss() {
            return this.bloodLoss;
        }

        public int getFrostBite() {
            return this.frostBite;
        }

        public int getSleep() {
            return this.sleep;
        }


        public int getMadness() {
            return this.madness;
        }

        public int getDeathBlight() {
            return this.deathBlight;
        }

        public Status copy() {
            Status status = new Status();
            status.poison = this.poison;
            status.scarletRot = this.scarletRot;
            status.bloodLoss = this.bloodLoss;
            status.frostBite = this.frostBite;
            status.madness = this.madness;
            status.deathBlight = this.deathBlight;
            return status;
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("Base", this.base.serializeNBT());
        nbt.put("Damage", this.damage.serializeNBT());
        nbt.put("Scaling", this.scaling.serializeNBT());
        nbt.put("Requirements", this.requirements.serializeNBT());
        nbt.put("Guard", this.guard.serializeNBT());
        nbt.put("Status", this.status.serializeNBT());
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
        if (nbt.contains("Requirements", 10)) {
            this.requirements.deserializeNBT(nbt.getCompound("Requirements"));
        }
        if (nbt.contains("Guard", 10)) {
            this.guard.deserializeNBT(nbt.getCompound("Guard"));
        }
        if (nbt.contains("Status", 10)) {
            this.status.deserializeNBT(nbt.getCompound("Status"));
        }
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("base", this.base.toJsonObject());
        jsonObject.add("damage", this.damage.toJsonObject());
        jsonObject.add("scaling", this.scaling.toJsonObject());
        jsonObject.add("requirements", this.requirements.toJsonObject());
        jsonObject.add("guard", this.guard.toJsonObject());
        jsonObject.add("status", this.status.toJsonObject());
        return jsonObject;
    }

    public static ScaledWeapon create(CompoundTag nbt) {
        ScaledWeapon weapon = new ScaledWeapon();
        weapon.deserializeNBT(nbt);
        return weapon;
    }

    private static Saturations[] createSaturationArray(int[] array) {
        return Arrays.stream(array).mapToObj(Saturations::getSaturationById).toArray(Saturations[]::new);
    }

    private static PhysicalDamageType[] createDamageTypeArray(int[] array) {
        return Arrays.stream(array).mapToObj(PhysicalDamageType::getTypeById).toArray(PhysicalDamageType[]::new);
    }

    public ScaledWeapon copy() {
        ScaledWeapon scaledWeapon = new ScaledWeapon();
        scaledWeapon.base = this.base.copy();
        scaledWeapon.damage = this.damage.copy();
        scaledWeapon.scaling = this.scaling.copy();
        scaledWeapon.requirements = this.requirements.copy();
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

        public Builder elementID(AttackElement elementId) {
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

        public Builder saturation(int physSat, int magSat, int fireSat, int lightSat, int holySat) {
            this.scaledWeapon.base.saturations = createSaturationArray(new int[]{physSat, magSat, fireSat, lightSat, holySat});
            return this;
        }

        public Builder saturation(Saturations physSat, Saturations magSat, Saturations fireSat, Saturations lightSat, Saturations holySat) {
            this.scaledWeapon.base.saturations = new Saturations[]{physSat, magSat, fireSat, lightSat, holySat};
            return this;
        }

        public Builder physDamageType(int... damageTypes) {
            this.scaledWeapon.damage.physDamageTypes = createDamageTypeArray(damageTypes);
            return this;
        }

        public Builder physDamageType(PhysicalDamageType... damageTypes) {
            this.scaledWeapon.damage.physDamageTypes = damageTypes;
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

        public Builder weaponRequirements(int strReq, int dexReq, int intReq, int faiReq, int arcReq) {
            this.scaledWeapon.requirements.strReq = strReq;
            this.scaledWeapon.requirements.dexReq = dexReq;
            this.scaledWeapon.requirements.intReq = intReq;
            this.scaledWeapon.requirements.faiReq = faiReq;
            this.scaledWeapon.requirements.arcReq = arcReq;
            return this;
        }

        public Builder weaponGuard(int physGuard, int magicGuard, int fireGuard, int lightGuard, int holyGuard) {
            this.scaledWeapon.guard.physGuard = physGuard;
            this.scaledWeapon.guard.magicGuard = magicGuard;
            this.scaledWeapon.guard.fireGuard = fireGuard;
            this.scaledWeapon.guard.lightGuard = lightGuard;
            this.scaledWeapon.guard.holyGuard = holyGuard;
            return this;
        }
    }
}
