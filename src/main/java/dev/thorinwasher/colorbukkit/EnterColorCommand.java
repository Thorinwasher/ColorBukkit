package dev.thorinwasher.colorbukkit;

import java.util.function.Consumer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class EnterColorCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Consumer<ChatColor> consumer = ColorBukkit.instance.getFromQueue((Player) sender);
            if(consumer == null) {
                return true;
            }
            if (args.length > 1) {
                ColorBukkitAPI.getColorFromUser((Player) sender, Float.parseFloat(args[1]), consumer);
                return true;
            }
            ChatColor color = ChatColor.of(args[0]);
            consumer.accept(color);
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

}
