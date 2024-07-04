package com.ombremoon.enderring.datagen;

import com.google.common.collect.ImmutableMap;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.gui.screen.StarterScreen;
import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.StatInit;
import com.ombremoon.enderring.common.init.blocks.BlockInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.EntityInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.item.equipment.FlaskItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModLangProvider extends LanguageProvider {

    protected static final Map<String, String> REPLACE_LIST = ImmutableMap.of(
            "tnt", "TNT",
            "sus", "",
            "shabriris", "Shabriri's",
            "radagons", "Radagon's",
            "astrologers", "Astrologer's",
            "giants", "Giant's"
    );

    //Include talismans that go up to plus one and no further
    private static final List<RegistryObject<Item>> PLUS_ONE_TALISMANS = List.of(
            ItemInit.CLARIFYING_HORN_CHARM,
            ItemInit.IMMUNIZING_HORN_CHARM,
            ItemInit.STALWART_HORN_CHARM,
            ItemInit.MOTTLED_NECKLACE
    );

    //Include talismans that go up to plus two and no higher/lower
    private static final List<RegistryObject<Item>> PLUS_TWO_TALISMANS = List.of(
            ItemInit.DRAGONCREST_SHIELD_TALISMAN,
            ItemInit.ERDTREES_FAVOR,
            ItemInit.FLAMEDRAKE_TALISMAN,
            ItemInit.BOLTDRAKE_TALISMAN,
            ItemInit.HALIGDRAKE_TALISMAN,
            ItemInit.PEARLDRAKE_TALISMAN,
            ItemInit.SPELLDRAKE_TALISMAN,
            ItemInit.CERULEAN_AMBER_MEDALLION,
            ItemInit.VIRIDIAN_AMBER_MEDALLION,
            ItemInit.CRIMSON_AMBER_MEDALLION
    );

    public ModLangProvider(PackOutput gen) {
        super(gen, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ItemInit.ITEMS.getEntries().forEach(this::itemLang);
        BlockInit.BLOCKS.getEntries().forEach(this::blockLang);
        EntityInit.ENTITIES.getEntries().forEach(this::entityLang);
        EntityAttributeInit.ATTRIBUTES.getEntries().forEach(this::attributeLang);
        StatusEffectInit.STATUS_EFFECTS.getEntries().forEach(this::effectLang);
        SpellInit.SPELL_TYPE.getEntries().forEach(this::spellLang);
        MenuTypeInit.MENU_TYPES.getEntries().forEach(this::menuLang);
        StatInit.STATS.getEntries().forEach(this::statLang);
        tabLang();
        originLang();

        talismanTooltips();
        manualEntries();
        upgradedLang(PLUS_ONE_TALISMANS, 1);
        upgradedLang(PLUS_TWO_TALISMANS, 2);
    }

    protected void upgradedLang(List<RegistryObject<Item>> list, int tier) {
        for (RegistryObject<Item> item : list) {
            for (int i = 1; i <= tier; i++) {
                add(item.get().getDescriptionId() + "plus" + i,
                        checkReplace(item) + " +" + i);
            }
        }
    }

    protected void itemLang(RegistryObject<Item> entry) {
        if (!(entry.get() instanceof BlockItem) || entry.get() instanceof ItemNameBlockItem) {
            addItem(entry, checkReplace(entry));
        }
    }

    protected void blockLang(RegistryObject<Block> entry) {
        addBlock(entry, checkReplace(entry));
    }

    protected void entityLang(RegistryObject<EntityType<?>> entry) {
        addEntityType(entry, checkReplace(entry));
    }

    protected void effectLang(RegistryObject<MobEffect> entry) {
        addEffect(entry, checkReplace(entry));
    }

    protected void spellLang(RegistryObject<SpellType<?>> entry) {
        add(entry.get().createSpell().getDescriptionId(), checkReplace(entry));
    }

    protected void menuLang(RegistryObject<MenuType<?>> entry) {
        add(MenuTypeInit.getDescriptionId(entry), checkReplaceMenu(entry));
    }

    protected void tabLang() {
        add("itemgroup." + Constants.MOD_ID + ".tab", Constants.MOD_NAME);
        add("itemgroup.talismans.tab", Constants.ABBR_NAME + " Talismans");
        add("itemgroup.equipment.tab", Constants.ABBR_NAME + " Equipment");
    }

    protected void attributeLang(RegistryObject<Attribute> entry) {
        add(entry.get().getDescriptionId(), checkReplace(entry));
    }

    protected void originLang() {
        Arrays.stream(StarterScreen.Base.values()).toList().forEach(this::originLang);
    }

    private void originLang(StarterScreen.Base entry) {
        add(Constants.MOD_ID + ".origin." + entry.getName(), checkReplace(entry.getName()));
    }

    private void statLang(RegistryObject<ResourceLocation> entry) {
        add("stat." + Constants.MOD_ID + "." + entry.get().getPath(), checkReplace(entry));
    }

    protected void manualEntries() {
        add(Constants.MOD_ID + ".menu.character_origin", "Origin");
        add(Constants.MOD_ID + ".menu.keepsake", "Keepsake");
        add(Constants.MOD_ID + ".menu.starter_confirm", "Confirm Character");
        add(Constants.MOD_ID + ".menu.confirm", "Confirm");
        add(Constants.MOD_ID + ".flask.potency", "Potency");
        add(Constants.MOD_ID + ".flask.charges", "Charges");
        add(Constants.MOD_ID + ".flask.empty_tear", "Empty");
        add(Constants.MOD_ID + ".flask.tear_1", "1st Crystal Tear Slot");
        add(Constants.MOD_ID + ".flask.tear_2", "2nd Crystal Tear Slot");
        add(FlaskItem.NO_TEARS, "Wondrous Physick is currently holding no Crystal Tears");
        add("item.enderring.marikas_scarsealplus1", "Marika's Soreseal");
        add("item.enderring.radagons_scarsealplus1", "Radagon's Soreseal");
        add("item.enderring.dragoncrest_shield_talismanplus3", "Dragoncrest Greatshield Talisman");
        add("item.enderring.winged_sword_insigniaplus1", "Rotten Winged Sword Insignia");
        add("effect.enderring.rotten_winged_sword_insigniaplus1", "Rotten Winged Sword Insignia +1");
        add("effect.enderring.rotten_winged_sword_insigniaplus2", "Rotten Winged Sword Insignia +2");
        add("effect.enderring.millicents_prosthesisplus1", "Millicents Prosthesis +1");
        add("effect.enderring.millicents_prosthesisplus2", "Millicents Prosthesis +2");
        add("item.enderring.prince_of_deaths_pustuleplus1", "Prince of Death's Cyst");
        add("tooltip.enderring.more_info", "Hold shift for more info.");
        add("curios.identifier.talismans", "Talismans");
    }

    protected void talismanTooltips() {
        addTooltip(ItemInit.CRIMSON_AMBER_MEDALLION, "Raises maximum HP.");
        addTooltip(ItemInit.CRIMSON_SEED_TALISMAN, "Boosts HP regeneration from Flask of Crimson Tears.");
        addTooltip(ItemInit.BLESSED_DEW_TALISMAN, "Slowly restores HP.");
        addTooltip(ItemInit.CERULEAN_AMBER_MEDALLION, "Raises maximum FP.");
        addTooltip(ItemInit.CERULEAN_SEED_TALISMAN, "Boosts FP regeneration from Flask of Cerulean Tears.");
        addTooltip(ItemInit.VIRIDIAN_AMBER_MEDALLION, "Raises maximum stamina.");
        addTooltip(ItemInit.GREEN_TURTLE_TALISMAN, "Raises stamina recovery speed.");
        addTooltip(ItemInit.ERDTREES_FAVOR, "Raises maximum HP and stamina.");
        addTooltip(ItemInit.RADAGONS_SCARSEAL, "Raises Vigor, Endurance, Strength and Dexterity but increases damage taken.");
        addTooltip(ItemInit.MARIKAS_SCARSEAL, "Raises Mind, Intelligence, Faith and Arcane but increases damage taken.");
        addTooltip(ItemInit.STARSCOURGE_HEIRLOOM, "Raises Strength.");
        addTooltip(ItemInit.PROSTHESIS_WEARER_HEIRLOOM, "Raises Dexterity.");
        addTooltip(ItemInit.STARGAZER_HEIRLOOM, "Raises Intelligence.");
        addTooltip(ItemInit.TWO_FINGERS_HEIRLOOM, "Raises Faith.");
        addTooltip(ItemInit.DRAGONCREST_SHIELD_TALISMAN, "Boosts Physical Damage negation.");
        addTooltip(ItemInit.SPELLDRAKE_TALISMAN, "Boosts Magic Damage negation.");
        addTooltip(ItemInit.FLAMEDRAKE_TALISMAN, "Boosts Fire Damage negation.");
        addTooltip(ItemInit.BOLTDRAKE_TALISMAN, "Boosts Lightning Damage negation.");
        addTooltip(ItemInit.HALIGDRAKE_TALISMAN, "Boosts Holy Damage negation.");
        addTooltip(ItemInit.PEARLDRAKE_TALISMAN, "Boosts Elemental Damage negation.");
        addTooltip(ItemInit.IMMUNIZING_HORN_CHARM, "Raises Immunity.");
        addTooltip(ItemInit.STALWART_HORN_CHARM, "Greatly raises Robustness.");
        addTooltip(ItemInit.CLARIFYING_HORN_CHARM, "Raises Focus.");
        addTooltip(ItemInit.MOTTLED_NECKLACE, "Raises Robustness, Immunity and Focus.");
        addTooltip(ItemInit.PRINCE_OF_DEATHS_PUSTULE, "Raises Vitality.");
        addTooltip(ItemInit.PRIMAL_GLINTSTONE_BLADE, "Spells consume less FP, but maximum HP is reduced.");
        addTooltip(ItemInit.MAGIC_SCORPION_CHARM, "Raises Magic Damage, but lowers damage negation.");
        addTooltip(ItemInit.FIRE_SCORPION_CHARM, "Raises Fire Damage, but lowers damage negation.");
        addTooltip(ItemInit.LIGHTNING_SCORPION_CHARM, "Raises Lightning Damage, but lowers damage negation.");
        addTooltip(ItemInit.SACRED_SCORPION_CHARM, "Raises Holy Damage, but lowers damage negation.");
        addTooltip(ItemInit.RED_FEATHERED_BRANCHSWORD, "Raises attack power when HP is low.");
        addTooltip(ItemInit.BLUE_FEATHERED_BRANCHSWORD, "Raises defense when HP is low.");
        addTooltip(ItemInit.RITUAL_SHIELD_TALISMAN, "Raises defense when HP is at maximum.");
        addTooltip(ItemInit.RITUAL_SWORD_TALISMAN, "Raises attack power when HP is at maximum.");
        addTooltip(ItemInit.LONGTAIL_CAT_TALISMAN, "Renders the wearer immune to fall damage.");
        addTooltip(ItemInit.SACRIFICIAL_TWIG, "Prevents rune loss on death, but will be lost its self in exchange.");
        addTooltip(ItemInit.TWINBLADE_TALISMAN, "Enhances final hit of chain attacks.");
        addTooltip(ItemInit.LANCE_TALISMAN, "Enhances attacks on horseback.");
        addTooltip(ItemInit.CLAW_TALISMAN, "Enhances jump attacks.");
        addTooltip(ItemInit.FLOCKS_CANVAS_TALISMAN, "Greatly raises the potency of Incantations.");
        addTooltip(ItemInit.TAKERS_CAMEO, "Restores HP upon defeating enemies.");
        addTooltip(ItemInit.ANCESTRAL_SPIRITS_HORN, "Restores FP upon defeating enemies.");
        addTooltip(ItemInit.KINDRED_OF_ROTS_EXULTATION, "Poisoning or Rot in vicinity increases attack power.");
        addTooltip(ItemInit.LORD_OF_BLOODS_EXULTATION, "Blood loss in vicinity increases attack power.");
        addTooltip(ItemInit.WINGED_SWORD_INSIGNIA, "Raises attack power with successive attacks.");
        addTooltip(ItemInit.DAEDICARS_WOE, "Increases damage taken.");
        addTooltip(ItemInit.ARROWS_STING_TALISMAN, "Raises attack power of arrows and bolts.");
        addTooltip(ItemInit.MILLICENTS_PROSTHESIS, "Boosts Dexterity, raises attack power with successive attacks.");
    }

    protected void addTooltip(RegistryObject<Item> item, String translation) {
        add(item.get().getDescriptionId().replace("item", "tooltip"), translation);
    }

    protected String checkReplaceMenu(RegistryObject<MenuType<?>> registryObject) {
        return checkReplace(registryObject).replace(" Menu", "");
    }

    protected String checkReplace(RegistryObject<?> registryObject) {
        return Arrays.stream(registryObject.getId().getPath().split("_"))
                .map(this::checkReplace)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(" "))
                .trim();
    }

    protected String checkReplace(String string) {
        return REPLACE_LIST.containsKey(string) ? REPLACE_LIST.get(string) : StringUtils.capitalize(string);
    }

}
