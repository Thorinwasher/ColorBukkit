package dev.thorinwasher.utils.colorbukkit;

import java.awt.Color;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import dev.thorinwasher.utils.colorbukkit.utils.ColorUtil;
import dev.thorinwasher.utils.colorbukkit.utils.TextCompiler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class ColorBukkitAPI {
    
    /**
     * Opens a book color menu for specified user
     * @param user <p> The user that should open the menu </p>
     * @param consumer <p> What to execute with the resulting chosen color </p>
     */
    public static void getColorFromUser(Player user, Consumer<ChatColor> consumer) {
        getColorFromUser(user,0f,consumer);
    }
    
    /**
     * Opens a book color menu for specified user with specific hue
     * @param user <p> The user that should open the menu </p>
     * @param hue <p> a value between 0 and 1 specifying a hue</p>
     * @param consumer <p> What to execute with the resulting chosen color </p>
     */
    public static void getColorFromUser(Player user, float hue, Consumer<ChatColor> consumer) {
        ItemStack book = TextCompiler.generateBook(hue);
        user.openBook(book);
        ColorBukkit.instance.putIntoQueue(user,consumer);
    }
}
