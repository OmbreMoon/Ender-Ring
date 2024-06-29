package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatusEffect extends MobEffect {
    protected static final Logger LOGGER = Constants.LOG;
    @Nullable public Map<Integer, String> translationKeys = null;
    @Nullable private Map<String, Map<Integer, Double>> tiers;

    public StatusEffect(MobEffectCategory pCategory, int pColor, @Nullable Map<Integer, String> translations, Map<String, Map<Integer, Double>> tiers) {
        super(pCategory, pColor);
        this.translationKeys = translations;
        this.tiers = tiers;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (this == StatusEffectInit.CRIMSON_CRYSTAL.get()) {
            pLivingEntity.heal(pLivingEntity.getMaxHealth() / 2);
        } else if (this == StatusEffectInit.CERULEAN_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseFP(player, (float) (player.getAttributeValue(EntityAttributeInit.MAX_FP.get()) / 2));
        }
        else if(this == StatusEffectInit.CRIMSONSPILL_CRYSTAL.get() && pLivingEntity instanceof Player player){
            EntityStatusUtil.increaseBaseStat(player, Attributes.MAX_HEALTH,10,false);
        }
        else if(this == StatusEffectInit.CRIMSONBURST_CRYSTAL.get() && pLivingEntity instanceof ServerPlayer player){
            player.heal(10);
        }
        else if(this == StatusEffectInit.GREENSPILL_CRYSTAL.get() && pLivingEntity instanceof Player player){
            EntityStatusUtil.increaseBaseStat(player, EpicFightAttributes.MAX_STAMINA.get(),10,false);
        }
        else if(this == StatusEffectInit.GREENBURST_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,EpicFightAttributes.STAMINA_REGEN.get(),10,false);
        }else if(this == StatusEffectInit.STRENGTHKNOT_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,Attributes.ATTACK_DAMAGE,10,false);
        }else if(this == StatusEffectInit.DEXTERITYKNOT_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,EntityAttributeInit.DEXTERITY.get(),10,false);
        }else if(this == StatusEffectInit.INTELLIGENCEKNOT_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,EntityAttributeInit.INTELLIGENCE.get(),10,false);
        }else if(this == StatusEffectInit.FAITHKNOT_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,EntityAttributeInit.FAITH.get(),10,false);
        }
        else if(this == StatusEffectInit.SPECKLEDHARD_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,Attributes.ARMOR,10,false);
            player.removeAllEffects();
        }

        //TODO::POISE OF EFM
        else if(this == StatusEffectInit.LEADENHARD_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            //EntityStatusUtil.increaseBaseStat(player,EntityAttributeInit.POISE.get(),10,false);
        }

        else if(this == StatusEffectInit.MAGICSHROUDING_CRACKED.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,EntityAttributeInit.MAGIC_DAMAGE.get(),10,false);
        }else if(this == StatusEffectInit.FLAMESHROUDING_CRACKED.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,EntityAttributeInit.FIRE_DAMAGE.get(),10,false);
        }else if(this == StatusEffectInit.HOLYSHROUDING_CRACKED.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,EntityAttributeInit.HOLY_DAMAGE.get(),10,false);
        }else if(this == StatusEffectInit.LIGHTNINGSHROUDING_CRACKED.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseBaseStat(player,EntityAttributeInit.LIGHT_DAMAGE.get(),10,false);
        }else if(this == StatusEffectInit.CRIMSON_BUBBLE.get() && pLivingEntity instanceof Player player) {
            if(player.isDeadOrDying()){
                player.heal(5);
            }
        }else if(this == StatusEffectInit.CRIMSONWHORL_BUBBLE.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.healPlayerOnDamageTaken(player);
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public Double getTier(String uuid, int amp) {
        if (this.tiers == null) {
            LOGGER.warn("Attempted to use getTier on status effect with no tiers.");
            return null;
        } else if (this.tiers.get(uuid) == null) {
            LOGGER.warn("Attempted to use getTier on an invalid UUID");
            return null;
        }
        return this.tiers.get(uuid).get(amp);
    }

}
