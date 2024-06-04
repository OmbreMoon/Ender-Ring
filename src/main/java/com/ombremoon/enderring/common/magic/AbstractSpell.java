package com.ombremoon.enderring.common.magic;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.object.spell.SpellModule;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import javax.annotation.Nullable;
import java.util.*;

/**TODO:
 * Spells cannot get/set fields/have to use a impl of data keys
 * Can be saved in a map that is registered on init*/

public abstract class AbstractSpell {
    protected static int DEFAULT_CAST_TIME = 1;
    protected final SpellType<?> spellType;
    protected final MagicType magicType;
    protected final Set<Pair<WeaponScaling, Integer>> requiredStats;
    protected final int fpCost;
    protected final int duration;
    protected final float motionValue;
    private final Map<SpellModule<?>, Optional<? extends ExpirableValue<?>>> modules = Maps.newHashMap();
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
        SpellInstance currentSpellInstance = EntityStatusUtil.getActiveSpells(player).get(spell);
        if (currentSpellInstance == null) {
            EntityStatusUtil.activateSpell(player, spell, spellInstance);
            spell.onSpellStart(spellInstance, playerPatch, spellInstance.getWeapon(), level, blockPos);
        } else if (currentSpellInstance.updateSpell(spellInstance)) {
            currentSpellInstance.getSpell().onSpellUpdate(currentSpellInstance, playerPatch, currentSpellInstance.getWeapon(), level, blockPos);
        }
    }

    public void clearModules() {
        this.modules.keySet().forEach(spellModule -> {
            this.modules.put(spellModule, Optional.empty());
        });
    }

    public <O> void invalidateModule(SpellModule<O> spellModule) {
        this.setModule(spellModule, Optional.empty());
    }

    public <O> void setModule(SpellModule<O> spellModule, @Nullable O module) {
        this.setModule(spellModule, Optional.ofNullable(module));
    }

    public <O> void setTransientModule(SpellModule<O> spellModule, O module, long ticksValid) {
        this.setSpellModule(spellModule, Optional.of(ExpirableValue.of(module, ticksValid)));
    }

    public <O> void setModule(SpellModule<O> spellModule, Optional<? extends O> module) {
        this.setSpellModule(spellModule, module.map(ExpirableValue::of));
    }

    <O> void setSpellModule(SpellModule<O> spellModule, Optional<? extends ExpirableValue<?>> module) {
        if (this.modules.containsKey(spellModule)) {
            if (module.isPresent() && this.isEmptyCollection(module.get().getValue())) {
                this.invalidateModule(spellModule);
            } else {
                this.modules.put(spellModule, module);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <O> Optional<O> getModule(SpellModule<O> spellModule) {
        Optional<? extends ExpirableValue<?>> optional = this.modules.get(spellModule);
        if (optional == null) {
            throw new IllegalStateException("Tried to fetch unregistered module: " + spellModule);
        } else {
            return (Optional<O>) optional.map(ExpirableValue::getValue);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <O> Optional<O> getSpellModule(SpellModule<O> spellModule) {
        Optional<? extends ExpirableValue<?>> optional = this.modules.get(spellModule);
        return optional == null ? null : (Optional<O>) optional.map(ExpirableValue::getValue);
    }

    public <O> long getTicksUntilInvalid(SpellModule<O> spellModule) {
        Optional<? extends ExpirableValue<?>> optional = this.modules.get(spellModule);
        return optional.map(ExpirableValue::getTimeToLive).orElse(0L);
    }

    private boolean isEmptyCollection(Object pCollection) {
        return pCollection instanceof Collection collection && collection.isEmpty();
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
