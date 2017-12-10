package de.fileinputstream.lobby.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("lobby.cc")) {
            for(int i = 0; i < 320; i++) {
                Bukkit.broadcastMessage("");

            }
            Bukkit.broadcastMessage("§7● Der Chat wurde von §c" + commandSender.getName() + " §7geleert");
        } else {
            commandSender.sendMessage("§cSystem §7● §cYou do not have permission to execute this command!");
            return true;
        }
        return false;
    }
}
