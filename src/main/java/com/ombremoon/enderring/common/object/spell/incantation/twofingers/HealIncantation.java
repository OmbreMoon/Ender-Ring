package com.ombremoon.enderring.common.object.spell.incantation.twofingers;

import com.google.common.collect.Lists;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SimpleAnimationSpell;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.spell.incantation.HealSpell;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.compat.epicfight.gameassets.AnimationInit;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageSources;

import java.util.List;

public class HealIncantation extends HealSpell {
    protected static ResourceLocation HEAL_ANIMATION = CommonClass.customLocation("biped/spell/heal");

    public static SimpleAnimationSpell.Builder createHealBuilder() {
        return createSimpleSpellBuilder().setMagicType(MagicType.INCANTATION).setFPCost(32).setDuration(76).setRequirements(WeaponScaling.FAI, 12).setMotionValue(2.3F).setAnimations(HEAL_ANIMATION);
    }

    public HealIncantation(Builder builder) {
        this(SpellInit.HEAL.get(), builder);
    }

    public HealIncantation(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
    }

    @Override
    public void onSpellStart(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {
        super.onSpellStart(spellInstance, playerPatch, weapon, level, blockPos);
        Player player = playerPatch.getOriginal();
        LivingEntity livingEntity = playerPatch.getTarget();
        List<Entity> hurted = Lists.newArrayList();

        if (livingEntity != null) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            Vec3 direction = livingEntity.getPosition(0.1F).subtract(x, y, z);
            Vec3 start = new Vec3(x, y, z);
            Vec3 destination = start.add(direction.normalize().scale(200.0));
            BlockHitResult hitResult = level.clip(new ClipContext(start, destination, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity) null));
            Vec3 hitLocation = hitResult.getLocation();
            double xLength = hitLocation.x - x;
            double yLength = hitLocation.y - y;
            double zLength = hitLocation.z - z;
            double horizontalDistance = Math.sqrt(xLength * xLength + zLength * zLength);
            double length = Math.sqrt(xLength * xLength + yLength * yLength + zLength * zLength);
            float yRot = (float) (-Math.atan2(zLength, xLength) * 57.29577951308232) - 90.0F;
            float xRot = (float) (Math.atan2(yLength, horizontalDistance) * 57.29577951308232);
            OBBCollider collider = new OBBCollider(0.25, 0.25, length * 0.5, 0.0, 0.0, length * 0.5);
            collider.transform(OpenMatrix4f.createTranslation((float) (-x), (float) y, (float) (-z)).rotateDeg(yRot, Vec3f.Y_AXIS).rotateDeg(-xRot, Vec3f.X_AXIS));
            List<Entity> hitEntities = collider.getCollideEntities(player);
            EpicFightDamageSources damageSources = EpicFightDamageSources.of(level);
            EpicFightDamageSource damagesource = damageSources.playerAttack(player);
            hitEntities.forEach((entity) -> {
                if (!hurted.contains(entity)) {
                    hurted.add(entity);
                    entity.hurt(damagesource, 12.0F);
                }
            });
        }
    }

    @Override
    public void tickSpellEffect(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {
        super.tickSpellEffect(spellInstance, playerPatch, weapon, level, blockPos);
        float incantScaling = spellInstance.getMagicScaling();
        int statScale = ConfigHandler.STAT_SCALE.get();
        List<Entity> entityList = level.getEntities(playerPatch.getOriginal(), playerPatch.getOriginal().getBoundingBox().inflate(3.0F));
        for (Entity entity : entityList) {
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity instanceof Player player) {
                    if (playerPatch.getOriginal().getTeam() != null && playerPatch.getOriginal().getTeam().isAlliedTo(player.getTeam())) {
                        player.heal(incantScaling * 1.15F / statScale);
                    }
                } else if (livingEntity instanceof Mob mob) {
                    if (mob.getMobType() == MobType.UNDEAD) {
                        mob.hurt(DamageUtil.moddedDamageSource(level, ModDamageTypes.HOLY), incantScaling * this.motionValue / statScale);
                    }
                }
            }
        }
    }

    @Override
    public boolean isEffectTick(int duration) {
        return duration == 36;
    }
}
