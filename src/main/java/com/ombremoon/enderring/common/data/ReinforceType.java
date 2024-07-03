package com.ombremoon.enderring.common.data;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.WeaponScaling;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class ReinforceType {
    private static final List<WeaponScaling> MAGICAL = List.of(WeaponScaling.INT, WeaponScaling.FAI, WeaponScaling.ARC);
    private static final List<WeaponScaling> PHYSICAL = List.of(WeaponScaling.STR, WeaponScaling.DEX);

    public static final ReinforceType DEFAULT = Builder.create(CommonClass.customLocation("default"))
            .baseDamage(1.0F).multDamage(0.058F)
            .baseScaling(1.0F).multScaling(PHYSICAL, 0.02F).multScaling(MAGICAL, 0.04F)
            .breakpointAt10(MAGICAL, 0.03F).breakpointAt20(MAGICAL, 0.02F).build();

    public static final ReinforceType HEAVY = Builder.create(CommonClass.customLocation("heavy"))
            .baseDamage(0.95F).multDamage(0.056F)
            .baseScaling(WeaponScaling.STR, 1.8F).baseScaling(MAGICAL, 1.0F).multScaling(WeaponScaling.STR, 0.05F).multScaling(MAGICAL, 0.04F)
            .breakpointAt10(WeaponScaling.STR, 0.04F).breakpointAt10(MAGICAL, 0.03F).breakpointAt20(WeaponScaling.STR, 0.02F).breakpointAt20(MAGICAL, 0.02F).build();

    public static final ReinforceType COLOSSAL_HEAVY = Builder.create(CommonClass.customLocation("colossal_heavy"))
            .baseDamage(0.95F).multDamage(0.05F)
            .baseScaling(WeaponScaling.STR, 1.4F).baseScaling(WeaponScaling.DEX, 0.5F).baseScaling(MAGICAL, 1.0F).multScaling(WeaponScaling.STR, 0.06F).multScaling(WeaponScaling.DEX, 0.03F).multScaling(MAGICAL, 0.04F)
            .breakpointAt10(WeaponScaling.STR, 0.05F).breakpointAt10(MAGICAL, 0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType KEEN = Builder.create(CommonClass.customLocation("keen"))
            .baseDamage(0.95F).multDamage(0.05F)
            .baseScaling(WeaponScaling.STR, 0.5F).baseScaling(WeaponScaling.DEX, 1.5F).baseScaling(MAGICAL, 1.0F).multScaling(WeaponScaling.STR, 0.03F).multScaling(WeaponScaling.DEX, 0.05F).multScaling(MAGICAL, 0.04F)
            .breakpointAt10(PHYSICAL, 0.04F).breakpointAt10(MAGICAL, 0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType QUALITY = Builder.create(CommonClass.customLocation("quality"))
            .baseDamage(0.9F).multDamage(0.046F)
            .baseScaling(1.0F).multScaling(PHYSICAL, 0.05F).multScaling(MAGICAL, 0.04F)
            .breakpointAt10(0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType FIRE = Builder.create(CommonClass.customLocation("fire"))
            .baseDamage(0.8F).multDamage(0.036F)
            .baseScaling(WeaponScaling.STR, 1.3F).baseScaling(WeaponScaling.DEX, 0.5F).baseScaling(MAGICAL, 1.0F).multScaling(WeaponScaling.STR, 0.04F).multScaling(WeaponScaling.DEX, 0.03F).multScaling(MAGICAL, 0.04F)
            .breakpointAt10(0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType FLAME = Builder.create(CommonClass.customLocation("flame"))
            .baseDamage(0.85F).multDamage(0.046F).baseScaling(PHYSICAL, 0.8F).baseScaling(MAGICAL, 1.0F).multScaling(PHYSICAL, 0.05F).multScaling(WeaponScaling.INT, 0.08F).multScaling(WeaponScaling.FAI, 0.07F).multScaling(WeaponScaling.ARC, 0.04F)
            .breakpointAt10(PHYSICAL, 0.04F).breakpointAt10(List.of(WeaponScaling.INT, WeaponScaling.FAI), 0.05F).breakpointAt10(WeaponScaling.ARC, 0.04F).breakpointAt20(PHYSICAL, 0.02F).breakpointAt20(WeaponScaling.INT, -0.1F).breakpointAt20(List.of(WeaponScaling.FAI, WeaponScaling.ARC), 0.02F).build();

    public static final ReinforceType LIGHTNING = Builder.create(CommonClass.customLocation("lightning"))
            .baseDamage(0.8F).multDamage(0.0376F)
            .baseScaling(WeaponScaling.STR, 0.5F).baseScaling(WeaponScaling.DEX, 1.3F).baseScaling(MAGICAL, 1.0F).multScaling(WeaponScaling.STR, 0.03F).multScaling(WeaponScaling.DEX, 0.04F).multScaling(MAGICAL, 0.04F)
            .breakpointAt10(WeaponScaling.DEX, 0.03F).breakpointAt10(MAGICAL, 0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType SACRED = Builder.create(CommonClass.customLocation("sacred"))
            .baseDamage(0.75F).multDamage(0.05F)
            .baseScaling(1.0F).multScaling(PHYSICAL, 0.04F).multScaling(WeaponScaling.INT, 0.04F).multScaling(WeaponScaling.FAI, 0.07F).multScaling(WeaponScaling.ARC, 0.04F)
            .breakpointAt10(PHYSICAL, 0.03F).breakpointAt10(WeaponScaling.INT, 0.03F).breakpointAt10(WeaponScaling.FAI, 0.05F).breakpointAt10(WeaponScaling.ARC, 0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType MAGIC = Builder.create(CommonClass.customLocation("magic"))
            .baseDamage(0.75F).multDamage(0.05F)
            .baseScaling(PHYSICAL, 0.8F).baseScaling(MAGICAL, 1.0F).multScaling(PHYSICAL, 0.03F).multScaling(WeaponScaling.INT, 0.07F).multScaling(List.of(WeaponScaling.FAI, WeaponScaling.ARC), 0.04F)
            .breakpointAt10(PHYSICAL, 0.01F).breakpointAt10(WeaponScaling.INT, 0.05F).breakpointAt10(List.of(WeaponScaling.FAI, WeaponScaling.ARC), 0.03F).breakpointAt20(PHYSICAL, 0.02F).breakpointAt20(WeaponScaling.INT, 0.03F).breakpointAt20(List.of(WeaponScaling.FAI, WeaponScaling.ARC), 0.02F).build();

    public static final ReinforceType COLD = Builder.create(CommonClass.customLocation("cold"))
            .baseDamage(0.85F).multDamage(0.038F)
            .baseScaling(1.0F).multScaling(PHYSICAL, 0.05F).multScaling(WeaponScaling.INT, 0.05F).multScaling(List.of(WeaponScaling.FAI, WeaponScaling.ARC), 0.04F)
            .breakpointAt10(PHYSICAL, 0.03F).breakpointAt10(WeaponScaling.INT, 0.04F).breakpointAt10(List.of(WeaponScaling.FAI, WeaponScaling.ARC), 0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType POISON = Builder.create(CommonClass.customLocation("poison"))
            .baseDamage(0.85F).multDamage(0.052F)
            .baseScaling(1.0F).multScaling(PHYSICAL, 0.04F).multScaling(List.of(WeaponScaling.INT, WeaponScaling.FAI), 0.04F).multScaling(WeaponScaling.ARC, 0.03F)
            .breakpointAt10(PHYSICAL, 0.03F).breakpointAt10(List.of(WeaponScaling.INT, WeaponScaling.FAI), 0.03F).breakpointAt10(WeaponScaling.ARC, 0.01F).breakpointAt20(PHYSICAL, 0.04F).breakpointAt20(List.of(WeaponScaling.INT, WeaponScaling.FAI), 0.04F).build();

    public static final ReinforceType BLOOD = Builder.create(CommonClass.customLocation("blood"))
            .baseDamage(0.85F).multDamage(0.052F)
            .baseScaling(1.0F).multScaling(PHYSICAL, 0.04F).multScaling(List.of(WeaponScaling.INT, WeaponScaling.FAI), 0.04F).multScaling(WeaponScaling.ARC, 0.03F)
            .breakpointAt10(PHYSICAL, 0.03F).breakpointAt10(List.of(WeaponScaling.INT, WeaponScaling.FAI), 0.03F).breakpointAt10(WeaponScaling.ARC, 0.01F).breakpointAt20(PHYSICAL, 0.04F).breakpointAt20(List.of(WeaponScaling.INT, WeaponScaling.FAI), 0.04F).build();

    public static final ReinforceType OCCULT = Builder.create(CommonClass.customLocation("occult"))
            .baseDamage(0.9F).multDamage(0.054F)
            .baseScaling(PHYSICAL, 0.8F).baseScaling(List.of(WeaponScaling.INT, WeaponScaling.FAI), 0.8F).baseScaling(WeaponScaling.ARC, 1.0F).multScaling(0.04F)
            .breakpointAt10(PHYSICAL, 0.02F).breakpointAt10(List.of(WeaponScaling.INT, WeaponScaling.FAI), 0.02F).breakpointAt10(WeaponScaling.ARC, 0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType STANDARD_CATALYST = Builder.create(CommonClass.customLocation("standard_catalyst"))
            .baseDamage(1.0F).multDamage(0.03F).baseScaling(1.0F).multScaling(0.08F).build();

    public static final ReinforceType UNIQUE_CATALYST = Builder.create(CommonClass.customLocation("unique_catalyst"))
            .baseDamage(1.0F).multDamage(0.145F).baseScaling(1.0F).multScaling(0.2F).build();

    public static final ReinforceType UNIQUE = Builder.create(CommonClass.customLocation("unique"))
            .baseDamage(1.0F).multDamage(0.145F).baseScaling(1.0F).multScaling(0.08F).build();

    public static final ReinforceType SMALL_SHIELD = Builder.create(CommonClass.customLocation("small_shield"))
            .baseDamage(1.0F).multDamage(0.058F)
            .baseScaling(1.0F).multScaling(0.04F)
            .breakpointAt10(0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType MEDIUM_SHIELD = Builder.create(CommonClass.customLocation("medium_shield"))
            .baseDamage(1.0F).multDamage(0.058F)
            .baseScaling(1.0F).multScaling(0.04F)
            .breakpointAt10(0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType GREATSHIELD = Builder.create(CommonClass.customLocation("greatshield"))
            .baseDamage(1.0F).multDamage(0.058F)
            .baseScaling(1.0F).multScaling(0.04F)
            .breakpointAt10(0.03F).breakpointAt20(0.02F).build();

    public static final ReinforceType UNIQUE_SHIELD = Builder.create(CommonClass.customLocation("unique_shield"))
            .baseDamage(1.2F).multDamage(0.11F).baseScaling(1.0F).multScaling(0.08F).build();

    public static final ReinforceType COIL_SHIELD = Builder.create(CommonClass.customLocation("coil_shield"))
            .baseDamage(1.2F).multDamage(0.135F).baseScaling(1.0F).multScaling(0.08F).build();

    private static final Map<ResourceLocation, ReinforceType> reinforceTypeMap = new HashMap<>();

    //TODO: ADD EVENT
    static {
        registerType(DEFAULT);
        registerType(HEAVY);
        registerType(COLOSSAL_HEAVY);
        registerType(KEEN);
        registerType(QUALITY);
        registerType(FIRE);
        registerType(FLAME);
        registerType(LIGHTNING);
        registerType(SACRED);
        registerType(MAGIC);
        registerType(COLD);
        registerType(POISON);
        registerType(BLOOD);
        registerType(OCCULT);
        registerType(STANDARD_CATALYST);
        registerType(UNIQUE_CATALYST);
        registerType(UNIQUE);
        registerType(SMALL_SHIELD);
        registerType(MEDIUM_SHIELD);
        registerType(GREATSHIELD);
        registerType(UNIQUE_SHIELD);
        registerType(COIL_SHIELD);
    }

    public static void registerType(ReinforceType type) {
        reinforceTypeMap.putIfAbsent(type.getTypeId(), type);
    }

    public static ReinforceType getTypeFromLocation(ResourceLocation resourceLocation) {
        return reinforceTypeMap.getOrDefault(resourceLocation, DEFAULT);
    }

    private final ResourceLocation typeId;
    private Map<WeaponDamage, Float> baseDamage = new TreeMap<>();
    private Map<WeaponDamage, Float> multDamage = new TreeMap<>();
    private Map<WeaponScaling, Float> baseScaling = new TreeMap<>();
    private Map<WeaponScaling, Float> multScaling = new TreeMap<>();
    private Map<WeaponScaling, Float> breakpoint10Map = new TreeMap<>();
    private Map<WeaponScaling, Float> breakpoint20Map = new TreeMap<>();
    private Set<WeaponScaling> breakpointList = new LinkedHashSet<>();

    public ReinforceType(ResourceLocation typeId) {
        this.typeId = typeId;
    }

    public ResourceLocation getTypeId() {
        return this.typeId;
    }

    public float getReinforceScaleParam(WeaponScaling weaponScaling, int weaponLevel) {
        float baseScaling = this.baseScaling.computeIfAbsent(weaponScaling, scaling -> {
            return 0.0F;
        });
        if (hasBreakpoints(weaponScaling)) {
            if (weaponLevel > 20) {
                return baseScaling + (10 * getMultScaleAmount(weaponScaling, 10)) + (10 * getMultScaleAmount(weaponScaling, 20)) + ((weaponLevel - 20) * getMultScaleAmount(weaponScaling, weaponLevel));
            } else if (weaponLevel > 10) {
                return baseScaling + (10 * getMultScaleAmount(weaponScaling, 10)) + ((weaponLevel - 10) * getMultScaleAmount(weaponScaling, weaponLevel));
            } else {
                return baseScaling + (weaponLevel * getMultScaleAmount(weaponScaling, weaponLevel));
            }
        }
        return baseScaling + (weaponLevel * getMultScaleAmount(weaponScaling, weaponLevel));
    }

    public float getReinforceDamageParam(WeaponDamage weaponDamage, int weaponLevel) {
        float baseDamage = this.baseDamage.get(weaponDamage);
        return baseDamage + (weaponLevel * getMultBaseAmount(weaponDamage));
    }

    private float getMultBaseAmount(WeaponDamage weaponDamage) {
        return this.multDamage.get(weaponDamage);
    }

    private float getMultScaleAmount(WeaponScaling weaponScaling, int weaponLevel) {
        if (hasBreakpoints(weaponScaling)) {
            if (weaponLevel > 20) {
                return this.breakpoint20Map.getOrDefault(weaponScaling, this.breakpoint10Map.get(weaponScaling));
            } else if (weaponLevel > 10) {
                return this.breakpoint10Map.getOrDefault(weaponScaling, this.multScaling.get(weaponScaling));
            } else {
                return this.multScaling.getOrDefault(weaponScaling, 0.0F);
            }
        } else {
            return this.multScaling.getOrDefault(weaponScaling, 0.0F);
        }
    }

    private boolean hasBreakpoints(WeaponScaling weaponScaling) {
        return this.breakpointList.contains(weaponScaling);
    }

    public ReinforceType copy() {
        ReinforceType reinforceType = new ReinforceType(this.typeId);
        reinforceType.baseDamage = this.baseDamage;
        reinforceType.multDamage = this.multDamage;
        reinforceType.baseScaling = this.baseScaling;
        reinforceType.multScaling = this.multScaling;
        reinforceType.breakpoint10Map = this.breakpoint10Map;
        reinforceType.breakpoint20Map = this.breakpoint20Map;
        reinforceType.breakpointList = this.breakpointList;
        return reinforceType;
    }

    static class Builder {
        private final ReinforceType reinforceType;

        private Builder(ResourceLocation resourceLocation) {
            this.reinforceType = new ReinforceType(resourceLocation);
        }

        public static Builder create(ResourceLocation resourceLocation) {
            return new Builder(resourceLocation);
        }

        public ReinforceType build() {
            return this.reinforceType.copy();
        }

        public Builder baseDamage(float damage) {
            for (WeaponDamage weaponDamage : WeaponDamage.values()) {
                this.reinforceType.baseDamage.put(weaponDamage, damage);
            }
            return this;
        }

        public Builder baseDamage(WeaponDamage weaponDamage, float damage) {
            this.reinforceType.baseDamage.put(weaponDamage, damage);
            return this;
        }

        public Builder multDamage(float damage) {
            for (WeaponDamage weaponDamage : WeaponDamage.values()) {
                this.reinforceType.multDamage.put(weaponDamage, damage);
            }
            return this;
        }

        public final Builder multDamage(WeaponDamage weaponDamage, float damage) {
            this.reinforceType.multDamage.put(weaponDamage, damage);
            return this;
        }

        public Builder baseScaling(WeaponScaling weaponScaling, float scaling) {
            this.reinforceType.baseScaling.put(weaponScaling, scaling);
            return this;
        }

        public Builder baseScaling(float scaling) {
            for (WeaponScaling weaponScaling : WeaponScaling.values()) {
                this.reinforceType.baseScaling.put(weaponScaling, scaling);
            }
            return this;
        }

        public Builder baseScaling(List<WeaponScaling> scalingList, float scaling) {
            for (WeaponScaling weaponScaling : scalingList) {
                this.reinforceType.baseScaling.put(weaponScaling, scaling);
            }
            return this;
        }

        public Builder multScaling(WeaponScaling weaponScaling, float scaling) {
            this.reinforceType.multScaling.put(weaponScaling, scaling);
            return this;
        }

        public Builder multScaling(float scaling) {
            for (WeaponScaling weaponScaling : WeaponScaling.values()) {
                this.reinforceType.multScaling.put(weaponScaling, scaling);
            }
            return this;
        }

        public Builder multScaling(List<WeaponScaling> scalingList, float scaling) {
            for (WeaponScaling weaponScaling : scalingList) {
                this.reinforceType.multScaling.put(weaponScaling, scaling);
            }
            return this;
        }

        public Builder breakpointAt10(float newMult) {
            for (WeaponScaling weaponScaling : WeaponScaling.values()) {
                this.reinforceType.breakpoint10Map.put(weaponScaling, newMult);
                this.reinforceType.breakpointList.add(weaponScaling);
            }
            return this;
        }

        public Builder breakpointAt10(WeaponScaling weaponScaling, float newMult) {
            this.reinforceType.breakpoint10Map.put(weaponScaling, newMult);
            this.reinforceType.breakpointList.add(weaponScaling);
            return this;
        }

        public Builder breakpointAt10(List<WeaponScaling> scalingList, float newMult) {
            for (WeaponScaling weaponScaling : scalingList) {
                this.reinforceType.breakpoint10Map.put(weaponScaling, newMult);
                this.reinforceType.breakpointList.add(weaponScaling);
            }
            return this;
        }

        public Builder breakpointAt20(float newMult) {
            for (WeaponScaling weaponScaling : WeaponScaling.values()) {
                this.reinforceType.breakpoint20Map.put(weaponScaling, newMult);
                this.reinforceType.breakpointList.add(weaponScaling);
            }
            return this;
        }

        public Builder breakpointAt20(WeaponScaling weaponScaling, float newMult) {
            this.reinforceType.breakpoint20Map.put(weaponScaling, newMult);
            this.reinforceType.breakpointList.add(weaponScaling);
            return this;
        }

        public Builder breakpointAt20(List<WeaponScaling> scalingList, float newMult) {
            for (WeaponScaling weaponScaling : scalingList) {
                this.reinforceType.breakpoint20Map.put(weaponScaling, newMult);
                this.reinforceType.breakpointList.add(weaponScaling);
            }
            return this;
        }
    }
}
