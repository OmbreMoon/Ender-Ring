package com.ombremoon.enderring.common.data;

public enum Saturations implements Saturation {
    DEFAULT(new int[]{1, 18, 60, 80, 150}, new int[]{0, 25, 75, 90, 110}, new double[]{1.2, -1.2, 1, 1, 1}),
    HEAVY(new int[]{1, 20, 60, 80, 150}, new int[]{0, 35, 75, 90, 110}, new double[]{1.2, -1.2, 1, 1, 1}),
    KEEN(new int[]{1, 20, 60, 80, 150}, new int[]{0, 35, 75, 90, 110}, new double[]{1.2, -1.2, 1, 1, 1}),
    THROWING_PHYSICAL(new int[]{1, 20, 30, 50, 99}, new int[]{0, 30, 62, 82, 100}, new double[]{1, 1, 1, 1, 1}),
    ELEMENTAL(new int[]{1, 20, 50, 80, 99}, new int[]{0, 40, 80, 95, 100}, new double[]{1, 1, 1, 1, 1}),
    SERPENT_BOW(new int[]{1, 20, 40, 80, 99}, new int[]{0, 75, 225, 270, 300}, new double[]{1, 1, 1, 1, 1}),
    STATUS_EFFECT(new int[]{1, 25, 45, 60, 99}, new int[]{0, 10, 75, 90, 100}, new double[]{1, 1, 1, 1, 1}),
    OCCULT(new int[]{1, 20, 60, 80, 150}, new int[]{0, 35, 75, 90, 110}, new double[]{1.2, -1.2, 1, 1, 1}),
    QUALITY(new int[]{1, 16, 60, 80, 99}, new int[]{0, 25, 75, 90, 110}, new double[]{1.2, -1.2, 1, 1, 1}),
    THROWING_STATUS(new int[]{1, 15, 30, 50, 99}, new int[]{0, 10, 50, 65, 70}, new double[]{1, 1, 1, 1, 1}),
    SPELL_STATUS(new int[]{1, 25, 45, 60, 99}, new int[]{0, 6, 15, 18, 20}, new double[]{1, 1, 1, 1, 1}),
    HYBRID_CATALYST(new int[]{1, 15, 30, 45, 99}, new int[]{0, 10, 55, 75, 100}, new double[]{1, 1, 1, 1, 1}),
    DEMI_HUMAN_QUEEN(new int[]{1, 20, 40, 80, 99}, new int[]{0, 40, 60, 85, 100}, new double[]{1, 1, 1, 1, 1}),
    PURE_CATALYST_BACK(new int[]{1, 25, 60, 80, 99}, new int[]{0, 25, 65, 95, 100}, new double[]{1, 1, 1, 1, 1}),
    PURE_CATALYST_FRONT(new int[]{1, 18, 60, 80, 99}, new int[]{0, 20, 75, 90, 100}, new double[]{1, 1, 1, 1, 1}),
    RUNE_DEFENSE(new int[]{1, 150, 170, 240, 792}, new int[]{40, 100, 120, 135, 155}, new double[]{1, 1, 1, 1, 1}),
    PHYSICAL_DEFENSE(new int[]{0, 30, 40, 60, 99}, new int[]{0, 10, 15, 30, 40}, new double[]{1, 1, 1, 1, 1}),
    MAGICAL_DEFENSE(new int[]{0, 20, 35, 60, 99}, new int[]{0, 40, 50, 60, 70}, new double[]{1, 1, 1, 1, 1}),
    FIRE_DEFENSE(new int[]{0, 30, 40, 60, 99}, new int[]{0, 20, 40, 60, 70}, new double[]{1, 1, 1, 1, 1}),
    HOLY_DEFENSE(new int[]{0, 20, 35, 60, 99}, new int[]{0, 40, 50, 60, 70}, new double[]{1, 1, 1, 1, 1}),
    RUNE_RESISTANCE(new int[]{1, 71, 111, 161, 713}, new int[]{75, 105, 145, 160, 180}, new double[]{1, 1, 1, 1, 1}),
    RESISTANCE(new int[]{0, 30, 40, 60, 99}, new int[]{0, 0, 30, 40, 50}, new double[]{1, 1, 1, 1, 1}),
    VITALITY(new int[]{0, 15, 40, 60, 99}, new int[]{0, 15, 30, 40, 50}, new double[]{1, 1, 1, 1, 1});

    final int[] stat;
    final int[] grow;
    final double[] exp;
    final int id;

    Saturations(int[] stat, int[] grow, double[] exp) {
        this.id = Saturation.ENUM_MANAGER.assign(this);
        this.stat = stat;
        this.grow = grow;
        this.exp = exp;

        checkSize(this.stat, this.grow, this.exp);
    }

    public static Saturations getSaturationById(int ordinal) {
        return Saturations.values()[ordinal];
    }

    @Override
    public int[] getStat() {
        return this.stat;
    }

    @Override
    public double[] getExp() {
        return this.exp;
    }

    @Override
    public int[] getGrow() {
        return this.grow;
    }

    private void checkSize(int[] stat, int[] grow, double[] exp) {
        if (stat.length != 5 || grow.length != 5 || exp.length != 5) {
            throw new IllegalStateException("Stat/Grow/Exp must all be size 5");
        }
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
