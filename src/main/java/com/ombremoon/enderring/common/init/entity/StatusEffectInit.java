package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.world.effect.EffectBuilder;
import com.ombremoon.enderring.common.object.world.effect.IncrementalStatusEffect;
import com.ombremoon.enderring.common.object.world.effect.ShabririWoeEffect;
import com.ombremoon.enderring.common.object.world.effect.StatusEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;

public class StatusEffectInit {
    public static final DeferredRegister<MobEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MOD_ID);

    public static final RegistryObject<MobEffect> PHYSICAL_DAMAGE_NEGATION = registerSimpleEffect("physical_damage_negation", new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> INVIGORATING_MEAT = registerStatusEffect("invigorating_cured_meat",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(EntityAttributeInit.ROBUSTNESS)
                    .setModifier(new AttributeModifier(
                            "Cured Meat",
                            100,
                            AttributeModifier.Operation.ADDITION)).build());

    //STATUS EFFECTS
    public static final RegistryObject<MobEffect> POISON = registerIncrementalEffect("poison");

    //CRYSTAL TEARS
    public static final RegistryObject<MobEffect> CRIMSON_CRYSTAL = registerSimpleEffect("crimson_crystal_tear", new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> CERULEAN_CRYSTAL = registerSimpleEffect("cerulean_crystal_tear", new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> OPALINE_BUBBLE = registerSimpleEffect("opaline_bubble_tear", new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> CERULEAN_HIDDEN = registerSimpleEffect("cerulean_hidden_tear", new EffectBuilder(MobEffectCategory.BENEFICIAL).build());

    //TALISMANS
    //TODO: fix crimson amber medallion values
    public static final RegistryObject<MobEffect> CRIMSON_AMBER_MEDALLION = registerStatusEffect("crimson_amber_medallion",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(() -> Attributes.MAX_HEALTH)
                    .setModifier(new AttributeModifier(
                            UUID.fromString("8d56064f-619a-40f5-bc91-d74f9735d964"),
                            "Crimson Amber Medallion",
                            1.06F,
                            AttributeModifier.Operation.MULTIPLY_BASE))
                    .addTier(1, 1.07F)
                    .addTier(2, 1.08F).build());
    public static final RegistryObject<MobEffect> SHABRIRIS_WOE = registerStatusEffect("shabriris_woe",
            new ShabririWoeEffect(MobEffectCategory.NEUTRAL, 234227227));
    public static final RegistryObject<MobEffect> GREEN_TURTLE = registerStatusEffect("green_turtle_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(EpicFightAttributes.STAMINA_REGEN)
                    .setModifier(new AttributeModifier(
                            UUID.fromString("574ddd07-f2cc-41c8-accf-b8e6369a7377"),
                            "Green turtle talisman",
                            0.2F,
                            AttributeModifier.Operation.ADDITION)).build());
    public static final RegistryObject<MobEffect> RADAGONS_SCARSEAL = registerStatusEffect("radagons_scarseal",
            new EffectBuilder(MobEffectCategory.NEUTRAL)
                    .addAttribute(EntityAttributeInit.VIGOR)
                    .addAttribute(EntityAttributeInit.ENDURANCE)
                    .addAttribute(EntityAttributeInit.STRENGTH)
                    .addAttribute(EntityAttributeInit.DEXTERITY)
                    .setModifier(new AttributeModifier(
                            UUID.fromString("03592921-f6c8-4528-9ec4-f301fa5ec9d7"),
                            "Radagon's Scarseal",
                            3F,
                            AttributeModifier.Operation.ADDITION))
                    .addTier(1, 5f)
                    .addTranslation(0, "item.enderring.radagons_scarseal")
                    .addTranslation(1, "item.enderring.radagons_scarsealplus1").build());
    public static final RegistryObject<MobEffect> MARIKAS_SCARSEAL = registerStatusEffect("marikas_scarseal",
            new EffectBuilder(MobEffectCategory.NEUTRAL)
                    .addAttribute(EntityAttributeInit.MIND)
                    .addAttribute(EntityAttributeInit.INTELLIGENCE)
                    .addAttribute(EntityAttributeInit.FAITH)
                    .addAttribute(EntityAttributeInit.ARCANE)
                    .setModifier(new AttributeModifier(
                            UUID.fromString("44f08804-32f0-42b3-85e5-1b441aa6c325"),
                            "Marika's Soreseal",
                            3.0F,
                            AttributeModifier.Operation.ADDITION))
                    .addTier(1, 5F)
                    .addTranslation(0, "item.enderring.marikas_scarseal")
                    .addTranslation(1, "item.enderring.marikas_scarsealplus1").build());
    public static final RegistryObject<MobEffect> DRAGONCREST_SHIELD_TALISMAN = registerSimpleEffect("dragoncrest_shield_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(EntityAttributeInit.PHYS_NEGATE)
                    .setModifier(new AttributeModifier(
                            UUID.fromString("07f0bb21-c796-4808-8fd4-18d8df9f6347"),
                            "Dragoncrest Shield Talisman",
                            1.1F,
                            AttributeModifier.Operation.MULTIPLY_BASE
                    ))
                    .addTier(1, 1.13F)
                    .addTier(2, 1.17F).build());
    public static final RegistryObject<MobEffect> SACRIFICIAL_TWIG = registerSimpleEffect("sacrificial_twig",
            new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> CRIMSON_SEED_TALISMAN = registerSimpleEffect("crimson_seed_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> CERULEAN_SEED_TALISMAN = registerSimpleEffect("cerulean_seed_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> STARSCOURGE_HEIRLOOM = registerSimpleEffect("starscourge_heirloom",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .singleAttribute(EntityAttributeInit.STRENGTH,
                            "6b6df324-3d12-4abe-9373-02298b897c77",
                            "Starscourge Heirloom",
                            5F, AttributeModifier.Operation.ADDITION).build());
    public static final RegistryObject<MobEffect> PROSTHESIS_WEARER_HEIRLOOM = registerSimpleEffect("prosthesis_wearer_heirloom",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .singleAttribute(EntityAttributeInit.DEXTERITY,
                            "acdf6fe1-31ec-411f-9946-67964a4957f5",
                            "Prosthesis Wearer Heirloom",
                            5F, AttributeModifier.Operation.ADDITION).build());
    public static final RegistryObject<MobEffect> STARGAZER_HEIRLOOM = registerStatusEffect("stargazer_heirloom",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .singleAttribute(EntityAttributeInit.INTELLIGENCE,
                            "9690d174-be9c-4fac-a5d4-5dd31e00dc7b",
                            "Stargazer Heirloom",
                            5F, AttributeModifier.Operation.ADDITION).build());
    public static final RegistryObject<MobEffect> TWO_FINGERS_HEIRLOOM = registerStatusEffect("two_finger_heirloom",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .singleAttribute(EntityAttributeInit.INTELLIGENCE,
                            "a5114201-7ce1-4781-b444-f72c5a2d0632",
                            "Two Fingers Heirloom",
                            5F, AttributeModifier.Operation.ADDITION).build());
    public static final RegistryObject<MobEffect> LONGTAIL_CAT_TALISMAN = registerStatusEffect("longtail_cat_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL).build());


    private static RegistryObject<MobEffect> registerIncrementalEffect(String name) {
        return STATUS_EFFECTS.register(name, () -> new IncrementalStatusEffect(MobEffectCategory.HARMFUL, 234227227));
    }

    private static RegistryObject<MobEffect> registerSimpleEffect(String name, StatusEffect effect) {
        return STATUS_EFFECTS.register(name, () -> effect);
    }

    private static RegistryObject<MobEffect> registerStatusEffect(String name, MobEffect statusEffect) {
        return STATUS_EFFECTS.register(name, () -> statusEffect);
    }

    public static void register(IEventBus modEventBus) {
        STATUS_EFFECTS.register(modEventBus);
    }
}
