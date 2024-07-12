package com.ombremoon.enderring.common.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

public class ArmorResistance implements INBTSerializable<CompoundTag> {
    protected Negation negation = new Negation();
    protected Resistance resistance = new Resistance();

    public Negation getNegation() { return this.negation; }
    public Resistance getResistance() { return this.resistance; }

    public static class Negation implements INBTSerializable<CompoundTag> {
        private float physNegation;
        private float strikeNegation;
        private float slashNegation;
        private float pierceNegation;
        private float magicNegation;
        private float fireNegation;
        private float lightNegation;
        private float holyNegation;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putFloat("PhysNegation", this.physNegation);
            nbt.putFloat("StrikeNegation", this.physNegation);
            nbt.putFloat("SlashNegation", this.physNegation);
            nbt.putFloat("PierceNegation", this.physNegation);
            nbt.putFloat("MagicNegation", this.physNegation);
            nbt.putFloat("FireNegation", this.physNegation);
            nbt.putFloat("LightNegation", this.physNegation);
            nbt.putFloat("HolyNegation", this.physNegation);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (nbt.contains("PhysNegation", 99)) {
                this.physNegation = nbt.getInt("PhysNegation");
            }
            if (nbt.contains("StrikeNegation", 99)) {
                this.strikeNegation = nbt.getInt("StrikeNegation");
            }
            if (nbt.contains("SlashNegation", 99)) {
                this.slashNegation = nbt.getInt("SlashNegation");
            }
            if (nbt.contains("PierceNegation", 99)) {
                this.pierceNegation = nbt.getInt("PierceNegation");
            }
            if (nbt.contains("MagicNegation", 99)) {
                this.magicNegation = nbt.getInt("MagicNegation");
            }
            if (nbt.contains("FireNegation", 99)) {
                this.fireNegation = nbt.getInt("FireNegation");
            }
            if (nbt.contains("LightNegation", 99)) {
                this.lightNegation = nbt.getInt("LightNegation");
            }
            if (nbt.contains("HolyNegation", 99)) {
                this.holyNegation = nbt.getInt("HolyNegation");
            }
        }

        public JsonObject toJsonObject() {
            JsonObject jsonObject = new JsonObject();
            Preconditions.checkArgument(this.physNegation >= 0, "Physical negation must be greater than or equal to 0");
            Preconditions.checkArgument(this.strikeNegation >= 0, "Strike negation must be greater than or equal to 0");
            Preconditions.checkArgument(this.slashNegation >= 0, "Slash negation must be greater than or equal to 0");
            Preconditions.checkArgument(this.pierceNegation >= 0, "Pierce negation must be greater than or equal to 0");
            Preconditions.checkArgument(this.magicNegation >= 0, "Magical negation must be greater than or equal to 0");
            Preconditions.checkArgument(this.fireNegation >= 0, "Fire negation must be greater than or equal to 0");
            Preconditions.checkArgument(this.lightNegation >= 0, "Lightning negation must be greater than or equal to 0");
            Preconditions.checkArgument(this.holyNegation >= 0, "Holy negation must be greater than or equal to 0");
            if (this.physNegation > 0) jsonObject.addProperty("physNegation", this.physNegation);
            if (this.strikeNegation > 0) jsonObject.addProperty("strikeNegation", this.strikeNegation);
            if (this.slashNegation > 0) jsonObject.addProperty("slashNegation", this.slashNegation);
            if (this.pierceNegation > 0) jsonObject.addProperty("pierceNegation", this.pierceNegation);
            if (this.magicNegation > 0) jsonObject.addProperty("magicNegation", this.magicNegation);
            if (this.fireNegation > 0) jsonObject.addProperty("fireNegation", this.fireNegation);
            if (this.lightNegation > 0) jsonObject.addProperty("lightNegation", this.lightNegation);
            if (this.holyNegation > 0) jsonObject.addProperty("holyNegation", this.holyNegation);
            return jsonObject;
        }

        public float getPhysNegation() {
            return this.physNegation;
        }

        public float getStrikeNegation() {
            return this.strikeNegation;
        }

        public float getSlashNegation() {
            return this.slashNegation;
        }

        public float getPierceNegation() {
            return this.pierceNegation;
        }

        public float getMagicNegation() {
            return this.magicNegation;
        }

        public float getFireNegation() {
            return this.fireNegation;
        }

        public float getLightNegation() {
            return this.lightNegation;
        }

        public float getHolyNegation() {
            return this.holyNegation;
        }

        public Map<Attribute, Float> getNegationMap() {
            Map<Attribute, Float> negationMap = new Object2FloatOpenHashMap<>();
            if (this.physNegation > 0) negationMap.put(EntityAttributeInit.PHYS_NEGATE.get(), this.physNegation);
            if (this.strikeNegation > 0) negationMap.put(EntityAttributeInit.STRIKE_NEGATE.get(), this.strikeNegation);
            if (this.slashNegation > 0) negationMap.put(EntityAttributeInit.SLASH_NEGATE.get(), this.slashNegation);
            if (this.pierceNegation > 0) negationMap.put(EntityAttributeInit.PIERCE_NEGATE.get(), this.pierceNegation);
            if (this.magicNegation > 0) negationMap.put(EntityAttributeInit.MAGIC_NEGATE.get(), this.magicNegation);
            if (this.fireNegation > 0) negationMap.put(EntityAttributeInit.FIRE_NEGATE.get(), this.fireNegation);
            if (this.lightNegation > 0) negationMap.put(EntityAttributeInit.LIGHT_NEGATE.get(), this.lightNegation);
            if (this.holyNegation > 0) negationMap.put(EntityAttributeInit.HOLY_NEGATE.get(), this.holyNegation);
            return negationMap;
        }

