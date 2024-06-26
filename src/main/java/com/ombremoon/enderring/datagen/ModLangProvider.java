package com.ombremoon.enderring.datagen;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.gui.screen.StarterScreen;
import com.ombremoon.enderring.common.init.StatInit;
import com.ombremoon.enderring.common.init.blocks.BlockInit;
import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.EntityInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.item.equipment.FlaskItem;
import com.ombremoon.enderring.common.object.world.effect.talisman.MarikasScarsealEffect;
import com.ombremoon.enderring.common.object.world.effect.talisman.RadagonsScarsealEffect;
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
            "astrologers", "Astrologer's"
    );

    //Include talismans that go up to plus one and no further
    private static final List<RegistryObject<Item>> PLUS_ONE_TALISMANS = List.of(

    );

    //Include talismans that go up to plus two and no higher/lower
    private static final List<RegistryObject<Item>> PLUS_TWO_TALISMANS = List.of(
        ItemInit.DRAGONCREST_SHIELD_TALISMAN
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
        add("itemGroup." + Constants.MOD_ID + ".tab", Constants.MOD_NAME);
        add("itemGroup.talismans.tab", Constants.ABBR_NAME + " Talismans");
        add("itemGroup.equipment.tab", Constants.ABBR_NAME + " Equipment");
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
        add(MarikasScarsealEffect.MARIKAS_SORESEAL, "Marika's Soreseal");
        add(RadagonsScarsealEffect.RADAGONS_SORESEAL, "Radagon's Soreseal");
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
