package com.ombremoon.enderring.datagen;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class ModItemModelProvider extends ItemModelProvider {
    private final Predicate<Item> exclusionPredicate = item -> !Arrays.stream(EXCLUSION_LIST).toList().contains(item);
    private final Predicate<Item> equipExclusionPredicate = item -> !Arrays.stream(EQUIP_EXCLUSION_LIST).toList().contains(item);
    private final Predicate<Item> exclusionPredicate1 = item -> !Arrays.stream(EXCLUSION_LIST_1).toList().contains(item);
    private final Predicate<Item> exclusionPredicate2 = item -> !Arrays.stream(EXCLUSION_LIST_2).toList().contains(item);
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerEquipModels(ItemInit.EQUIPMENT_LIST);
        registerItemModels(ItemInit.TALISMAN_LIST.keySet());
        registerItemModels(ItemInit.GENERAL_LIST);
        Arrays.stream(EXCLUSION_LIST).toList().forEach(this::tempItem);
        Arrays.stream(EQUIP_EXCLUSION_LIST).toList().forEach(this::tempItem);

    }

    private void registerItemModels(Collection<RegistryObject<? extends Item>> registryObjects) {
        registryObjects.stream().map(RegistryObject::get).filter(exclusionPredicate).filter(exclusionPredicate1).forEach(this::simpleGeneratedModel);
    }

    private void registerEquipModels(Collection<RegistryObject<? extends Item>> registryObjects) {
        registryObjects.stream().map(RegistryObject::get).filter(equipExclusionPredicate).filter(exclusionPredicate2).forEach(this::simpleHandHeldModel);
    }

    private void registerTempHandModels(Collection<RegistryObject<? extends Item>> registryObjects) {
        registryObjects.stream().map(RegistryObject::get).filter(exclusionPredicate2).forEach(this::tempItem);
    }

    protected ItemModelBuilder simpleGeneratedModel(Item item) {
        return simpleModel(item, mcLoc("item/generated"));
    }

    protected ItemModelBuilder simpleHandHeldModel(Item item) {
        return simpleModel(item, mcLoc("item/handheld"));
    }

    protected ItemModelBuilder tempHandHeldModel(Item item) {
        return tempModel(item, mcLoc("item/handheld"));
    }

    protected ItemModelBuilder simpleModel(Item item, ResourceLocation parent) {
        String name = getName(item);
        return singleTexture(name, parent, "layer0", modLoc("item/" + name));
    }

    protected ItemModelBuilder tempModel(Item item, ResourceLocation parent) {
        String name = getName(item);
        return singleTexture("temp", parent, "layer0", modLoc("item/" + name));
    }

    protected ItemModelBuilder tempItem(Item item) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                CommonClass.customLocation("item/" + "temp_texture"));
    }

    protected String getName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    protected String getName(Block item) {
        return ForgeRegistries.BLOCKS.getKey(item).getPath();
    }

    static final Item[] EXCLUSION_LIST = {
            ItemInit.DEBUG.get(),
            ItemInit.TEST_CATALYST.get(),
            ItemInit.SPIRIT_CALLING_BELL.get(),
            ItemInit.TORRENT_WHISTLE.get(),
            ItemInit.SACRED_TEAR.get(),
            ItemInit.WONDROUS_PHYSICK_FLASK.get(),
            ItemInit.TALISMAN_POUCH.get(),
            ItemInit.CRIMSON_CRYSTAL.get(),
            ItemInit.CERULEAN_CRYSTAL.get(),
            ItemInit.CERULEAN_HIDDEN.get(),
            ItemInit.BLOCKS_BETWEEN_RUNE.get(),
            ItemInit.BEWITCHING_BRANCH.get(),
            ItemInit.LAND_OCTOPUS_OVARY.get(),
            ItemInit.WHITE_FLESH_STRIP.get(),
            ItemInit.GOLDEN_ROWA.get(),
            ItemInit.INVIGORATING_CURED_MEAT.get(),
            ItemInit.INVIGORATING_CURED_WHITE_MEAT.get(),
            ItemInit.CLAY_POT.get(),
            ItemInit.HARDENED_POT.get(),
            ItemInit.HOLY_WATER.get(),
            ItemInit.ROPED_HOLY_WATER.get(),
            ItemInit.FETID_POT.get(),
            ItemInit.ROPED_FETID_POT.get(),
            ItemInit.SLEEP_POT.get(),
            ItemInit.MISSIONARY_COOKBOOK_ONE.get(),
            ItemInit.NOMADIC_WARRIOR_COOKBOOK_ONE.get(),
            ItemInit.NOMADIC_WARRIOR_COOKBOOK_TWO.get(),
            ItemInit.GREEN_TURTLE_TALISMAN.get(),
            ItemInit.GODFREY_ICON.get(),
            ItemInit.SACRIFICIAL_TWIG.get(),
            ItemInit.CERULEAN_SEED_TALISMAN.get(),
            ItemInit.CRIMSON_SEED_TALISMAN.get(),
            ItemInit.DRAGONCREST_SHIELD_TALISMAN.get(),
            ItemInit.MARIKAS_SCARSEAL.get(),
            ItemInit.RADAGONS_SCARSEAL.get(),
            ItemInit.STARSCOURGE_HEIRLOOM.get(),
            ItemInit.PROSTHESIS_WEARER_HEIRLOOM.get(),
            ItemInit.STARGAZER_HEIRLOOM.get(),
            ItemInit.TWO_FINGERS_HEIRLOOM.get(),
            ItemInit.LONGTAIL_CAT_TALISMAN.get(),
            ItemInit.PRIMAL_GLINTSTONE_BLADE.get(),
            ItemInit.FIRE_SCORPION_CHARM.get(),
            ItemInit.LIGHTNING_SCORPION_CHARM.get(),
            ItemInit.SACRED_SCORPION_CHARM.get(),
            ItemInit.MAGIC_SCORPION_CHARM.get(),
            ItemInit.ERDTREES_FAVOR.get(),
            ItemInit.RITUAL_SHIELD_TALISMAN.get(),
            ItemInit.RITUAL_SWORD_TALISMAN.get(),
            ItemInit.RED_FEATHERED_BRANCHSWORD.get(),
            ItemInit.BLUE_FEATHERED_BRANCHSWORD.get(),
            ItemInit.SPELLDRAKE_TALISMAN.get(),
            ItemInit.FLAMEDRAKE_TALISMAN.get(),
            ItemInit.BOLTDRAKE_TALISMAN.get(),
            ItemInit.HALIGDRAKE_TALISMAN.get(),
            ItemInit.PEARLDRAKE_TALISMAN.get(),
            ItemInit.BLESSED_DEW_TALISMAN.get(),
            ItemInit.CERULEAN_AMBER_MEDALLION.get(),
            ItemInit.VIRIDIAN_AMBER_MEDALLION.get(),
            ItemInit.CLARIFYING_HORN_CHARM.get(),
            ItemInit.IMMUNIZING_HORN_CHARM.get(),
            ItemInit.STALWART_HORN_CHARM.get(),
            ItemInit.MOTTLED_NECKLACE.get(),
            ItemInit.PRINCE_OF_DEATHS_PUSTULE.get(),
            ItemInit.TWINBLADE_TALISMAN.get(),
            ItemInit.CLAW_TALISMAN.get(),
            ItemInit.LANCE_TALISMAN.get(),
            ItemInit.FLOCKS_CANVAS_TALISMAN.get(),
            ItemInit.ANCESTRAL_SPIRITS_HORN.get(),
            ItemInit.TAKERS_CAMEO.get(),
            ItemInit.LORD_OF_BLOODS_EXULTATION.get(),
            ItemInit.KINDRED_OF_ROTS_EXULTATION.get(),
            ItemInit.MILLICENTS_PROSTHESIS.get(),
            ItemInit.WINGED_SWORD_INSIGNIA.get(),
            ItemInit.DAEDICARS_WOE.get(),
            ItemInit.ARROWS_STING_TALISMAN.get(),
            ItemInit.CRIMSONSPILL_CRYSTAL.get(),
            ItemInit.CRIMSONBURST_CRYSTAL.get(),
            ItemInit.GREENSPILL_CRYSTAL.get(),
            ItemInit.GREENBURST_CRYSTAL.get(),
            ItemInit.STRENGTHKNOT_CRYSTAL.get(),
            ItemInit.DEXTERITYKNOT_CRYSTAL.get(),
            ItemInit.FAITHKNOT_CRYSTAL.get(),
            ItemInit.INTELLIGENCEKNOT_CRYSTAL.get(),
            ItemInit.OPALINEHARD_CRYSTAL.get(),
            ItemInit.SPECKLEDHARD_CRYSTAL.get(),
            ItemInit.LIGHTNINGSHROUDING_CRACKED.get(),
            ItemInit.HOLYSHROUDING_CRACKED.get(),
            ItemInit.FLAMESHROUDING_CRACKED.get(),
            ItemInit.MAGICSHROUDING_CRACKED.get(),
            ItemInit.GRACE_SITE.get()

    };

    static final Item[] EQUIP_EXCLUSION_LIST = {
            EquipmentInit.DAGGER.get(),
            EquipmentInit.LORDSWORNS_GREATSWORD.get(),
            EquipmentInit.GREAT_KNIFE.get(),
            EquipmentInit.BLACK_KNIFE.get(),
            EquipmentInit.SHORT_SWORD.get(),
            EquipmentInit.LONGSWORD.get(),
            EquipmentInit.ESTOC.get(),
            EquipmentInit.CLUB.get(),
            EquipmentInit.SHORT_SPEAR.get(),
            EquipmentInit.SHORTBOW.get(),
            EquipmentInit.LONGBOW.get(),
            EquipmentInit.GIANT_SEAL.get(),
            EquipmentInit.LARGE_LEATHER_SHIELD.get(),
            EquipmentInit.RIVETED_WOODEN_SHIELD.get(),
            EquipmentInit.BUCKLER.get(),
            EquipmentInit.SCRIPTURE_WOODEN_SHIELD.get(),
            EquipmentInit.RIFT_SHIELD.get(),
            EquipmentInit.BLUE_CREST_HEATER_SHIELD.get(),
            EquipmentInit.HEATER_SHIELD.get(),
            EquipmentInit.RICKETY_SHIELD.get(),
            EquipmentInit.RED_THORN_ROUNDSHIELD.get(),
            EquipmentInit.BLUE_CLOTH_COWL.get(),
            EquipmentInit.BLUE_CLOTH_VEST.get(),
            EquipmentInit.BLUE_CLOTH_LEGGINGS.get(),
            EquipmentInit.BLUE_CLOTH_GREAVES.get(),
            EquipmentInit.BONE_ARROW.get(),
            EquipmentInit.BONE_BOLT.get(),
            // Astrologer Set
            EquipmentInit.ASTROLOGER_HOOD.get(),
            EquipmentInit.ASTROLOGER_ROBE.get(),
            EquipmentInit.ASTROLOGER_GLOVES.get(),
            EquipmentInit.ASTROLOGER_TROUSERS.get(),

            // Bandit Set
            EquipmentInit.BANDIT_MASK.get(),
            EquipmentInit.BANDIT_GARBS.get(),
            EquipmentInit.BANDIT_BRACERS.get(),
            EquipmentInit.BANDIT_BOOTS.get(),

            // Confessor Set
            EquipmentInit.CONFESSOR_HOOD.get(),
            EquipmentInit.CONFESSOR_ARMOR.get(),
            EquipmentInit.CONFESSOR_GAUNTLETS.get(),
            EquipmentInit.CONFESSOR_BOOTS.get(),

            // Hero Set
            EquipmentInit.HERO_HELM.get(),
            EquipmentInit.HERO_ARMOR.get(),
            EquipmentInit.HERO_GAUNTLETS.get(),
            EquipmentInit.HERO_BOOTS.get(),

            // Prisoner Set
            EquipmentInit.PRISONER_IRON_MASK.get(),
            EquipmentInit.PRISONER_CLOTHING.get(),

            // Prophet Set
            EquipmentInit.PROPHET_BLINDFOLD.get(),
            EquipmentInit.PROPHET_ROBE.get(),
            EquipmentInit.PROPHET_TROUSERS.get(),

            // Land of Reeds Set
            EquipmentInit.LAND_OF_REEDS_HELM.get(),
            EquipmentInit.LAND_OF_REEDS_ARMOR.get(),
            EquipmentInit.LAND_OF_REEDS_GAUNTLETS.get(),
            EquipmentInit.LAND_OF_REEDS_LEGGINGS.get(),

            // Vagabond Set
            EquipmentInit.VAGABOND_KNIGHT_HELM.get(),
            EquipmentInit.VAGABOND_KNIGHT_ARMOR.get(),
            EquipmentInit.VAGABOND_KNIGHT_GAUNTLETS.get(),
            EquipmentInit.VAGABOND_KNIGHT_GREAVES.get(),
    };

    static final Item[] EXCLUSION_LIST_1 = {
            ItemInit.GRACE_SITE.get()
    };

    static final Item[] EXCLUSION_LIST_2 = {
            EquipmentInit.SCIMITAR.get(),
            EquipmentInit.BATTLE_AXE.get(),
            EquipmentInit.BROADSWORD.get(),
            EquipmentInit.HALBERD.get(),
            EquipmentInit.UCHIGATANA.get(),
            EquipmentInit.CLUB.get(),
            EquipmentInit.GLINTSTONE_STAFF.get(),
            EquipmentInit.ASTROLOGER_STAFF.get(),
            EquipmentInit.GUARDIAN_SWORDSPEAR.get(),
    };

}

