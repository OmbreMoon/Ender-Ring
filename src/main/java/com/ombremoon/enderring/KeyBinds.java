package com.ombremoon.enderring;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static final String KEY_CATEGORY_ER = "key.category.enderring.er";
    public static final String KEY_OPEN_QUICK_ACCESS = "key.enderring.open_quick_access";
    public static final String KEY_CYCLE_QUICK_ACCESS = "key.enderring.cycle_quick_access";
    public static final String KEY_USE_QUICK_ACCESS = "key.enderring.use_quick_access";

    public static final KeyMapping OPEN_QUICK_ACCESS_BINDING = new KeyMapping(KEY_OPEN_QUICK_ACCESS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, KEY_CATEGORY_ER);

    public static final KeyMapping CYCLE_QUICK_ACCESS_BINDING = new KeyMapping(KEY_CYCLE_QUICK_ACCESS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, KEY_CATEGORY_ER);

    public static final KeyMapping USE_QUICK_ACCESS_BINDING = new KeyMapping(KEY_USE_QUICK_ACCESS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_I, KEY_CATEGORY_ER);
}
