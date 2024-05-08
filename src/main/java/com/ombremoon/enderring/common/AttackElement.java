package com.ombremoon.enderring.common;

import com.ombremoon.enderring.common.data.WeaponDamage;
import com.ombremoon.enderring.common.data.WeaponScaling;

import java.util.*;

public class AttackElement {
    private static final Map<Integer, AttackElement> attackElementMap = new HashMap<>();

    public static final AttackElement ID0 = Builder.create(10000).physical(WeaponScaling.STR, WeaponScaling.DEX).magical(WeaponScaling.INT).fire(WeaponScaling.FAI).lightning(WeaponScaling.DEX).holy(WeaponScaling.FAI).build();

    static {
        registerElementID(ID0);
    }

    public static void registerElementID(AttackElement attackElement) {
        attackElementMap.putIfAbsent(attackElement.getElementId(), attackElement);
    }

    public static AttackElement getElementFromId(int elementId) {
        return attackElementMap.getOrDefault(elementId, ID0);
    }

    private final int elementId;
    private Map<WeaponDamage, List<WeaponScaling>> listMap = new TreeMap<>();

    public AttackElement(int elementId) {
        this.elementId = elementId;
    }

    public int getElementId() {
        return this.elementId;
    }

    public Map<WeaponDamage, List<WeaponScaling>> getListMap() {
        return this.listMap;
    }

    public AttackElement copy() {
        AttackElement attackElement = new AttackElement(this.elementId);
        attackElement.listMap = this.listMap;
        return attackElement;
    }

    static class Builder {
        private final AttackElement attackElement;

        private Builder(int elementId) {
            this.attackElement = new AttackElement(elementId);
        }

        public static AttackElement.Builder create(int elementId) {
            return new AttackElement.Builder(elementId);
        }

        public AttackElement build() {
            return this.attackElement.copy();
        }

        public Builder physical(WeaponScaling... weaponScalings) {
            List<WeaponScaling> scalingList = new ArrayList<>(Arrays.asList(weaponScalings));
            this.attackElement.listMap.put(WeaponDamage.PHYSICAL, scalingList);
            return this;
        }

        public Builder magical(WeaponScaling... weaponScalings) {
            List<WeaponScaling> scalingList = new ArrayList<>(Arrays.asList(weaponScalings));
            this.attackElement.listMap.put(WeaponDamage.MAGICAL, scalingList);
            return this;
        }

        public Builder fire(WeaponScaling... weaponScalings) {
            List<WeaponScaling> scalingList = new ArrayList<>(Arrays.asList(weaponScalings));
            this.attackElement.listMap.put(WeaponDamage.FIRE, scalingList);
            return this;
        }

        public Builder lightning(WeaponScaling... weaponScalings) {
            List<WeaponScaling> scalingList = new ArrayList<>(Arrays.asList(weaponScalings));
            this.attackElement.listMap.put(WeaponDamage.LIGHTNING, scalingList);
            return this;
        }

        public Builder holy(WeaponScaling... weaponScalings) {
            List<WeaponScaling> scalingList = new ArrayList<>(Arrays.asList(weaponScalings));
            this.attackElement.listMap.put(WeaponDamage.HOLY, scalingList);
            return this;
        }
    }
}
