package com.ombremoon.enderring.common;

public enum Saturation {
    DEFAULT(0, new int[]{1, 18, 60, 80, 150}, new int[]{0, 25, 75, 90, 110}, new double[]{1.2, -1.2, 1, 1, 1});

    private final int[] stat;
    private final int[] grow;
    private final double[] exp;

    Saturation(int id, int[] stat, int[] grow, double[] exp) {
        this.stat = stat;
        this.grow = grow;
        this.exp = exp;

        checkSize(this.stat, this.grow, this.exp);
    }

    public Saturation getSaturationById(int ordinal) {
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
