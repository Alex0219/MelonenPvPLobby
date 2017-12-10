package de.fileinputstream.lobby.commands;

import de.fileinputstream.lobby.manager.HistoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;

public class CommandHistory implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            if (p.hasPermission("ban.history")) {
                if (args.length == 1) {
                    String playername = args[0].toLowerCase();
                    String Player = Bukkit.getOfflinePlayer(playername).getName();
                    OfflinePlayer op = Bukkit.getOfflinePlayer(Player);
                    if (HistoryManager.isRegistered(op.getUniqueId().toString())) {
                        p.sendMessage("§cSystem §7● §eHistory von §8" + Player);
                        List < String > history = HistoryManager.getEntrys(op.getUniqueId().toString());
                        for (int i = 0; i < history.size(); i++) {
                            String[] historyEntry = ((String) history.get(i)).split("#Cut#");
                            p.sendMessage("§cSystem §7● §3Typ: §c" + historyEntry[0] + " §3Erstellt: §a" + historyEntry[4] + " §3Grund: §c" + historyEntry[2] + " §3Von: §e" + historyEntry[1] + " §3Zeit: §a" + historyEntry[3]);
                        }
                    } else {
                        p.sendMessage("§cSystem §7● §cHistory ist leer.");
                    }
                } else if (args.length == 2) {
                    if ((args[0].equalsIgnoreCase("clear")) && (p.hasPermission("ban.history"))) {
                        String playername = args[1].toLowerCase();
                        String Player = Bukkit.getOfflinePlayer(playername).getName();
                        OfflinePlayer op = Bukkit.getOfflinePlayer(Player);
                        if (HistoryManager.isRegistered(op.getUniqueId().toString())) {
                            HistoryManager.delHistory(op.getUniqueId().toString());
                            p.sendMessage("§cSystem §7● §aHistory wurde geleert.");
                        } else {
                            p.sendMessage("§cSystem §7● §cHistory ist leer.");
                        }
                    }
                } else {
                    p.sendMessage("§cSystem §7● §c/history <Player>");
                }
            } else {
                p.sendMessage("§cSystem §7● §cYou do not have permission to execute this command!");
            }
        } else if ((sender instanceof ConsoleCommandSender)) {
            if (args.length == 1) {
                String playername2 = args[0].toLowerCase();
                String Player2 = Bukkit.getOfflinePlayer(playername2).getName();
                OfflinePlayer op2 = Bukkit.getOfflinePlayer(Player2);
                if (HistoryManager.isRegistered(op2.getUniqueId().toString())) {
                    sender.sendMessage("§cSystem §7● §eHistory von §8" + Player2);
                    List < String > history2 = HistoryManager.getEntrys(op2.getUniqueId().toString());
                    for (int j = 0; j < history2.size(); j++) {
                        String[] historyEntry2 = ((String) history2.get(j)).split("#Cut#");
                        sender.sendMessage("§cSystem §7● §3Typ: §c" + historyEntry2[0] + " §3Erstellt: §a" + historyEntry2[4] + " §3Grund: §c" + historyEntry2[2] + " §3Von: §e" + historyEntry2[1] + " §3Zeit: §a" + historyEntry2[3]);
                    }
                } else {
                    sender.sendMessage("§cSystem §7● §cHistory ist leer");
                }
            } else if ((args.length == 2) && (args[0].equalsIgnoreCase("clear"))) {
                String playername2 = args[1].toLowerCase();
                String Player2 = Bukkit.getOfflinePlayer(playername2).getName();
                OfflinePlayer op2 = Bukkit.getOfflinePlayer(Player2);
                if (HistoryManager.isRegistered(op2.getUniqueId().toString())) {
                    HistoryManager.delHistory(op2.getUniqueId().toString());
                    sender.sendMessage("§cSystem §7● §aDie History wurde geleert.");
                }
            }
        }
        return true;
    }
}