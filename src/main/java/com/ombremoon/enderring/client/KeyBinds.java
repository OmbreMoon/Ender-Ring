package com.ombremoon.enderring.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;
import yesman.epicfight.client.input.CombatKeyMapping;

public class KeyBinds {
    public static final String KEY_CATEGORY_ER = "key.category.enderring.er";
    public static final String KEY_OPEN_QUICK_ACCESS = "key.enderring.open_quick_access";
    public static final String KEY_CYCLE_QUICK_ACCESS = "key.enderring.cycle_quick_access";
    public static final String KEY_USE_QUICK_ACCESS = "key.enderring.use_quick_access";
    public static final String KEY_HEAVY_ATTACK = "key.enderring.heavy_attack";
    public static final String KEY_ASH_OF_WAR = "key.enderring.ash_of_war";

    public static final KeyMapping OPEN_QUICK_ACCESS_BINDING = new KeyMapping(KEY_OPEN_QUICK_ACCESS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, KEY_CATEGORY_ER);

    public static final KeyMapping CYCLE_QUICK_ACCESS_BINDING = new KeyMapping(KEY_CYCLE_QUICK_ACCESS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, KEY_CATEGORY_ER);

    public static final KeyMapping USE_QUICK_ACCESS_BINDING = new KeyMapping(KEY_USE_QUICK_ACCESS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_I, KEY_CATEGORY_ER);

    public static final KeyMapping HEAVY_ATTACK_BINDING = new CombatKeyMapping(KEY_HEAVY_ATTACK,
            InputConstants.Type.MOUSE, 0, KEY_CATEGORY_ER);

    public static final KeyMapping ASH_OF_WAR_BINDING = new CombatKeyMapping(KEY_ASH_OF_WAR,
            InputConstants.Type.KEYSYM, InputConstants.KEY_Z, KEY_CATEGORY_ER);

    public static KeyMapping getCastKey() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.options.keyUse;
    }
}
