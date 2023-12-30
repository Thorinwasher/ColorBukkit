package dev.thorinwasher.utils.colorbukkit.utils;

import java.awt.Color;

import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;

public class ColorUtil {
    
    /**
     * Convert to ChatColor from Color
     * @param color <p> The color to convert from </p>
     * @return <p> The converted color </p>
     */
    public static ChatColor fromColorToChatColor(Color color) {
        return ChatColor.of(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));
    }

    public static TextColor fromChatColor(ChatColor chatColor){
        return TextColor.fromHexString(chatColor.getName());
    }
}
