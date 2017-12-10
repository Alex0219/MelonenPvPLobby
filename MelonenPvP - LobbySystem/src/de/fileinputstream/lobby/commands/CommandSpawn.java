package de.fileinputstream.lobby.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.DARK_RED + "Die Konsole kann diesen Befehl nicht ausführen.");
            return true;
        }
        File file = new File("plugins//MelonLobbySystem//", "config.yml");

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        double x = cfg.getDouble("X");
        double y = cfg.getDouble("Y");
        double z = cfg.getDouble("Z");
        float yaw = (float)cfg.getDouble("Yaw");
        float pitch = (float)cfg.getDouble("Pitch");
        World world = Bukkit.getWorld(cfg.getString("World"));

        Location spawnLocation = new Location(world,x,y,z,yaw,pitch);

        Player player = (Player)sender;
        player.teleport(spawnLocation);

        return false;
    }
}
