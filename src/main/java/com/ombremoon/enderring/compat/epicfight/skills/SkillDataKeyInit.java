package com.ombremoon.enderring.compat.epicfight.skills;

import com.ombremoon.enderring.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.SkillDataKey;

public class SkillDataKeyInit {
    public static DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(new ResourceLocation(EpicFightMod.MODID, "skill_data_keys"), Constants.MOD_ID);

    public static final RegistryObject<SkillDataKey<Integer>> COMBO_COUNTER = DATA_KEYS.register("combo_counter", () -> SkillDataKey.createIntKey(0, false, HeavyAttack.class));

    public static void register(IEventBus modEventBus) {
        DATA_KEYS.register(modEventBus);
    }
}
