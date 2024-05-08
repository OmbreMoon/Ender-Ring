package com.ombremoon.enderring.common;

public enum Saturation {
    DEFAULT(new int[]{1, 18, 60, 80, 150}, new int[]{0, 25, 75, 90, 110}, new double[]{1.2, -1.2, 1, 1, 1}),
    HEAVY(new int[]{1, 20, 60, 80, 150}, new int[]{0, 35, 75, 90, 110}, new double[]{1.2, -1.2, 1, 1, 1}),
    KEEN(new int[]{1, 20, 60, 80, 150}, new int[]{0, 35, 75, 90, 110}, new double[]{1.2, -1.2, 1, 1, 1}),
    THROWING(new int[]{1, 20, 30, 50, 99}, new int[]{0, 30, 62, 82, 100}, new double[]{1, 1, 1, 1, 1}),
    ELEMENTAL(new int[]{1, 20, 50, 80, 99}, new int[]{0, 40, 80, 95, 100}, new double[]{1, 1, 1, 1, 1});

    private final int[] stat;
    private final int[] grow;
    private final double[] exp;

    Saturation(int[] stat, int[] grow, double[] exp) {
        this.stat = stat;
        this.grow = grow;
        this.exp = exp;

        checkSize(this.stat, this.grow, this.exp);
    }

    public static Saturation getSaturationById(int ordinal) {
        return Saturation.values()[ordinal];
    }

    public int[] getStat() {
        return this.stat;
    }

    public double[] getExp() {
        return this.exp;
    }

    public int[] getGrow() {
        return this.grow;
    }

    private void checkSize(int[] stat, int[] grow, double[] exp) {
        if (stat.length != 5 || grow.length != 5 || exp.length != 5) {
            throw new IllegalStateException("Stat/Grow/Exp must all be size 5");
        }
    }
}
