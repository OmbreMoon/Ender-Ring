package com.ombremoon.enderring.common.magic;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractSpell {
    static final Logger LOGGER = Constants.LOG;
    protected static int DEFAULT_CAST_TIME = 1;
    protected static int INSTANT_SPELL_DURATION = 10;
    protected final SpellType<?> spellType;
    protected final MagicType magicType;
    protected final Set<Pair<WeaponScaling, Integer>> requiredStats;
    protected final int fpCost;
    protected final int duration;
    protected final float motionValue;
    private String descriptionId;
    private Level level;
    private ServerPlayerPatch playerPatch;
    private BlockPos blockPos;
    private ScaledWeapon scaledWeapon;
    private ItemStack weapon;
    private int ticks = 0;
    public boolean isInactive = false;
    public boolean init = false;
    public float magicScaling;

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
        //BUILD SPELL EVENT
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

    public float getMotionValue() {
        return motionValue;
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

    public void tick() {
        if (!level.isClientSide) {
            ticks++;
            if (init) {
                this.startSpell();
            } else if (!isInactive) {
                if (this.shouldTickEffect(ticks)) {
                    this.tickSpell();
                }
                if (ticks % duration == 0) {
                    this.endSpell();
                }
            }
        }
    }

    private void startSpell() {
        this.init = false;
        this.onSpellStart(this.playerPatch, this.level, this.blockPos, this.scaledWeapon);
    }

    private void tickSpell() {
        this.onSpellTick(this.playerPatch, this.level, this.blockPos, this.scaledWeapon);
        if (this.requiresConcentration() && !EntityStatusUtil.consumeFP(this.playerPatch.getOriginal(), this.fpCost, true)) {
            this.endSpell();
        }
    }

    private void endSpell() {
        this.onSpellStop(this.playerPatch, this.level, this.blockPos, this.scaledWeapon);
        this.init = false;
        this.isInactive = true;
        this.ticks = 0;
    }

    protected void onSpellTick(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {

    }

    protected void onSpellStart(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        playerPatch.getOriginal().sendSystemMessage(Component.literal("Spell: ").append(Component.translatable(this.getSpellName().getString())));
    }

    protected void onSpellStop(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {

    }

    protected void onHurtTick(ServerPlayerPatch playerPatch, LivingEntity targetEntity, Level level, ScaledWeapon weapon) {

    }

    protected boolean shouldTickEffect(int duration) {
        return true;
    }

    public float getCastValue() {
        return this.magicScaling * this.motionValue / ConfigHandler.STAT_SCALE.get();
    }

    protected float getSorceryScaling() {
        return DamageUtil.getSorceryScaling(this.scaledWeapon, this.playerPatch.getOriginal(), this.weapon.getTag().getInt("WeaponLevel"));
    }

    protected float getIncantationScaling() {
        return DamageUtil.getIncantScaling(this.scaledWeapon, this.playerPatch.getOriginal(), this.weapon.getTag().getInt("WeaponLevel"));
    }

    public void checkHurt(LivingEntity livingEntity) {
        DamageInstance damageInstance = this.createDamageInstance();
        if (damageInstance != null) {
            onHurtTick(this.playerPatch, livingEntity, this.level, this.scaledWeapon);
            livingEntity.hurt(DamageUtil.moddedDamageSource(this.level, damageInstance.damageType()), damageInstance.amount());
        }
    }

    protected DamageInstance createDamageInstance() {
        return null;
    }

    protected float getScaledDamage(float magicScaling) {
        return magicScaling * motionValue / ConfigHandler.STAT_SCALE.get();
    }

    public void initSpell(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ItemStack itemStack, ScaledWeapon scaledWeapon, float magicScaling) {
        this.level = level;
        this.playerPatch = playerPatch;
        this.blockPos = blockPos;
        this.weapon = itemStack;
        this.scaledWeapon = scaledWeapon;
        this.magicScaling = magicScaling;

        EntityStatusUtil.activateSpell(playerPatch.getOriginal(), this);
        this.init = true;
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

    public record DamageInstance(ResourceKey<DamageType> damageType, float amount) {}
}
