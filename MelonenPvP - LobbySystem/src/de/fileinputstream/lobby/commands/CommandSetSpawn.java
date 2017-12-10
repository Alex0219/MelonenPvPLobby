package de.fileinputstream.lobby.commands;

import de.fileinputstream.lobby.fetcher.UUIDFetcher;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CommandSetSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.DARK_RED + "Die Konsole kann diesen Befehl nicht ausführen.");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("lobby.setspawn")) {
            File file = new File("plugins//MelonLobbySystem//", "config.yml");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            cfg.set("X", player.getLocation().getX());
            cfg.set("Y", player.getLocation().getY());
            cfg.set("Z", player.getLocation().getZ());
            cfg.set("Yaw", player.getLocation().getYaw());
            cfg.set("Pitch", player.getLocation().getPitch());
            cfg.set("World", player.getLocation().getWorld().getName());

            player.sendMessage("§cWarp §7● §7Der §aSpawn §7wurde §aerfolgreich §7gesetzt.");
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
        }

        return false;
    }

}
