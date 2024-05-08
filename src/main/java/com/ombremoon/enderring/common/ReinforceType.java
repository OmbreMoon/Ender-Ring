package com.ombremoon.enderring.common;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.data.WeaponDamage;
import com.ombremoon.enderring.common.data.WeaponScaling;
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

    public static final ReinforceType UNIQUE = Builder.create(CommonClass.customLocation("unique"))
            .baseDamage(1.0F).multDamage(0.145F).baseScaling(1.0F).multScaling(0.08F).build();

    private static final Map<ResourceLocation, ReinforceType> reinforceTypeMap = new HashMap<>();

    static {
        registerType(DEFAULT);
        registerType(HEAVY);
        registerType(UNIQUE);
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
                return this.breakpoint20Map.computeIfAbsent(weaponScaling, scaling -> {
                    return this.breakpoint10Map.get(weaponScaling);
                });
            } else if (weaponLevel > 10) {
                return this.breakpoint10Map.computeIfAbsent(weaponScaling, scaling -> {
                    return this.multScaling.get(weaponScaling);
                });
            } else {
                return this.multScaling.computeIfAbsent(weaponScaling, scaling -> {
                    return 0.0F;
                });
            }
        } else {
            return this.multScaling.computeIfAbsent(weaponScaling, scaling -> {
                return 0.0F;
            });
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
