package com.ombremoon.enderring.compat.epicfight.skills;

import com.ombremoon.enderring.compat.epicfight.util.ItemUtil;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedSkillCategories;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.BasicAttackEvent;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.SkillConsumeEvent;

import java.util.List;
import java.util.UUID;

public class HeavyAttack extends Skill {
    private static final UUID EVENT_UUID = UUID.fromString("29c33a0b-175c-47cc-93f6-20facdd42735");

    public static Skill.Builder<HeavyAttack> createHeavyAttackBuilder() {
        return (new Builder<HeavyAttack>()).setCategory(ExtendedSkillCategories.HEAVY_ATTACK).setResource(Resource.STAMINA);
    }

    public static void setComboCounterWithEvent(ComboCounterHandleEvent.Causal reason, ServerPlayerPatch playerPatch, SkillContainer container, StaticAnimation casualAnimation, int value) {
        int prevValue = container.getDataManager().getDataValue(SkillDataKeyInit.COMBO_COUNTER.get());
        ComboCounterHandleEvent event = new ComboCounterHandleEvent(reason, playerPatch, casualAnimation, prevValue, value);
        container.getExecuter().getEventListener().triggerEvents(PlayerEventListener.EventType.COMBO_COUNTER_HANDLE_EVENT, event);
        container.getDataManager().setData(SkillDataKeyInit.COMBO_COUNTER.get(), event.getNextValue());
    }

    public HeavyAttack(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID, (event) -> {
            if (!event.getAnimation().isBasicAttackAnimation() && event.getAnimation().getProperty(AnimationProperty.ActionAnimationProperty.RESET_PLAYER_COMBO_COUNTER).orElse(true)) {
                CapabilityItem item = event.getPlayerPatch().getHoldingItemCapability(InteractionHand.MAIN_HAND);

                if (item.shouldCancelCombo(event.getPlayerPatch())) {
                    setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ACTION_ANIMATION_RESET, event.getPlayerPatch(), container, event.getAnimation(), 0);
                }
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
    }

    @Override
    public boolean isExecutableState(PlayerPatch<?> executer) {
        EntityState entityState = executer.getEntityState();
        Player player = executer.getOriginal();

        return !player.isSpectator() && !executer.isUnstable() && entityState.canBasicAttack();
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        SkillConsumeEvent event = new SkillConsumeEvent(executer, this, this.resource, true);
        executer.getEventListener().triggerEvents(PlayerEventListener.EventType.SKILL_CONSUME_EVENT, event);

        if (!event.isCanceled()) {
            event.getResourceType().consumer.consume(this, executer, event.getAmount());
        }

        //TODO: CREATE HEAVY ATTACK EVENT?
        if (executer.getEventListener().triggerEvents(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, new BasicAttackEvent(executer))) {
            return;
        }

        ExtendedWeaponCapability cap = ItemUtil.getWeaponCapability(executer);
        StaticAnimation animation = null;
        ServerPlayer serverPlayer = executer.getOriginal();
        SkillContainer skillContainer = executer.getSkill(this);
        SkillDataManager dataManager = skillContainer.getDataManager();
        int comboCounter = dataManager.getDataValue(SkillDataKeyInit.COMBO_COUNTER.get());

        if (serverPlayer.isPassenger()) {
            Entity entity = serverPlayer.getVehicle();

            if ((entity instanceof PlayerRideableJumping rideable && rideable.canJump()) && cap.availableOnHorse() && cap.getMountAttackMotion() != null) {
                comboCounter %= cap.getMountAttackMotion().size();
                animation = cap.getMountAttackMotion().get(comboCounter);
                comboCounter++;
            }
        } else {
            List<StaticAnimation> combo = cap.getHeavyAutoAttackMotion(executer);
            int comboSize = combo.size();
            boolean dashAttack = serverPlayer.isSprinting();

            if (dashAttack) {
                comboCounter = comboSize - 2;
            } else {
                comboCounter %= comboSize - 2;
            }

            animation = combo.get(comboCounter);
            comboCounter = dashAttack ? 0 : comboCounter + 1;
        }

        setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ACTION_ANIMATION_RESET, executer, skillContainer, animation, comboCounter);

        if (animation != null) {
            executer.playAnimationSynchronized(animation, 0);
        }

        executer.updateEntityState();
    }

    @Override
    public void updateContainer(SkillContainer container) {
        if (!container.getExecuter().isLogicalClient() && container.getExecuter().getTickSinceLastAction() > 16 && container.getDataManager().getDataValue(SkillDataKeyInit.COMBO_COUNTER.get()) > 0) {
            setComboCounterWithEvent(ComboCounterHandleEvent.Causal.TIME_EXPIRED_RESET, (ServerPlayerPatch) container.getExecuter(), container, null, 0);
        }
    }
}
