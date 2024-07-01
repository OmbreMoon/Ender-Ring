package com.ombremoon.enderring.event.custom;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class FPConsumeEvent extends LivingEvent {
    private final float fpAmount;
    private final AbstractSpell abstractSpell;
    private float newFPAmount;

    public FPConsumeEvent(LivingEntity entity, float fpAmount, AbstractSpell abstractSpell) {
        super(entity);
        this.fpAmount = this.newFPAmount = fpAmount;
        this.abstractSpell = abstractSpell;
    }

    public float getFpAmount() {
        return this.fpAmount;
    }

    public AbstractSpell getAbstractSpell() {
        return this.abstractSpell;
    }

    public float getNewFPAmount() {
        return this.newFPAmount;
    }

    public void setNewFPAmount(float newFPAmount) {
        this.newFPAmount = newFPAmount;
    }
}
