package com.ombremoon.enderring.common.magic;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractSpell {
    protected static int DEFAULT_CAST_TIME = 1;
    protected final SpellType<?> spellType;
    protected final MagicType magicType;
    protected final Set<Pair<WeaponScaling, Integer>> requiredStats;
    protected final int fpCost;
    protected final int duration;
    protected final float motionValue;
    private String descriptionId;

    public static Builder<AbstractSpell> createBuilder() {
        return new Builder<>();
    }

    public AbstractSpell(SpellType<?> spellType, Builder<? extends AbstractSpell> builder) {
        this.spellType = spellType;
        this.magicType = builder.magicType;
        this.requiredStats = builder.requiredStats;
        this.fpCost = builder.fpCost;
        this.duration = builder.duration;
        this.motionValue = builder.motionValue;
    }

    public SpellType<?> getSpellType() {
        return this.spellType;
    }

    public MagicType getMagicType() {
        return this.magicType;
    }

    public int getFpCost() {
        return this.fpCost;
    }

    public int getDuration() {
        return this.duration;
    }

    public Set<Pair<WeaponScaling, Integer>> getRequiredStats() {
        return this.requiredStats;
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

    public ResourceLocation getSpellTexture() {
        ResourceLocation name = this.getId();
        return CommonClass.customLocation("textures/gui/spells/" + name.getPath() + ".png");
    }

    public Component getSpellName() {
        return Component.translatable(this.getDescriptionId());
    }

    public static SpellType<?> getSpellByName(ResourceLocation resourceLocation) {
        for (RegistryObject<SpellType<?>> registryObject : SpellInit.SPELL_TYPE.getEntries()) {
            if (registryObject.getId().equals(resourceLocation)) {
                return registryObject.get();
            }
        }
        return null;
    }

    public abstract void tickSpellEffect(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos);

    public abstract void onSpellStart(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos);

    public void onSpellUpdate(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {

    }

    public void onSpellStop(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {

    }

    public boolean isEffectTick(int duration) {
        return true;
    }

    public void activateSpellEffect(SpellInstance spellInstance, ServerPlayerPatch playerPatch, Level level, BlockPos blockPos) {
        AbstractSpell spell = spellInstance.getSpell();
        Player player = playerPatch.getOriginal();
        SpellInstance currentSpellInstance = PlayerStatusUtil.getActiveSpells(player).get(spell);
        if (currentSpellInstance == null) {
            PlayerStatusUtil.activateSpell(player, spell, spellInstance);
            spell.onSpellStart(spellInstance, playerPatch, spellInstance.getWeapon(), level, blockPos);
        } else if (currentSpellInstance.updateSpell(spellInstance)) {
            currentSpellInstance.getSpell().onSpellUpdate(currentSpellInstance, playerPatch, currentSpellInstance.getWeapon(), level, blockPos);
        }
    }

    public static class Builder<T extends AbstractSpell> {
        protected MagicType magicType;
        protected Set<Pair<WeaponScaling, Integer>> requiredStats = new LinkedHashSet<>();
        protected int duration = 1;
        protected int fpCost;
        protected float motionValue;

        public Builder<T> setMagicType(MagicType magicType) {
            this.magicType = magicType;
            return this;
        }

        public Builder<T> setFPCost(int fpCost) {
            this.fpCost = fpCost;
            return this;
        }

        public Builder<T> setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder<T> setRequirements(WeaponScaling weaponScaling, int statReq) {
            this.createReqList(weaponScaling, statReq);
            return this;
        }

        public Builder<T> setMotionValue(float motionValue) {
            this.motionValue = motionValue;
            return this;
        }

        protected void createReqList(WeaponScaling statType, int requiredStat) {
            if (statType == WeaponScaling.STR || statType == WeaponScaling.DEX) {
                throw new IllegalArgumentException();
            }
            this.requiredStats.add(Pair.of(statType, requiredStat));
        }
    }
}
