package com.ombremoon.enderring.common.magic;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.event.custom.EventFactory;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractSpell {
    protected static final Logger LOGGER = Constants.LOG;
    protected static int DEFAULT_CAST_TIME = 1;
    protected static int INSTANT_SPELL_DURATION = 10;
    private final SpellType<?> spellType;
    private final MagicType magicType;
    private final Classification classification;
    private final Set<Pair<WeaponScaling, Integer>> requiredStats;
    private final int fpCost;
    private final int staminaCost;
    private final int duration;
    private final float motionValue;
    private final float chargedMotionValue;
    private final boolean canCharge;
    private final SoundEvent castSound;
    protected Level level;
    protected ScaledWeapon scaledWeapon;
    private LivingEntityPatch<?> livingEntityPatch;
    private BlockPos blockPos;
    private String descriptionId;
    protected float catalystBoost;
    private float chargeAmount = 1.0F;
    private int ticks = 0;
    private boolean wasCharged = false;
    private int channelTicks = 0;
    public boolean isInactive = false;
    public boolean init = false;
    public float magicScaling;

    public static Builder<AbstractSpell> createBuilder() {
        return new Builder<>();
    }

    public AbstractSpell(SpellType<?> spellType, Builder<? extends AbstractSpell> builder) {
        this.spellType = spellType;
        builder = EventFactory.getBuilder(spellType, builder);
        this.classification = builder.classification;
        this.magicType = this.classification.getMagicType();
        this.requiredStats = builder.requiredStats;
        this.fpCost = builder.fpCost;
        this.staminaCost = builder.staminaCost;
        this.duration = builder.duration;
        this.motionValue = builder.motionValue;
        this.chargedMotionValue = builder.chargedMotionValue;
        this.canCharge = builder.canCharge;
        this.castSound = builder.castSound;
    }

    public SpellType<?> getSpellType() {
        return this.spellType;
    }

    public Classification getClassification() {
        return this.classification;
    }

    public MagicType getMagicType() {
        return this.magicType;
    }

    public int getFpCost(LivingEntity caster) {
        if (caster.hasEffect(StatusEffectInit.PRIMAL_GLINTSTONE_BLADE.get()))
            return Math.round((float) this.fpCost * 0.75F);
        return this.fpCost;
    }

    public int getStaminaCost(LivingEntity caster) {
        return this.staminaCost;
    }

    public int getDuration() {
        return this.duration;
    }

    public Set<Pair<WeaponScaling, Integer>> getRequiredStats() {
        return this.requiredStats;
    }

    public float getMotionValue() {
        return this.motionValue;
    }

    public float getChargedMotionValue() {
        return this.chargedMotionValue;
    }

    public boolean canCharge() {
        return this.canCharge;
    }

    protected SoundEvent getCastSound() {
        return this.castSound;
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
                if (!this.requiresConcentration() && ticks % duration == 0) {
                    this.endSpell();
                }
            }
        }
    }

    private void startSpell() {
        this.init = false;
        this.onSpellStart(this.livingEntityPatch, this.level, this.blockPos, this.scaledWeapon);
        EventFactory.onSpellStart(this, this.livingEntityPatch, this.level, this.blockPos, this.scaledWeapon);
    }

    //TODO: ADD CAN CAST SPELL CHECK
    private void tickSpell() {
        this.onSpellTick(this.livingEntityPatch, this.level, this.blockPos, this.scaledWeapon);
        if (this.livingEntityPatch.getOriginal() instanceof Player player) {
            if (EntityStatusUtil.isChannelling(player))
                this.channelTicks++;
        }

        EventFactory.onSpellTick(this, this.livingEntityPatch, this.level, this.blockPos, this.scaledWeapon, this.ticks);
    }

    protected void endSpell() {
        this.onSpellStop(this.livingEntityPatch, this.level, this.blockPos, this.scaledWeapon);
        EventFactory.onSpellStop(this, this.livingEntityPatch, this.level, this.blockPos, this.scaledWeapon);
        this.init = false;
        this.isInactive = true;
        this.ticks = 0;
        this.wasCharged = false;
        this.channelTicks = 0;
    }

    protected void onSpellTick(LivingEntityPatch<?> playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
    }

    protected void onSpellStart(LivingEntityPatch<?> playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
    }

    protected void onSpellStop(LivingEntityPatch<?> playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
    }

    protected void onHurtTick(LivingEntityPatch<?> playerPatch, LivingEntity targetEntity, Level level, ScaledWeapon weapon) {
    }

    protected boolean shouldTickEffect(int duration) {
        return true;
    }

    public float getScaledDamage() {
        float f = this.magicScaling * (1 + this.catalystBoost)  / ConfigHandler.STAT_SCALE.get();
        float motionValue = this.wasCharged ? this.chargedMotionValue : this.motionValue;
        return f * motionValue;
    }

    public void checkHurt(LivingEntity livingEntity) {
        DamageInstance damageInstance = this.createDamageInstance();
        if (damageInstance != null) {
            onHurtTick(this.livingEntityPatch, livingEntity, this.level, this.scaledWeapon);
            livingEntity.hurt(DamageUtil.moddedDamageSource(this.level, damageInstance.damageType(), this.livingEntityPatch.getOriginal()), damageInstance.amount());
        }
    }

    public boolean isChannelling() {
        return this.channelTicks > 0;
    }

    public float getChargeAmount() {
        return this.chargeAmount;
    }

    public void setChargeAmount(float chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public static float getPowerForTime(AbstractSpell spell, int pCharge) {
        if (spell.canCharge()) {
            float f = (float) pCharge / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;
            if (f > 1.0F) {
                f = 1.0F;
            }
            return f;
        } else {
            return 1.0F;
        }
    }

    protected DamageInstance createDamageInstance() {
        return null;
    }

    public LivingEntityPatch<?> getCaster() {
        return this.livingEntityPatch;
    }

    public ScaledWeapon getCatalyst() {
        return this.scaledWeapon;
    }

    public void initSpell(LivingEntityPatch<?> livingEntityPatch, Level level, BlockPos blockPos, ScaledWeapon scaledWeapon, float magicScaling, boolean wasCharged, Set<Classification> classifications, float spellBoost) {
        this.level = level;
        this.livingEntityPatch = livingEntityPatch;
        this.blockPos = blockPos;
        this.scaledWeapon = scaledWeapon;
        this.magicScaling = magicScaling;
        this.wasCharged = wasCharged;

        for (var cl : classifications) {
            if (this.classification == cl) {
                this.catalystBoost = spellBoost;
                break;
            }
        }

        EntityStatusUtil.activateSpell(livingEntityPatch.getOriginal(), this);
        this.init = true;
    }

    public static class Builder<T extends AbstractSpell> {
        protected Classification classification;
        protected Set<Pair<WeaponScaling, Integer>> requiredStats = new LinkedHashSet<>();
        protected int duration = 1;
        protected int fpCost;
        protected int staminaCost;
        protected float motionValue;
        protected float chargedMotionValue;
        protected boolean canCharge;
        protected SoundEvent castSound;

        public Builder<T> setClassification(Classification classification) {
            this.classification = classification;
            return this;
        }

        public Builder<T> setFPCost(int fpCost) {
            this.fpCost = fpCost;
            return this;
        }

        public Builder<T> setStaminaCost(int staminaCost) {
            this.staminaCost = staminaCost;
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

        public Builder<T> setChargedMotionValue(float motionValue) {
            this.chargedMotionValue = motionValue;
            return this;
        }

        public Builder<T> canCharge() {
            this.canCharge = true;
            return this;
        }

        public Builder<T> setCastSound(SoundEvent castSound) {
            this.castSound = castSound;
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
