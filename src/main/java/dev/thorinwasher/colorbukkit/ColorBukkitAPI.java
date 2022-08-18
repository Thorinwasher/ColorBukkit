package dev.thorinwasher.colorbukkit;

import java.awt.Color;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import dev.thorinwasher.colorbukkit.utils.ColorUtil;
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
      //create the book
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        
        ComponentBuilder builder = new ComponentBuilder();
        
        int maxHeight = 13;
        int maxWidth = 12;
        
        for(int iHeight = 0; iHeight < maxHeight; iHeight++) {
            for(int iWidth = 0; iWidth < 12; iWidth++) {
                float brightness = (float) iHeight/(maxHeight-1);
                float saturation = (float) iWidth/(maxWidth-1);
                Color javaColor = Color.getHSBColor(hue,saturation,brightness);
                ChatColor color = ColorUtil.fromColorToChatColor(javaColor);
                builder.append("\u2588");
                builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cw " + color.getName()));
                builder.color(color);
            }
            builder.append("\n");
        }
        
        for(int i = 0; i < 54; i++) {
            float barHue = (float) i/(54-1);
            builder.append("|");
            builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cw hue " + barHue));
            builder.color(ColorUtil.fromColorToChatColor(Color.getHSBColor(barHue, 1, 1)));
        }
        
        BaseComponent[] page = builder.create();
        //add the page to the meta
        bookMeta.spigot().addPage(page);

        //set the title and author of this book
        bookMeta.setTitle("ColorBukkit");
        bookMeta.setAuthor("Thorinwasher");
        
        //update the ItemStack with this new meta
        book.setItemMeta(bookMeta);
        
        user.openBook(book);
        ColorBukkit.instance.putIntoQueue(user,consumer);
    }
}
