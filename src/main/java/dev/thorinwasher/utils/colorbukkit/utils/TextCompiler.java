package dev.thorinwasher.utils.colorbukkit.utils;

import java.awt.Color;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class TextCompiler {
    
    /**
     * Note that we are limited both in the number of pages we can generate, and what fits within one line
     * 
     * <p>
     * When I tested this, we are limited to approximately 54 pages before the book gets too large, and 50 "|"
     * approximately fits within a line when &lt; &gt; is included
     * </p>
     */
    public static final int HUE_BAR_LENGTH = 50;

    /**
     * Compile a message to be sent to the player
     * @param unformatedMessage <p> Unformated message without prefix </p>
     * @return  <p> A formated message with prefix </p>
     */
    public static String compileMessage(String unformatedMessage) {
        String prefix = "[Color Bukkit]";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < prefix.length(); i++) {
            float hue = (float) i / (prefix.length() -1 );
            Color color = Color.getHSBColor(hue, 0.6f, 1f);
            builder.append(ColorUtil.fromColorToChatColor(color)+String.valueOf(prefix.charAt(i)));
        }
        builder.append(ChatColor.WHITE + unformatedMessage);
        return builder.toString();
    }
    
    /**
     * Compile a book colorpicker gui
     * @param hue <p> A hue between 1 to 0 </p>
     * @return <p> A book colorpicker gui </p>
     */
    public static ItemStack generateBook(float hue) {
        //create the book
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        
        bookMeta.spigot().addPage(generatePage(hue));
        for(int i = 0; i < HUE_BAR_LENGTH; i++) {
            bookMeta.spigot().addPage(generatePage((float) i/(HUE_BAR_LENGTH)));
        }

        //set the title and author of this book
        bookMeta.setTitle("ColorBukkit");
        bookMeta.setAuthor("Thorinwasher");
        
        //update the ItemStack with this new meta
        book.setItemMeta(bookMeta);
        return book;
    }
    
    /**
     * Generate one page
     * @param hue <p> The hue used for the s-b plot within the page </p>
     * @return <p> A page </p>
     */
    public static BaseComponent[] generatePage(float hue) {
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
                float brightness = (float) (iHeight+1)/(maxHeight);//skip the first row as it's just black 
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
        builder.append("\u25C0").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cw hue " + (hue-step))).color(ChatColor.BLACK);
        for(int i = 0; i < HUE_BAR_LENGTH; i++) {
            float barHue = (float) i/(HUE_BAR_LENGTH);
            builder.append("|");
            builder.event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, String.valueOf(i+2)));
            builder.color(ColorUtil.fromColorToChatColor(Color.getHSBColor(barHue, 1, 1)));
        }
        builder.append("\u25B6").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cw hue " + (hue+step))).color(ChatColor.BLACK);
    }
}