        public Negation copy() {
            Negation negation = new Negation();
            negation.physNegation = this.physNegation;
            negation.strikeNegation = this.strikeNegation;
            negation.slashNegation = this.slashNegation;
            negation.pierceNegation = this.pierceNegation;
            negation.magicNegation = this.magicNegation;
            negation.fireNegation = this.fireNegation;
            negation.lightNegation = this.lightNegation;
            negation.holyNegation = this.holyNegation;
            return negation;
        }
    }

    public static class Resistance implements INBTSerializable<CompoundTag> {
        private int immunity;
        private int robustness;
        private int focus;
        private int vitality;

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("Immunity", this.immunity);
            nbt.putInt("Robustness", this.robustness);
            nbt.putInt("Focus", this.focus);
            nbt.putInt("Vitality", this.vitality);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            if (nbt.contains("Immunity", 99)) {
                this.immunity = nbt.getInt("Immunity");
            }
            if (nbt.contains("Robustness", 99)) {
                this.robustness = nbt.getInt("Robustness");
            }
            if (nbt.contains("Focus", 99)) {
                this.focus = nbt.getInt("Focus");
            }
            if (nbt.contains("Vitality", 99)) {
                this.vitality = nbt.getInt("Vitality");
            }
        }

        public JsonObject toJsonObject() {
            Preconditions.checkArgument(this.immunity >= 0, "Immunity must be greater than or equal to 0");
            Preconditions.checkArgument(this.robustness >= 0, "Robustness must be greater than or equal to 0");
            Preconditions.checkArgument(this.focus >= 0, "Focus must be greater than or equal to 0");
            Preconditions.checkArgument(this.vitality >= 0, "Vitality must be greater than or equal to 0");
            JsonObject jsonObject = new JsonObject();
            if (this.immunity > 0) jsonObject.addProperty("immunity", this.immunity);
            if (this.robustness > 0) jsonObject.addProperty("robustness", this.robustness);
            if (this.focus > 0) jsonObject.addProperty("focus", this.focus);
            if (this.vitality > 0) jsonObject.addProperty("vitality", this.vitality);
            return jsonObject;
        }

        public int getImmunity() {
            return this.immunity;
        }

        public int getRobustness() {
            return this.robustness;
        }

        public int getFocus() {
            return this.focus;
        }

        public int getVitality() {
            return this.vitality;
        }

        public Map<Attribute, Integer> getResistanceMap() {
            Map<Attribute, Integer> negationMap = new Object2IntOpenHashMap<>();
            if (this.immunity > 0) negationMap.put(EntityAttributeInit.IMMUNITY.get(), this.immunity);
            if (this.robustness > 0) negationMap.put(EntityAttributeInit.ROBUSTNESS.get(), this.robustness);
            if (this.focus > 0) negationMap.put(EntityAttributeInit.FOCUS.get(), this.focus);
            if (this.vitality > 0) negationMap.put(EntityAttributeInit.VITALITY.get(), this.vitality);
            return negationMap;
        }

        public Resistance copy() {
            Resistance resistance = new Resistance();
            resistance.immunity = this.immunity;
            resistance.robustness = this.robustness;
            resistance.focus = this.focus;
            resistance.vitality = this.vitality;
            return resistance;
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("Negation", this.negation.serializeNBT());
        nbt.put("Resistance", this.resistance.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("Negation", 10)) {
            this.negation.deserializeNBT(nbt.getCompound("Negation"));
        }
        if (nbt.contains("Resistance", 10)) {
            this.resistance.deserializeNBT(nbt.getCompound("Resistance"));
        }
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("negation", this.negation.toJsonObject());
        jsonObject.add("resistance", this.resistance.toJsonObject());
        return jsonObject;
    }

    public static ArmorResistance create(CompoundTag nbt) {
        ArmorResistance armorResistance = new ArmorResistance();
        armorResistance.deserializeNBT(nbt);
        return armorResistance;
    }

    public ArmorResistance copy() {
        ArmorResistance armorResistance = new ArmorResistance();
        armorResistance.negation = this.negation.copy();
        armorResistance.resistance = this.resistance.copy();
        return armorResistance;
    }

    public static class Builder {
        private final ArmorResistance armorResistance;

        private Builder() {
            this.armorResistance = new ArmorResistance();
        }

        public static Builder create() {
            return new Builder();
        }

        public ArmorResistance build() {
            return this.armorResistance.copy();
        }

        public Builder negation(float physNeg, float strikeNeg, float slashNeg, float pierceNeg, float magicNeg, float fireNeg, float lightNeg, float holyNeg) {
            this.armorResistance.negation.physNegation = physNeg;
            this.armorResistance.negation.strikeNegation = strikeNeg;
            this.armorResistance.negation.slashNegation = slashNeg;
            this.armorResistance.negation.pierceNegation = pierceNeg;
            this.armorResistance.negation.magicNegation = magicNeg;
            this.armorResistance.negation.fireNegation = fireNeg;
            this.armorResistance.negation.lightNegation = lightNeg;
            this.armorResistance.negation.holyNegation = holyNeg;
            return this;
        }

        public Builder resistance(int immunity, int robustness, int focus, int vitality) {
            this.armorResistance.resistance.immunity = immunity;
            this.armorResistance.resistance.robustness = robustness;
            this.armorResistance.resistance.focus = focus;
            this.armorResistance.resistance.vitality = vitality;
            return this;
        }
    }
}
