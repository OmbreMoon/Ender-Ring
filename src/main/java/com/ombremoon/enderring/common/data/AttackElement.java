package com.ombremoon.enderring.common.data;

import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.WeaponScaling;

import java.util.*;

public class AttackElement {
    public static final int DEFAULT_ID = 10000;
    private static final Map<Integer, AttackElement> attackElementMap = new HashMap<>();

    public static final AttackElement DEFAULT = Builder.create(10000).physical(WeaponScaling.STR, WeaponScaling.DEX).magical(WeaponScaling.INT).fire(WeaponScaling.FAI).lightning(WeaponScaling.DEX).holy(WeaponScaling.FAI).build();
    public static final AttackElement FIRE = Builder.create(10005).physical(WeaponScaling.STR, WeaponScaling.DEX).magical(WeaponScaling.INT).fire(WeaponScaling.STR).lightning(WeaponScaling.DEX).holy(WeaponScaling.FAI).build();
    public static final AttackElement ARCANE = Builder.create(10013).physical(WeaponScaling.STR, WeaponScaling.DEX, WeaponScaling.ARC).magical(WeaponScaling.INT).fire(WeaponScaling.FAI).lightning(WeaponScaling.DEX).holy(WeaponScaling.FAI).build();
    public static final AttackElement UNIQUE_ARCANE = Builder.create(10100).physical(WeaponScaling.STR, WeaponScaling.DEX, WeaponScaling.ARC).magical(WeaponScaling.INT, WeaponScaling.ARC).fire(WeaponScaling.FAI, WeaponScaling.ARC).lightning(WeaponScaling.DEX, WeaponScaling.ARC).holy(WeaponScaling.FAI, WeaponScaling.ARC).build();
    public static final AttackElement ERDSTEEL = Builder.create(12000).physical(WeaponScaling.STR, WeaponScaling.DEX, WeaponScaling.FAI).magical(WeaponScaling.INT).fire(WeaponScaling.FAI).lightning(WeaponScaling.DEX).holy(WeaponScaling.FAI).build();
    public static final AttackElement ELEMENTAL_ERDSTEEL = Builder.create(12005).physical(WeaponScaling.STR, WeaponScaling.DEX, WeaponScaling.FAI).magical(WeaponScaling.INT).fire(WeaponScaling.STR).lightning(WeaponScaling.DEX).holy(WeaponScaling.FAI).build();
    public static final AttackElement STAFF = Builder.create(20000).physical(WeaponScaling.INT).magical(WeaponScaling.INT).fire(WeaponScaling.INT).lightning(WeaponScaling.INT).holy(WeaponScaling.INT).build();
    public static final AttackElement HYBRID_STAFF = Builder.create(20010).physical(WeaponScaling.INT, WeaponScaling.FAI).magical(WeaponScaling.INT, WeaponScaling.FAI).fire(WeaponScaling.INT, WeaponScaling.FAI).lightning(WeaponScaling.INT, WeaponScaling.FAI).holy(WeaponScaling.INT, WeaponScaling.FAI).build();
    public static final AttackElement STAFF_OF_GUILTY = Builder.create(20020).physical(WeaponScaling.FAI).magical(WeaponScaling.FAI).fire(WeaponScaling.FAI).lightning(WeaponScaling.FAI).holy(WeaponScaling.FAI).build();
    public static final AttackElement ALBINAURIC_STAFF = Builder.create(20030).physical(WeaponScaling.INT, WeaponScaling.ARC).magical(WeaponScaling.INT, WeaponScaling.ARC).fire(WeaponScaling.INT, WeaponScaling.ARC).lightning(WeaponScaling.INT, WeaponScaling.ARC).holy(WeaponScaling.INT, WeaponScaling.ARC).build();
    public static final AttackElement SEAL = Builder.create(30000).physical(WeaponScaling.FAI).magical(WeaponScaling.FAI).fire(WeaponScaling.FAI).lightning(WeaponScaling.FAI).holy(WeaponScaling.FAI).build();
    public static final AttackElement GOLDEN_ORDER_SEAL = Builder.create(30010).physical(WeaponScaling.INT, WeaponScaling.FAI).magical(WeaponScaling.INT, WeaponScaling.FAI).fire(WeaponScaling.INT, WeaponScaling.FAI).lightning(WeaponScaling.INT, WeaponScaling.FAI).holy(WeaponScaling.INT, WeaponScaling.FAI).build();
    public static final AttackElement CLAWMARK_SEAL = Builder.create(30020).physical(WeaponScaling.STR, WeaponScaling.FAI).magical(WeaponScaling.STR, WeaponScaling.FAI).fire(WeaponScaling.STR, WeaponScaling.FAI).lightning(WeaponScaling.STR, WeaponScaling.FAI).holy(WeaponScaling.STR, WeaponScaling.FAI).build();
    public static final AttackElement DRAGON_COMMUNION_SEAL = Builder.create(30030).physical(WeaponScaling.FAI, WeaponScaling.ARC).magical(WeaponScaling.FAI, WeaponScaling.ARC).fire(WeaponScaling.FAI, WeaponScaling.ARC).lightning(WeaponScaling.FAI, WeaponScaling.ARC).holy(WeaponScaling.FAI, WeaponScaling.ARC).build();
    public static final AttackElement FRENZIED_FLAME_SEAL = Builder.create(30040).physical(WeaponScaling.STR, WeaponScaling.DEX, WeaponScaling.INT, WeaponScaling.FAI).magical(WeaponScaling.STR, WeaponScaling.DEX, WeaponScaling.INT, WeaponScaling.FAI).fire(WeaponScaling.STR, WeaponScaling.DEX, WeaponScaling.INT, WeaponScaling.FAI).lightning(WeaponScaling.STR, WeaponScaling.DEX, WeaponScaling.INT, WeaponScaling.FAI).holy(WeaponScaling.STR, WeaponScaling.DEX, WeaponScaling.INT, WeaponScaling.FAI).build();
    public static final AttackElement HOLY_WATER_POT = Builder.create(200350).holy(WeaponScaling.FAI).build();
    public static final AttackElement FETID_POT = Builder.create(200330).holy(WeaponScaling.ARC).build();

    //TODO: ADD EVENT
    static {
        registerElementID(DEFAULT);
        registerElementID(FIRE);
        registerElementID(ARCANE);
        registerElementID(UNIQUE_ARCANE);
        registerElementID(ERDSTEEL);
        registerElementID(ELEMENTAL_ERDSTEEL);
        registerElementID(STAFF);
        registerElementID(HYBRID_STAFF);
        registerElementID(STAFF_OF_GUILTY);
        registerElementID(ALBINAURIC_STAFF);
        registerElementID(SEAL);
        registerElementID(GOLDEN_ORDER_SEAL);
        registerElementID(CLAWMARK_SEAL);
        registerElementID(DRAGON_COMMUNION_SEAL);
        registerElementID(FRENZIED_FLAME_SEAL);
        registerElementID(HOLY_WATER_POT);
        registerElementID(FETID_POT);
    }

    public static void registerElementID(AttackElement attackElement) {
        attackElementMap.putIfAbsent(attackElement.getElementId(), attackElement);
    }

    public static AttackElement getElementFromId(int elementId) {
        return attackElementMap.getOrDefault(elementId, DEFAULT);
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
