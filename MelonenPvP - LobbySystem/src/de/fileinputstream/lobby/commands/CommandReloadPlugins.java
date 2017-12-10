package de.fileinputstream.lobby.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

public class CommandReloadPlugins implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender || sender.hasPermission("lobby.plugins")) {
            for (Plugin plugins : Bukkit.getPluginManager().getPlugins()) {
                if (Bukkit.getPluginManager().isPluginEnabled(plugins.getName())) {
                    Bukkit.getPluginManager().disablePlugin(plugins);
                    sender.sendMessage(ChatColor.RED + "Lade alle Plugins neu....");

                    Bukkit.getPluginManager().enablePlugin(plugins);

                    sender.sendMessage(ChatColor.GREEN + "Alle Plugins wurden neugeladen.");
                }

            }

        }

        return false;
    }
}
