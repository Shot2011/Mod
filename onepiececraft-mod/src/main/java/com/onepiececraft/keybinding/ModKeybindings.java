package com.onepiececraft.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeybindings {
    // Devil Fruit Attacks 1-6
    public static final KeyMapping FRUIT_ATTACK_1 = new KeyMapping(
            "key.onepiececraft.fruit_attack_1", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_1, "key.categories.onepiececraft");
    public static final KeyMapping FRUIT_ATTACK_2 = new KeyMapping(
            "key.onepiececraft.fruit_attack_2", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_2, "key.categories.onepiececraft");
    public static final KeyMapping FRUIT_ATTACK_3 = new KeyMapping(
            "key.onepiececraft.fruit_attack_3", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_3, "key.categories.onepiececraft");
    public static final KeyMapping FRUIT_ATTACK_4 = new KeyMapping(
            "key.onepiececraft.fruit_attack_4", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_4, "key.categories.onepiececraft");
    public static final KeyMapping FRUIT_ATTACK_5 = new KeyMapping(
            "key.onepiececraft.fruit_attack_5", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_5, "key.categories.onepiececraft");
    public static final KeyMapping FRUIT_ATTACK_6 = new KeyMapping(
            "key.onepiececraft.fruit_attack_6", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_6, "key.categories.onepiececraft");

    // Faction / Status Screen
    public static final KeyMapping OPEN_MENU = new KeyMapping(
            "key.onepiececraft.open_menu", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.categories.onepiececraft");

    // Race Abilities
    public static final KeyMapping RACE_ABILITY_1 = new KeyMapping(
            "key.onepiececraft.race_ability_1", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.categories.onepiececraft");
    public static final KeyMapping RACE_ABILITY_2 = new KeyMapping(
            "key.onepiececraft.race_ability_2", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, "key.categories.onepiececraft");

    public static void register() {
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(FRUIT_ATTACK_1);
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(FRUIT_ATTACK_2);
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(FRUIT_ATTACK_3);
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(FRUIT_ATTACK_4);
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(FRUIT_ATTACK_5);
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(FRUIT_ATTACK_6);
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(OPEN_MENU);
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(RACE_ABILITY_1);
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(RACE_ABILITY_2);
    }
}
