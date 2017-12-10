package de.fileinputstream.lobby.commands;

import de.fileinputstream.lobby.manager.BanManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCheckBanID implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if ((sender instanceof Player))
        {
            Player p = (Player)sender;
            if (args.length == 1)
            {
                if (BanManager.getReasonFromBanID(args[0]) != null) {
                    p.sendMessage("§cSystem §7● §aDer Grund dieses Banns lautet: §c" + BanManager.getReasonFromBanID(args[0]));
                } else {
                    p.sendMessage("§cSystem §7● §7Die eingegebene Ban-ID ist nicht im System vorhanden.");
                }
            }
            else
            {
                p.sendMessage("§cSystem §7● §c/checkbanid <BanID>");
                return true;
            }
        }
        else
        {
            return true;
        }
        return false;
    }
}
