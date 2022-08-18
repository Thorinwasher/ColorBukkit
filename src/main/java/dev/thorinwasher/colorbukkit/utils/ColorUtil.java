package dev.thorinwasher.colorbukkit.utils;

import java.awt.Color;

import net.md_5.bungee.api.ChatColor;

public class ColorUtil {
    public static ChatColor fromColorToChatColor(Color color) {
        return ChatColor.of(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));
    }
}
