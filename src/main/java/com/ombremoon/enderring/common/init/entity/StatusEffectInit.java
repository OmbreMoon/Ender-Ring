package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.world.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Attr;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;

public class StatusEffectInit {
    public static final DeferredRegister<MobEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MOD_ID);

    public static final RegistryObject<MobEffect> PHYSICAL_DAMAGE_NEGATION = registerSimpleEffect("physical_damage_negation", new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> INVIGORATING_MEAT = registerStatusEffect("invigorating_cured_meat",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d77ddab7-efba-4871-9e95-c376dce685d4"),
                            "Cured Meat",
                            100, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.ROBUSTNESS).build());

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
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("8d56064f-619a-40f5-bc91-d74f9735d964"),
                            "Crimson Amber Medallion",
                            1.06F, AttributeModifier.Operation.MULTIPLY_BASE),
                            () -> Attributes.MAX_HEALTH)
                    .addTier("8d56064f-619a-40f5-bc91-d74f9735d964",1, 1.07F)
                    .addTier("8d56064f-619a-40f5-bc91-d74f9735d964",2, 1.08F).build());
    public static final RegistryObject<MobEffect> SHABRIRIS_WOE = registerStatusEffect("shabriris_woe",
            new ShabririWoeEffect(MobEffectCategory.NEUTRAL, 234227227));
    public static final RegistryObject<MobEffect> GREEN_TURTLE = registerStatusEffect("green_turtle_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("574ddd07-f2cc-41c8-accf-b8e6369a7377"),
                            "Green turtle talisman",
                            0.2F, AttributeModifier.Operation.ADDITION),
                            EpicFightAttributes.STAMINA_REGEN).build());
    public static final RegistryObject<MobEffect> RADAGONS_SCARSEAL = registerStatusEffect("radagons_scarseal",
            new EffectBuilder(MobEffectCategory.NEUTRAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("03592921-f6c8-4528-9ec4-f301fa5ec9d7"),
                            "Radagon's Scarseal",
                            3F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.VIGOR, EntityAttributeInit.ENDURANCE,
                            EntityAttributeInit.STRENGTH, EntityAttributeInit.DEXTERITY)
                    .addTier("03592921-f6c8-4528-9ec4-f301fa5ec9d7", 1, 5f)
                    .addTranslation(0, "item.enderring.radagons_scarseal")
                    .addTranslation(1, "item.enderring.radagons_scarsealplus1").build());
    public static final RegistryObject<MobEffect> MARIKAS_SCARSEAL = registerStatusEffect("marikas_scarseal",
            new EffectBuilder(MobEffectCategory.NEUTRAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("44f08804-32f0-42b3-85e5-1b441aa6c325"),
                            "Marika's Soreseal",
                            3.0F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.MIND, EntityAttributeInit.INTELLIGENCE,
                            EntityAttributeInit.FAITH, EntityAttributeInit.ARCANE)
                    .addTier("44f08804-32f0-42b3-85e5-1b441aa6c325", 1, 5F)
                    .addTranslation(0, "item.enderring.marikas_scarseal")
                    .addTranslation(1, "item.enderring.marikas_scarsealplus1").build());
    public static final RegistryObject<MobEffect> DRAGONCREST_SHIELD_TALISMAN = registerSimpleEffect("dragoncrest_shield_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("07f0bb21-c796-4808-8fd4-18d8df9f6347"),
                            "Dragoncrest Shield Talisman",
                            1.1F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE)
                    .addTier("07f0bb21-c796-4808-8fd4-18d8df9f6347", 1, 1.13D)
                    .addTier("07f0bb21-c796-4808-8fd4-18d8df9f6347", 2, 1.17D).build());
    public static final RegistryObject<MobEffect> SACRIFICIAL_TWIG = registerSimpleEffect("sacrificial_twig",
            new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> CRIMSON_SEED_TALISMAN = registerSimpleEffect("crimson_seed_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> CERULEAN_SEED_TALISMAN = registerSimpleEffect("cerulean_seed_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> STARSCOURGE_HEIRLOOM = registerSimpleEffect("starscourge_heirloom",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("6b6df324-3d12-4abe-9373-02298b897c77"),
                            "Starscourge Heirloom",
                            5F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.STRENGTH).build());
    public static final RegistryObject<MobEffect> PROSTHESIS_WEARER_HEIRLOOM = registerSimpleEffect("prosthesis_wearer_heirloom",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("acdf6fe1-31ec-411f-9946-67964a4957f5"),
                            "Prosthesis Wearer Heirloom",
                            5F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.DEXTERITY).build());
    public static final RegistryObject<MobEffect> STARGAZER_HEIRLOOM = registerStatusEffect("stargazer_heirloom",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("9690d174-be9c-4fac-a5d4-5dd31e00dc7b"),
                            "Stargazer Heirloom",
                            5F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.INTELLIGENCE).build());
    public static final RegistryObject<MobEffect> TWO_FINGERS_HEIRLOOM = registerStatusEffect("two_finger_heirloom",
            new EffectBuilder(MobEffectCategory.BENEFICIAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("a5114201-7ce1-4781-b444-f72c5a2d0632"),
                            "Two Fingers Heirloom",
                            5F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.FAITH).build());
    public static final RegistryObject<MobEffect> LONGTAIL_CAT_TALISMAN = registerStatusEffect("longtail_cat_talisman",
            new EffectBuilder(MobEffectCategory.BENEFICIAL).build());
    public static final RegistryObject<MobEffect> PRIMAL_GLINTSTONE_BLADE = registerStatusEffect("primal_glintstone_blade",
            new EffectBuilder(MobEffectCategory.NEUTRAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("8c9d806a-ac65-4423-be9d-d4b5e25e5f4f"),
                            "Primal Glintstone Blade",
                            0.85F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            () -> Attributes.MAX_HEALTH).build());
    public static final RegistryObject<MobEffect> MAGIC_SCORPION_CHARM = registerStatusEffect("magic_scorpion_charm",
            new EffectBuilder(MobEffectCategory.NEUTRAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("0e5a7192-845d-4b22-a086-91dbd2fe4d3f"),
                            "Magic Scorpion Charm",
                            1.12F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.MAGIC_DAMAGE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("dabb5e21-b470-4a8b-ad42-2b630bee1a1c"),
                            "Magic Scorpion Charm",
                            0.9F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE).build());
    public static final RegistryObject<MobEffect> FIRE_SCORPION_CHARM = registerStatusEffect("fire_scorpion_charm",
            new EffectBuilder(MobEffectCategory.NEUTRAL)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("5ef1281a-1a85-49b7-b716-53aa726d4019"),
                            "Fire Scorpion Charm",
                            1.12F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.FIRE_DAMAGE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("9a583800-cb26-45b1-9963-e53eb71d96c1"),
                            "Fire Scorpion Charm",
                            0.9F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE).build());


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
