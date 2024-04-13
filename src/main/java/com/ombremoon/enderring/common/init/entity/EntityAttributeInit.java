package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityAttributeInit {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Constants.MOD_ID);

    public static final RegistryObject<Attribute> RUNE_LEVEL = ATTRIBUTES.register("rune_level", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".rune_level", 1.0D, 1.0D, 713.0D).setSyncable(true));
    public static final RegistryObject<Attribute> RUNES_HELD = ATTRIBUTES.register("runes_held", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".runes_held", 0.0D, 0.0D, 999999999.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MAX_FP = ATTRIBUTES.register("max_fp", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".max_fp", 1.0D, 1.0D, 450.0D).setSyncable(true));
    public static final RegistryObject<Attribute> VIGOR = ATTRIBUTES.register("vigor", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".vigor", 1.0D, 1.0D, 99.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MIND = ATTRIBUTES.register("mind", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".mind", 1.0D, 1.0D, 99.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ENDURANCE = ATTRIBUTES.register("endurance", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".endurance", 1.0D, 1.0D, 99.0D).setSyncable(true));
    public static final RegistryObject<Attribute> STRENGTH = ATTRIBUTES.register("strength", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".strength", 1.0D, 1.0D, 99.0D).setSyncable(true));
    public static final RegistryObject<Attribute> DEXTERITY = ATTRIBUTES.register("dexterity", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".dexterity", 1.0D, 1.0D, 99.0D).setSyncable(true));
    public static final RegistryObject<Attribute> INTELLIGENCE = ATTRIBUTES.register("intelligence", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".intelligence", 1.0D, 1.0D, 99.0D).setSyncable(true));
    public static final RegistryObject<Attribute> FAITH = ATTRIBUTES.register("faith", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".faith", 1.0D, 1.0D, 99.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ARCANE = ATTRIBUTES.register("arcane", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".arcane", 1.0D, 1.0D, 99.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ROBUSTNESS = ATTRIBUTES.register("robustness", () -> new RangedAttribute("attribute.name." + Constants.MOD_ID + ".robustness", 1.0D, 1.0D, 230.0D).setSyncable(true));

    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
    }
}
