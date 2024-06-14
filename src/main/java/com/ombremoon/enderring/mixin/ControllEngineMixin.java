package com.ombremoon.enderring.mixin;

import com.ombremoon.enderring.client.KeyBinds;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedSkillSlots;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.SkillSlot;

import java.util.Map;
import java.util.function.BiFunction;

@Mixin(value = ControllEngine.class, remap = false)
public abstract class ControllEngineMixin {

    @Shadow private LocalPlayerPatch playerpatch;

    @Shadow private KeyMapping currentChargingKey;

    @Shadow private LocalPlayer player;

    @Shadow protected abstract void reserveKey(SkillSlot slot, KeyMapping keyMapping);

    @Shadow public abstract void lockHotkeys();

    @Shadow @Final private Map<KeyMapping, BiFunction<KeyMapping, Integer, Boolean>> keyFunctions;

    @Shadow private boolean hotbarLocked;
    @Shadow private int lastHotbarLockedTime;

    @Shadow public abstract void unlockHotkeys();

    @Shadow public abstract boolean isKeyDown(KeyMapping key);

    @Shadow private boolean attackLightPressToggle;
    @Shadow @Final private Minecraft minecraft;

    @Shadow public abstract void setKeyBind(KeyMapping key, boolean setter);

    @Unique private int heavyAttackPressCounter = 0;

    @Unique private boolean heavyAttackPressToggle = false;

    @Inject(method = "<init>()V", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        this.keyFunctions.put(KeyBinds.ASH_OF_WAR_BINDING, this::ashOfWarSkillKeyPressed);
        this.keyFunctions.put(KeyBinds.HEAVY_ATTACK_BINDING, this::heavyAttackKeyPressed);
    }

    @Inject(method = "setPlayerPatch", at = @At("HEAD"))
    private void setPlayerPatch(LocalPlayerPatch playerPatch, CallbackInfo info) {
        this.heavyAttackPressCounter = 0;
        this.heavyAttackPressToggle = false;
    }

    @Inject(method = "attackKeyPressed", at = @At(value = "HEAD"))
    private void heavyAttackPressed(KeyMapping key, int action, CallbackInfoReturnable<Boolean> cir) {
        if (action == 1 && this.playerpatch.isBattleMode() && this.currentChargingKey != key && !Minecraft.getInstance().isPaused()) {
            if (EpicFightKeyMappings.ATTACK.getKey().equals(KeyBinds.HEAVY_ATTACK_BINDING.getKey())) {
                if (!this.heavyAttackPressToggle) {
                    this.heavyAttackPressToggle = true;
                }
            }
        }
    }

    @Inject(method = "attackKeyPressed", at = @At(value = "INVOKE", target = "Lyesman/epicfight/client/events/engine/ControllEngine;lockHotkeys()V", shift = At.Shift.AFTER))
    private void attackKeyPressed(KeyMapping key, int action, CallbackInfoReturnable<Boolean> cir) {
        this.heavyAttackPressToggle = false;
        this.heavyAttackPressCounter = 0;
    }

    @Unique
    private boolean heavyAttackKeyPressed(KeyMapping key, int action) {
        if (action == 1 && this.playerpatch.isBattleMode() && this.currentChargingKey != key && !EpicFightKeyMappings.ATTACK.getKey().equals(KeyBinds.HEAVY_ATTACK_BINDING.getKey())) {
            if (this.playerpatch.getSkill(ExtendedSkillSlots.HEAVY_ATTACK).sendExecuteRequest(this.playerpatch, self()).shouldReserverKey()) {
                if (!this.player.isSpectator()) {
                    this.reserveKey(ExtendedSkillSlots.HEAVY_ATTACK, key);
                }
            } else {
                this.lockHotkeys();
            }
        }
        return false;
    }


    @Unique
    private boolean ashOfWarSkillKeyPressed(KeyMapping key, int action) {
        if (action == 1 && this.playerpatch.isBattleMode() && this.currentChargingKey != key && !EpicFightKeyMappings.ATTACK.getKey().equals(KeyBinds.ASH_OF_WAR_BINDING.getKey())) {
            if (this.playerpatch.getSkill(ExtendedSkillSlots.ASH_OF_WAR).sendExecuteRequest(this.playerpatch, self()).shouldReserverKey()) {
                if (!this.player.isSpectator()) {
                    this.reserveKey(ExtendedSkillSlots.ASH_OF_WAR, key);
                }
            } else {
                this.lockHotkeys();
            }
        }
        return false;
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tick(CallbackInfo info) {
        if (this.playerpatch != null && this.playerpatch.isBattleMode() && !Minecraft.getInstance().isPaused()) {
            if (this.player.tickCount - this.lastHotbarLockedTime > 20 && this.hotbarLocked) {
                this.unlockHotkeys();
            }

            if (this.heavyAttackPressToggle) {
                if (!this.isKeyDown(KeyBinds.HEAVY_ATTACK_BINDING)) {
                    this.attackLightPressToggle = true;
                    this.heavyAttackPressToggle = false;
                    this.heavyAttackPressCounter = 0;
                } else if (KeyBinds.HEAVY_ATTACK_BINDING.getKey().equals(EpicFightKeyMappings.ATTACK.getKey())) {
                    //TODO: ADD CONFIG
                    if (this.heavyAttackPressCounter > 4) {
                        if (this.minecraft.hitResult.getType() == HitResult.Type.BLOCK && this.playerpatch.getTarget() == null && !EpicFightMod.CLIENT_CONFIGS.noMiningInCombat.getValue()) {
                            this.minecraft.startAttack();
                            this.setKeyBind(EpicFightKeyMappings.ATTACK, true);
                        } else if (this.playerpatch.getSkill(ExtendedSkillSlots.HEAVY_ATTACK).sendExecuteRequest(this.playerpatch, self()).shouldReserverKey()) {
                            if (!this.player.isSpectator()) {
                                this.reserveKey(ExtendedSkillSlots.HEAVY_ATTACK, KeyBinds.HEAVY_ATTACK_BINDING);
                            }
                        } else {
                            this.lockHotkeys();
                        }

                        this.heavyAttackPressToggle = false;
                        this.heavyAttackPressCounter = 0;
                    } else {
                        this.heavyAttackPressCounter++;
                    }
                }
            }
        }
    }

    private ControllEngine self() {
        return (ControllEngine) (Object) this;
    }
}
