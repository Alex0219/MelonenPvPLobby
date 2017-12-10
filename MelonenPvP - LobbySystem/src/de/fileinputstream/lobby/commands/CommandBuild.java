package de.fileinputstream.lobby.commands;

import de.fileinputstream.lobby.listeners.PlayerJoinListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBuild implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("lobby.build")) {
                if (PlayerJoinListener.build.contains(player)) {
                    PlayerJoinListener.build.remove(player);
                    player.sendMessage("§cSystem §7● Du kannst nun nichtmehr §cbauen");
                } else {
                    PlayerJoinListener.build.add(player);
                    player.sendMessage("§cSystem §7● Du kannst nun  §abauen");

                }
            } else {
                player.sendMessage("§cSystem §7● §cYou do not have permission to execute this command!");
                return true;
            }


        } else {
            return true;
        }
        return false;
    }
}
