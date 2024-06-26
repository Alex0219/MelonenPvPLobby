package de.fileinputstream.lobby.commands;

import de.fileinputstream.lobby.fetcher.UUIDFetcher;
import de.fileinputstream.lobby.rank.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandRank implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (sender instanceof Player) {

            Player p = (Player) sender;
            String uuid = UUIDFetcher.getUUID(p.getName()).toString();
            if (RankManager.getRank(uuid).equalsIgnoreCase("Admin".toLowerCase()) || RankManager.getRank(uuid).equalsIgnoreCase("Owner".toLowerCase()) || RankManager.getRank(uuid).equalsIgnoreCase("Dev".toLowerCase()) ||  sender instanceof ConsoleCommandSender || p.isOp()) {

                if (args.length == 2) {

                    if (args[1].equalsIgnoreCase("Admin".toLowerCase()) || args[1].equalsIgnoreCase("Dev".toLowerCase()) || args[1].equalsIgnoreCase("Mod".toLowerCase()) || args[1].equalsIgnoreCase("Sup".toLowerCase()) || args[1].equalsIgnoreCase("Builder".toLowerCase()) || args[1].equalsIgnoreCase("YouTuber") || args[1].equalsIgnoreCase("Premium".toLowerCase()) || args[1].equalsIgnoreCase("Spieler".toLowerCase()) || args[1].equalsIgnoreCase("Owner".toLowerCase()) || args[1].equalsIgnoreCase("Premiumplus".toLowerCase())) {
                        String rank = args[1];
                        RankManager.setRank(UUIDFetcher.getUUID(args[0]).toString(), rank);
                        p.sendMessage("§7------------------------------------------------------");
                        p.sendMessage("§cSystem §7● §aRank wurde zu §c" + args[1] + " gesetzt.");
                        p.sendMessage("§7------------------------------------------------------");
                        if (Bukkit.getPlayer(args[0]) != null) {

                                             Bukkit.getPlayer(args[0]).kickPlayer("§7---------------------------------------------------------------------------\7" +
                                                                                      "§cSystem §7● Dein Rank wurde zu §c" + args[1].toUpperCase() + " §7gesetzt.\n" +
                                                                                    "§7---------------------------------------------------------------------------");
                        }

                    } else {
                        p.sendMessage("§cSystem §7● §cDieser Rang existiert nicht.");
                        return true;
                    }
                } else {
                    p.sendMessage("§cSystem §7● Verwende §7/rank <Spieler> <Rank>");
                }
            } else {
            }
            return false;
        }
        return false;
    }
}
