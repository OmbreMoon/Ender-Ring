package com.ombremoon.enderring.common.magic;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
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
    protected static final Logger LOGGER = Constants.LOG;
    protected static int DEFAULT_CAST_TIME = 1;
    protected static int INSTANT_SPELL_DURATION = 10;
    private final SpellType<?> spellType;
    private final MagicType magicType;
    private final Set<Pair<WeaponScaling, Integer>> requiredStats;
    private final int fpCost;
    private final int duration;
    private final float motionValue;
    private final float chargedMotionValue;
    private final boolean canCharge;
    private final SoundEvent castSound;
    protected Level level;
    protected ScaledWeapon scaledWeapon;
    private ServerPlayerPatch playerPatch;
    private BlockPos blockPos;
    private String descriptionId;
    private float chargeAmount;
    private int ticks = 0;
    private boolean wasCharged = false;
    private int chargeTicks = 0;
    public boolean isInactive = false;
    public boolean init = false;
    public float magicScaling;

    public static Builder<AbstractSpell> createBuilder() {
        return new Builder<>();
    }

    public AbstractSpell(SpellType<?> spellType, Builder<? extends AbstractSpell> builder) {
        this.spellType = spellType;
        //BUILD SPELL EVENT
        this.magicType = builder.magicType;
        this.requiredStats = builder.requiredStats;
        this.fpCost = builder.fpCost;
        this.duration = builder.duration;
        this.motionValue = builder.motionValue;
        this.chargedMotionValue = builder.chargedMotionValue;
        this.canCharge = builder.canCharge;
        this.castSound = builder.castSound;
    }

    public SpellType<?> getSpellType() {
        return this.spellType;
    }

    public MagicType getMagicType() {
        return this.magicType;
    }

    public int getFpCost(LivingEntity caster) {
        if (caster.hasEffect(StatusEffectInit.PRIMAL_GLINTSTONE_BLADE.get()))
            return Math.round((float) this.fpCost * 0.75F);
        return this.fpCost;
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

    //TODO: ADD CAN CAST SPELL CHECK
    private void tickSpell() {
        this.onSpellTick(this.playerPatch, this.level, this.blockPos, this.scaledWeapon);
        if (false/*player is holding down mouse*/) {
            this.chargeTicks++;

        }
        if (this.requiresConcentration() && !EntityStatusUtil.consumeFP(this.playerPatch.getOriginal(), this.fpCost, true)) {
            this.endSpell();
        }
    }

    private void endSpell() {
        this.onSpellStop(this.playerPatch, this.level, this.blockPos, this.scaledWeapon);
        this.init = false;
        this.isInactive = true;
        this.ticks = 0;
        this.wasCharged = false;
        this.chargeTicks = 0;
    }

    protected void onSpellTick(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {

    }

    protected void onSpellStart(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
//        playerPatch.getOriginal().sendSystemMessage(Component.literal("Spell: ").append(Component.translatable(this.getSpellName().getString())));
    }

    protected void onSpellStop(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {

    }

    protected void onHurtTick(ServerPlayerPatch playerPatch, LivingEntity targetEntity, Level level, ScaledWeapon weapon) {

    }

    protected boolean shouldTickEffect(int duration) {
        return true;
    }

    public float getScaledDamage() {
        if (this.wasCharged) {
            return this.magicScaling * this.chargedMotionValue / ConfigHandler.STAT_SCALE.get();
        }
        return this.magicScaling * this.motionValue / ConfigHandler.STAT_SCALE.get();
    }

    public void checkHurt(LivingEntity livingEntity) {
        DamageInstance damageInstance = this.createDamageInstance();
        if (damageInstance != null) {
            onHurtTick(this.playerPatch, livingEntity, this.level, this.scaledWeapon);
            livingEntity.hurt(DamageUtil.moddedDamageSource(this.level, damageInstance.damageType()), damageInstance.amount());
        }
    }

    public boolean wasCharged() {
        return this.wasCharged;
    }

    public boolean isCharging() {
        return this.chargeTicks > 0;
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

    public ServerPlayerPatch getCaster() {
        return this.playerPatch;
    }

    public ScaledWeapon getCatalyst() {
        return this.scaledWeapon;
    }

    public void initSpell(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon scaledWeapon, float magicScaling, boolean wasCharged) {
        this.level = level;
        this.playerPatch = playerPatch;
        this.blockPos = blockPos;
        this.scaledWeapon = scaledWeapon;
        this.magicScaling = magicScaling;
        this.wasCharged = wasCharged;

        EntityStatusUtil.activateSpell(playerPatch.getOriginal(), this);
        this.init = true;
    }

    public static class Builder<T extends AbstractSpell> {
        protected MagicType magicType;
        protected Set<Pair<WeaponScaling, Integer>> requiredStats = new LinkedHashSet<>();
        protected int duration = 1;
        protected int fpCost;
        protected float motionValue;
        protected float chargedMotionValue;
        protected boolean canCharge;
        protected SoundEvent castSound;

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

        public Builder<T> setChargedMotionValue(float motionValue) {
            this.chargedMotionValue = motionValue;
            return this;
        }

        public Builder<T> setCanCharge(boolean canCharge) {
            this.canCharge = canCharge;
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

    public record DamageInstance(ResourceKey<DamageType> damageType, float amount) {}
}
