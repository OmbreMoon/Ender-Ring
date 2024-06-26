package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.world.effect.EffectBuilder;
import com.ombremoon.enderring.common.object.world.effect.ShabririWoeEffect;
import com.ombremoon.enderring.common.object.world.effect.StatusEffect;
import com.ombremoon.enderring.common.object.world.effect.buildup.*;
import com.ombremoon.enderring.common.object.world.effect.stacking.EffectType;
import net.minecraft.world.effect.MobEffect;
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

    public static final RegistryObject<MobEffect> PHYSICAL_DAMAGE_NEGATION = registerSimpleEffect("physical_damage_negation", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> INVIGORATING_MEAT = registerStatusEffect("invigorating_cured_meat",
            new EffectBuilder(EffectType.BODY)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d77ddab7-efba-4871-9e95-c376dce685d4"),
                            "Cured Meat",
                            100, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.ROBUSTNESS).build());

    //STATUS EFFECTS
    public static final RegistryObject<MobEffect> POISON = registerStatusEffect("poison", new TickingEffect(234227227));
    public static final RegistryObject<MobEffect> SCARLET_ROT = registerStatusEffect("scarlet_rot", new TickingEffect(234227227));
    public static final RegistryObject<MobEffect> BLOOD_LOSS = registerStatusEffect("blood_loss", new BloodLossEffect(234227227));
    public static final RegistryObject<MobEffect> FROSTBITE = registerStatusEffect("frostbite", new FrostbiteEffect(234227227));
    public static final RegistryObject<MobEffect> SLEEP = registerStatusEffect("sleep", new BuildUpStatusEffect(234227227));
    public static final RegistryObject<MobEffect> MADNESS = registerStatusEffect("madness", new BuildUpStatusEffect(234227227));
    public static final RegistryObject<MobEffect> DEATH_BLIGHT = registerStatusEffect("death_blight", new BuildUpStatusEffect(234227227));

    //CRYSTAL TEARS
    public static final RegistryObject<MobEffect> CRIMSON_CRYSTAL = registerSimpleEffect("crimson_crystal_tear", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> CERULEAN_CRYSTAL = registerSimpleEffect("cerulean_crystal_tear", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> OPALINE_BUBBLE = registerSimpleEffect("opaline_bubble_tear", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> CERULEAN_HIDDEN = registerSimpleEffect("cerulean_hidden_tear", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> CRIMSONSPILL_CRYSTAL = registerSimpleEffect("crimsonspill_crystal", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> CRIMSONBURST_CRYSTAL = registerSimpleEffect("crimsonburst_crystal", new EffectBuilder(EffectType.HEALTH_REGEN).build());
    public static final RegistryObject<MobEffect> GREENSPILL_CRYSTAL = registerSimpleEffect("greenspill_crystal", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> GREENBURST_CRYSTAL = registerSimpleEffect("greenburst_crystal", new EffectBuilder(EffectType.STAMINA_REGEN).build());
    public static final RegistryObject<MobEffect> STRENGTHKNOT_CRYSTAL = registerSimpleEffect("strengthknot_crystal", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> DEXTERITYKNOT_CRYSTAL = registerSimpleEffect("dexterityknot_crystal", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> INTELLIGENCEKNOT_CRYSTAL = registerSimpleEffect("intelligenceknot_crystal", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> FAITHKNOT_CRYSTAL = registerSimpleEffect("faithknot_crystal", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> OPALINEHARD_CRYSTAL = registerSimpleEffect("opalinehard_crystal",
            new EffectBuilder(EffectType.UNIQUE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d9b7d304-cc64-4b36-b086-7b8bdb408344"),
                            "Opalinehard_Crystal",
                            0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d9b7d304-cc64-4b36-b086-7b8bdb408344"),
                            "Opalinehard_Crystal",
                            0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.FIRE_NEGATE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d9b7d304-cc64-4b36-b086-7b8bdb408344"),
                            "Opalinehard_Crystal",
                            0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.HOLY_DEFENSE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d9b7d304-cc64-4b36-b086-7b8bdb408344"),
                            "Opalinehard_Crystal",
                            0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.LIGHT_NEGATE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d9b7d304-cc64-4b36-b086-7b8bdb408344"),
                            "Opalinehard_Crystal",
                            0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.MAGIC_NEGATE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d9b7d304-cc64-4b36-b086-7b8bdb408344"),
                            "Opalinehard_Crystal",
                            0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PIERCE_NEGATE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d9b7d304-cc64-4b36-b086-7b8bdb408344"),
                            "Opalinehard_Crystal",
                            0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.SLASH_NEGATE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d9b7d304-cc64-4b36-b086-7b8bdb408344"),
                            "Opalinehard_Crystal",
                            0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.STRIKE_NEGATE).build()
                    );
    public static final RegistryObject<MobEffect> SPECKLEDHARD_CRYSTAL = registerSimpleEffect("speckledhard_crystal", new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> LEADENHARD_CRYSTAL = registerSimpleEffect("leadenhard_crystal", new EffectBuilder(EffectType.UNIQUE).build());

    public static final RegistryObject<MobEffect> MAGICSHROUDING_CRACKED = registerSimpleEffect("magicshrouding_cracked",new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> FLAMESHROUDING_CRACKED = registerSimpleEffect("flameshrouding_cracked",new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> HOLYSHROUDING_CRACKED = registerSimpleEffect("holyshrouding_cracked",new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> LIGHTNINGSHROUDING_CRACKED = registerSimpleEffect("lightningshrouding_cracked",new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> CRIMSON_BUBBLE = registerSimpleEffect("crimson_bubble",new EffectBuilder(EffectType.UNIQUE).build());
    public static final RegistryObject<MobEffect> CRIMSONWHORL_BUBBLE = registerSimpleEffect("crimsonwhorl_bubble",new EffectBuilder(EffectType.UNIQUE).build());

    //TALISMANS
    public static final RegistryObject<MobEffect> CRIMSON_AMBER_MEDALLION = registerStatusEffect("crimson_amber_medallion",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("8d56064f-619a-40f5-bc91-d74f9735d964"),
                            "Crimson Amber Medallion",
                            0.06F, AttributeModifier.Operation.MULTIPLY_BASE),
                            () -> Attributes.MAX_HEALTH)
                    .addTier("8d56064f-619a-40f5-bc91-d74f9735d964",1, 0.07F)
                    .addTier("8d56064f-619a-40f5-bc91-d74f9735d964",2, 0.08F).build());
    public static final RegistryObject<MobEffect> SHABRIRIS_WOE = registerStatusEffect("shabriris_woe",
            new ShabririWoeEffect(EffectType.PERSISTENT, (i, j) -> true, 234227227));
    public static final RegistryObject<MobEffect> GREEN_TURTLE = registerStatusEffect("green_turtle_talisman",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("574ddd07-f2cc-41c8-accf-b8e6369a7377"),
                            "Green turtle talisman",
                            0.2F, AttributeModifier.Operation.ADDITION),
                            EpicFightAttributes.STAMINA_REGEN).build());
    public static final RegistryObject<MobEffect> RADAGONS_SCARSEAL = registerStatusEffect("radagons_scarseal",
            new EffectBuilder(EffectType.PERSISTENT)
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
            new EffectBuilder(EffectType.PERSISTENT)
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
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("07f0bb21-c796-4808-8fd4-18d8df9f6347"),
                            "Dragoncrest Shield Talisman",
                            0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE)
                    .addTier("07f0bb21-c796-4808-8fd4-18d8df9f6347", 1, 0.13D)
                    .addTier("07f0bb21-c796-4808-8fd4-18d8df9f6347", 2, 0.17D)
                    .addTier("07f0bb21-c796-4808-8fd4-18d8df9f6347", 3, 0.20D)
                    .addTranslation(3, "item.enderring.dragoncrest_shield_talismanplus3").build());
    public static final RegistryObject<MobEffect> SACRIFICIAL_TWIG = registerSimpleEffect("sacrificial_twig",
            new EffectBuilder(EffectType.PERSISTENT).build());
    public static final RegistryObject<MobEffect> CRIMSON_SEED_TALISMAN = registerSimpleEffect("crimson_seed_talisman",
            new EffectBuilder(EffectType.PERSISTENT).build());
    public static final RegistryObject<MobEffect> CERULEAN_SEED_TALISMAN = registerSimpleEffect("cerulean_seed_talisman",
            new EffectBuilder(EffectType.PERSISTENT).build());
    public static final RegistryObject<MobEffect> STARSCOURGE_HEIRLOOM = registerSimpleEffect("starscourge_heirloom",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("6b6df324-3d12-4abe-9373-02298b897c77"),
                            "Starscourge Heirloom",
                            5F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.STRENGTH).build());
    public static final RegistryObject<MobEffect> PROSTHESIS_WEARER_HEIRLOOM = registerSimpleEffect("prosthesis_wearer_heirloom",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("acdf6fe1-31ec-411f-9946-67964a4957f5"),
                            "Prosthesis Wearer Heirloom",
                            5F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.DEXTERITY).build());
    public static final RegistryObject<MobEffect> STARGAZER_HEIRLOOM = registerStatusEffect("stargazer_heirloom",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("9690d174-be9c-4fac-a5d4-5dd31e00dc7b"),
                            "Stargazer Heirloom",
                            5F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.INTELLIGENCE).build());
    public static final RegistryObject<MobEffect> TWO_FINGERS_HEIRLOOM = registerStatusEffect("two_finger_heirloom",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("a5114201-7ce1-4781-b444-f72c5a2d0632"),
                            "Two Fingers Heirloom",
                            5F, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.FAITH).build());
    public static final RegistryObject<MobEffect> LONGTAIL_CAT_TALISMAN = registerStatusEffect("longtail_cat_talisman",
            new EffectBuilder(EffectType.FALL_DAMAGE).build());
    public static final RegistryObject<MobEffect> PRIMAL_GLINTSTONE_BLADE = registerStatusEffect("primal_glintstone_blade",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("8c9d806a-ac65-4423-be9d-d4b5e25e5f4f"),
                            "Primal Glintstone Blade",
                            0.85F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            () -> Attributes.MAX_HEALTH).build());
    public static final RegistryObject<MobEffect> MAGIC_SCORPION_CHARM = registerStatusEffect("magic_scorpion_charm",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("0e5a7192-845d-4b22-a086-91dbd2fe4d3f"),
                            "Magic Scorpion Charm",
                            0.12F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.MAGIC_DAMAGE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("dabb5e21-b470-4a8b-ad42-2b630bee1a1c"),
                            "Magic Scorpion Charm",
                            -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE).build());
    public static final RegistryObject<MobEffect> FIRE_SCORPION_CHARM = registerStatusEffect("fire_scorpion_charm",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("5ef1281a-1a85-49b7-b716-53aa726d4019"),
                            "Fire Scorpion Charm",
                            0.12F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.FIRE_DAMAGE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("9a583800-cb26-45b1-9963-e53eb71d96c1"),
                            "Fire Scorpion Charm",
                            -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE).build());
    public static final RegistryObject<MobEffect> LIGHTNING_SCORPION_CHARM = registerStatusEffect("lightning_scorpion_charm",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("8a67de75-8618-4d9b-8d3c-59245627ec6a"),
                            "Lightning Scorpion Charm",
                            0.12F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.LIGHT_DAMAGE)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("51070744-054b-4cd4-bdff-272cde582566"),
                            "Lightning Scorpion Charm",
                            -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE).build());
    public static final RegistryObject<MobEffect> SACRED_SCORPION_CHARM  = registerStatusEffect("sacred_scorpion_charm",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                                    UUID.fromString("c1c461e0-1c81-4f1c-833f-8ef2dd785c0a"),
                                    "Sacred Scorpion Charm",
                                    0.12F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.HOLY_DAMAGE)
                    .addAttribute(new AttributeModifier(
                                    UUID.fromString("1370a43c-ae66-464f-b78d-566660417a35"),
                                    "Sacred Scorpion Charm",
                                    -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE).build());
    //TODO: If equip load is implemented please add that to erdtrees favor
    public static final RegistryObject<MobEffect> ERDTREES_FAVOR = registerStatusEffect("erdtrees_favor",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("d0e53b5c-b3d0-478d-a426-cf5a6372c6f2"),
                            "ErdTree's Favor",
                            0.03D, AttributeModifier.Operation.MULTIPLY_BASE),
                            () -> Attributes.MAX_HEALTH)
                    .addTier("d0e53b5c-b3d0-478d-a426-cf5a6372c6f2", 1, 0.035D)
                    .addTier("d0e53b5c-b3d0-478d-a426-cf5a6372c6f2", 2, 0.04D)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("3c973081-d8d4-4dea-ad93-570ea884af51"),
                            "Erdtree's Favor",
                            0.07D, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EpicFightAttributes.MAX_STAMINA)
                    .addTier("3c973081-d8d4-4dea-ad93-570ea884af51", 1, 0.085D)
                    .addTier("3c973081-d8d4-4dea-ad93-570ea884af51", 2, 0.1D).build());
    public static final RegistryObject<MobEffect> RITUAL_SWORD_TALISMAN = registerStatusEffect("ritual_sword_talisman",
            new EffectBuilder(EffectType.PERSISTENT).build());
    public static final RegistryObject<MobEffect> RITUAL_SHIELD_TALISMAN = registerStatusEffect("ritual_shield_talisman",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("f981c6d2-3381-4633-bc94-5f7d3fbd813a"),
                            "Ritual Shield Talisman",
                            0.3D, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.PHYS_NEGATE, EntityAttributeInit.HOLY_NEGATE,
                            EntityAttributeInit.LIGHT_NEGATE, EntityAttributeInit.FIRE_NEGATE,
                            EntityAttributeInit.MAGIC_NEGATE).buildHpEffect(true));
    public static final RegistryObject<MobEffect> RED_FEATHERED_BRANCHSWORD = registerStatusEffect("red_feathered_branchsword",
            new EffectBuilder(EffectType.PERSISTENT).build());
    public static final RegistryObject<MobEffect> BLUE_FEATHERED_BRANCHSWORD = registerStatusEffect("blue_feathered_branchsword",
            new EffectBuilder(EffectType.PERSISTENT).addAttribute(new AttributeModifier(
                            UUID.fromString("3849ab13-31af-4dec-bdc5-aa3af1047457"),
                            "Blue Feathered Branchsword",
                            0.3D, AttributeModifier.Operation.MULTIPLY_TOTAL),
                    EntityAttributeInit.PHYS_NEGATE, EntityAttributeInit.HOLY_NEGATE,
                    EntityAttributeInit.LIGHT_NEGATE, EntityAttributeInit.FIRE_NEGATE,
                    EntityAttributeInit.MAGIC_NEGATE).buildHpEffect(false));
    public static final RegistryObject<MobEffect> SPELLDRAKE_TALISMAN = registerStatusEffect("spelldrake_talisman",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("412149bc-f790-4a06-a40c-9f024026eb65"),
                            "Spelldrake Talisman",
                            0.13D, AttributeModifier.Operation.MULTIPLY_TOTAL
                    ), EntityAttributeInit.MAGIC_NEGATE)
                    .addTier("412149bc-f790-4a06-a40c-9f024026eb65", 1, 0.17D)
                    .addTier("412149bc-f790-4a06-a40c-9f024026eb65", 2, 0.2D).build());
    public static final RegistryObject<MobEffect> FLAMEDRAKE_TALISMAN = registerStatusEffect("flamedrake_talisman",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("9de8ffae-2deb-4b5e-84fa-ca69fec7cfdd"),
                            "Flamedrake Talisman",
                            0.13D, AttributeModifier.Operation.MULTIPLY_TOTAL
                    ), EntityAttributeInit.FIRE_NEGATE)
                    .addTier("9de8ffae-2deb-4b5e-84fa-ca69fec7cfdd", 1, 0.17D)
                    .addTier("9de8ffae-2deb-4b5e-84fa-ca69fec7cfdd", 1, 0.2D).build());
    public static final RegistryObject<MobEffect> BOLTDRAKE_TALISMAN = registerStatusEffect("boltdrake_talisman",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("ac2e3caa-a368-4620-8d3a-07ffb9999259"),
                            "Boltdrake Talisman",
                            0.13D, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.LIGHT_NEGATE)
                    .addTier("ac2e3caa-a368-4620-8d3a-07ffb9999259", 1, 0.17D)
                    .addTier("ac2e3caa-a368-4620-8d3a-07ffb9999259", 2, 0.2D).build());
    public static final RegistryObject<MobEffect> HALIGDRAKE_TALISMAN = registerStatusEffect("haligdrake_talisman",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("66ee0b5a-fdf9-49a8-803a-64e670317d97"),
                            "Haligdrake Talisman",
                            0.13D, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.HOLY_NEGATE)
                    .addTier("66ee0b5a-fdf9-49a8-803a-64e670317d97", 1, 0.17D)
                    .addTier("66ee0b5a-fdf9-49a8-803a-64e670317d97", 1, 0.2D).build());
    public static final RegistryObject<MobEffect> PEARLDRAKE_TALISMAN = registerStatusEffect("pearldrake_talisman",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("ac8aa17e-3c5b-4120-8968-0cbadf272f02"),
                            "Pearldrake Talisman",
                            0.05D, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.HOLY_NEGATE, EntityAttributeInit.LIGHT_NEGATE,
                            EntityAttributeInit.FIRE_NEGATE, EntityAttributeInit.MAGIC_NEGATE)
                    .addTier("ac8aa17e-3c5b-4120-8968-0cbadf272f02", 1, 0.07D)
                    .addTier("ac8aa17e-3c5b-4120-8968-0cbadf272f02", 2, 0.09D).build());
    public static final RegistryObject<MobEffect> BLESSED_DEW_TALISMAN = registerStatusEffect("blessed_dew_talisman",
            new EffectBuilder(EffectType.PERSISTENT)
                    .setApplyTick((time, amp) -> time % 20 == 0).build());
    public static final RegistryObject<MobEffect> CERULEAN_AMBER_MEDALLION = registerStatusEffect("cerulean_amber_medallion",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("9b84dd1f-ba39-4cec-afa7-dcd7aa1cc3f4"),
                            "Cerulean Amber Medallion",
                            0.07D, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EntityAttributeInit.MAX_FP)
                    .addTier("9b84dd1f-ba39-4cec-afa7-dcd7aa1cc3f4", 1, 0.09D)
                    .addTier("9b84dd1f-ba39-4cec-afa7-dcd7aa1cc3f4", 2, 0.11D).build());
    public static final RegistryObject<MobEffect> VIRIDIAN_AMBER_MEDALLION = registerStatusEffect("viridian_amber_medallion",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("ac39b284-98c2-4a98-8518-d71ab1e94bf6"),
                            "Viridian Amber Medallion",
                            0.11D, AttributeModifier.Operation.MULTIPLY_TOTAL),
                            EpicFightAttributes.MAX_STAMINA)
                    .addTier("ac39b284-98c2-4a98-8518-d71ab1e94bf6", 1, 0.13D)
                    .addTier("ac39b284-98c2-4a98-8518-d71ab1e94bf6", 2, 0.15D).build());
    public static final RegistryObject<MobEffect> IMMUNIZING_HORN_CHARM = registerStatusEffect("immunizing_horn_charm",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("e2e008c8-8dcb-48f7-a6e7-42bb6157dc7a"),
                            "Immunizing Horn Charm",
                            90D, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.IMMUNITY)
                    .addTier("e2e008c8-8dcb-48f7-a6e7-42bb6157dc7a", 1, 140D).build());
    public static final RegistryObject<MobEffect> STALWART_HORN_CHARM = registerStatusEffect("stalwart_horn_charm",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("7999bc4a-52f5-4fab-a426-9172b8b9d3a2"),
                            "Stalwart Horn Charm",
                            90D, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.ROBUSTNESS)
                    .addTier("7999bc4a-52f5-4fab-a426-9172b8b9d3a2", 1, 140D).build());
    public static final RegistryObject<MobEffect> CLARIFYING_HORN_CHARM = registerStatusEffect("clarifying_horn_charm",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("653fb08c-9ba5-45c1-99d7-60e1734d4a5f"),
                            "Clarifying Horn Charm",
                            90D, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.FOCUS)
                    .addTier("653fb08c-9ba5-45c1-99d7-60e1734d4a5f", 1, 140D).build());
    public static final RegistryObject<MobEffect> MOTTLED_NECKLACE = registerStatusEffect("mottled_necklace",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("6944bd54-d28c-4c6d-b559-e1c50a4574d5"),
                            "Mottled Necklace",
                            40D, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.IMMUNITY, EntityAttributeInit.ROBUSTNESS,
                            EntityAttributeInit.FOCUS)
                    .addTier("6944bd54-d28c-4c6d-b559-e1c50a4574d5", 1, 60D).build());
    public static final RegistryObject<MobEffect> PRINCE_OF_DEATHS_PUSTULE = registerStatusEffect("prince_of_deaths_pustule",
            new EffectBuilder(EffectType.PERSISTENT)
                    .addAttribute(new AttributeModifier(
                            UUID.fromString("c0386557-a3b0-4c98-8493-6330e6adede7"),
                            "Prince of Deaths Pustule",
                            90D, AttributeModifier.Operation.ADDITION),
                            EntityAttributeInit.VITALITY)
                    .addTier("c0386557-a3b0-4c98-8493-6330e6adede7", 1, 140D)
                    .addTranslation(1, "item.enderring.prince_of_deaths_pustuleplus1").build());
    public static final RegistryObject<MobEffect> TWINBLADE_TALISMAN = registerStatusEffect("twinblade_talisman",
            new EffectBuilder(EffectType.PERSISTENT).build());
    public static final RegistryObject<MobEffect> LANCE_TALISMAN = registerStatusEffect("lance_talisman",
            new EffectBuilder(EffectType.PERSISTENT).build());
    public static final RegistryObject<MobEffect> CLAW_TALISMAN = registerStatusEffect("claw_talisman",
            new EffectBuilder(EffectType.PERSISTENT).build());
    public static final RegistryObject<MobEffect> FLOCKS_CANVAS_TALISMAN = registerStatusEffect("flocks_canvas_talisman",
            new EffectBuilder(EffectType.PERSISTENT).build());


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
