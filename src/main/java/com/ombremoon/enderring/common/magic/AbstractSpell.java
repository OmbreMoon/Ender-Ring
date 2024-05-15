package com.ombremoon.enderring.common.magic;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpell {
    protected static int DEFAULT_CAST_TIME = 1;
    private final SpellType<?> spellType;
    private final MagicType magicType;
    private ScaledWeapon weapon;
    private final int duration;
    private final int requiredFP;
    private final int requiredInt;
    private final int requiredFai;
    private final int requiredArc;
    private String descriptionId;
    private final List<Integer> requiredStats = new ArrayList<>(3);

    public AbstractSpell(SpellType<?> spellType, MagicType magicType, int duration, int requiredFP, int requiredInt, int requiredFai, int requiredArc) {
        this.spellType = spellType;
        this.magicType = magicType;
        this.duration = duration;
        this.requiredFP = requiredFP;
        this.requiredInt = requiredInt;
        this.requiredFai = requiredFai;
        this.requiredArc = requiredArc;

        this.createReqList(requiredInt, requiredFai, requiredArc);
    }

    public SpellType<?> getSpellType() {
        return this.spellType;
    }

    public MagicType getMagicType() {
        return this.magicType;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getRequiredFP() {
        return this.requiredFP;
    }

    public int getRequiredInt() {
        return this.requiredInt;
    }

    public int getRequiredFai() {
        return this.requiredFai;
    }

    public int getRequiredArc() {
        return this.requiredArc;
    }

    public List<Integer> getRequiredStats() {
        return this.requiredStats;
    }

    public ScaledWeapon getWeapon() {
        return this.weapon;
    }

    public void setWeapon(ScaledWeapon weapon) {
        this.weapon = weapon;
    }

    public boolean isInstantSpell() {
        return true;
    }

    public boolean requiresConcentration() {
        return false;
    }

    public abstract int getCastTime();

    public ResourceLocation getId() {
        return SpellInit.REGISTRY.get().getKey(this.spellType);
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("spell", this.getId());
        }
        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public Component getSpellName() {
        return Component.translatable(this.getDescriptionId());
    }

    public abstract void tickSpellEffect(Player player, Level level, BlockPos blockPos);

    public abstract void onSpellStart(SpellInstance spellInstance, Player player, Level level, BlockPos blockPos);

    public void onSpellUpdate(SpellInstance spellInstance, Player player, Level level, BlockPos blockPos) {

    }

    public void onSpellStop(SpellInstance spellInstance, Player player, Level level, BlockPos blockPos) {

    }

    public boolean isEffectTick(int duration) {
        return true;
    }

    public void activateSpellEffect(SpellInstance spellInstance, Player player, Level level, BlockPos blockPos) {
        AbstractSpell spell = spellInstance.getSpell();
        SpellInstance currentSpellInstance = PlayerStatusUtil.getActiveSpells(player).get(spell);
        if (currentSpellInstance == null) {
            PlayerStatusUtil.activateSpell(player, spell, spellInstance);
            spell.onSpellStart(spellInstance, player, level, blockPos);
        } else if (currentSpellInstance.updateSpell(spellInstance)) {
            currentSpellInstance.getSpell().onSpellUpdate(currentSpellInstance, player, level, blockPos);
        }
    }

    private void createReqList(int requiredInt, int requiredFai, int requiredArc) {
        this.requiredStats.add(requiredInt);
        this.requiredStats.add(requiredFai);
        this.requiredStats.add(requiredArc);
    }
}
