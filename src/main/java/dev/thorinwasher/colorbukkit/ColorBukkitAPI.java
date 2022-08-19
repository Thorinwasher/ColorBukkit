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
        
        bookMeta.spigot().addPage(generatePage(hue));
        for(int i = 0; i < 50; i++) {
            bookMeta.spigot().addPage(generatePage((float) i/50));
        }

        //set the title and author of this book
        bookMeta.setTitle("ColorBukkit");
        bookMeta.setAuthor("Thorinwasher");
        
        //update the ItemStack with this new meta
        book.setItemMeta(bookMeta);
        
        user.openBook(book);
        ColorBukkit.instance.putIntoQueue(user,consumer);
    }
    
    /**
     * Generate one page
     * @param hue <p> The hue used for the s-b plot within the page</p>
     * @return <p> A page </p>
     */
    private static BaseComponent[] generatePage(float hue) {
        int maxHeight = 12;
        int maxWidth = 12;
        ComponentBuilder builder = new ComponentBuilder();
        
        compileSBPlot(builder,maxHeight,maxWidth,hue);
        compileHueBar(builder, hue);
        return builder.create();
    }
    
    /**
     * Compile the plot with saturation and brightness
     * @param builder <p> A component builder used to build one page</p>
     * @param maxHeight <p> height of the plot</p>
     * @param maxWidth <p> width of the plot </p>
     * @param hue <p> The hue to use for the saturation-brightness plot</p>
     */
    private static void compileSBPlot(ComponentBuilder builder, int maxHeight, int maxWidth, float hue) {
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
    }
    
    /**
     * Compile the bar displaying all possible hue types
     * @param builder <p> A component builder used to build one page</p>
     * @param hue <p> The current hue in use</p>
     */
    private static void compileHueBar(ComponentBuilder builder, float hue) {
        float step = (float) 1/360;
        int barLength = 50;
        builder.append("<").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cw hue " + (hue-step))).color(ChatColor.BLACK);
        for(int i = 0; i < barLength; i++) {
            float barHue = (float) i/(barLength-1);
            builder.append("|");
            builder.event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, String.valueOf(i+1)));
            builder.color(ColorUtil.fromColorToChatColor(Color.getHSBColor(barHue, 1, 1)));
        }
        builder.append(">").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cw hue " + (hue+step))).color(ChatColor.BLACK);
    }
}
