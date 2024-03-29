package dev.thorinwasher.utils.colorbukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import dev.thorinwasher.utils.colorbukkit.utils.TextCompiler;
import net.md_5.bungee.api.ChatColor;

public class ColorBukkit extends JavaPlugin implements Listener{
    public static ColorBukkit instance;
    
    private final Map<Player,Consumer<ChatColor>> queue= new HashMap<>();
    
    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s,
            @NotNull String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command!");
        }
        
        switch(command.getName()) {
        case "colorbukkit":
            if(commandSender.hasPermission("colorbukkit")) {
                ColorBukkitAPI.getColorFromUser((Player)commandSender, (color) -> {
                    commandSender.sendMessage(TextCompiler.compileMessage(" The color you chose was " + color + color.getName()));
                });
            }
            return true;
        case "cw":
            return new EnterColorCommand().onCommand(commandSender, command, s, args);
        }
        
        return false;
    }
    
    /**
     * Registers commands for this plugin
     */
    private void registerCommands() {
        String[] commands = new String[] {"colorbukkit", "cw"};
        
        for (String command : commands) {
            PluginCommand stargateCommand = this.getCommand(command);
            if (stargateCommand != null) {
                stargateCommand.setExecutor(this);
            }
        }
    }

    /**
     *
     * @param user
     * @param consumer
     */
    public void putIntoQueue(Player user, Consumer<ChatColor> consumer) {
        if(user == null) {
            return;
        }
        queue.put(user, consumer);
        
    }

    /**
     *
     * @param sender
     * @return
     */
    public Consumer<ChatColor> getFromQueue(Player sender) {
        return queue.remove(sender);
    }
}
