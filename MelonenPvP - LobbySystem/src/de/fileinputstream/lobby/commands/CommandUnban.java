package de.fileinputstream.lobby.commands;

import de.fileinputstream.lobby.Main;
import de.fileinputstream.lobby.manager.BanManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandUnban implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            if (p.hasPermission("ban.unban")) {
                if (args.length == 1) {
                    String playername = args[0];
                    playername = playername.toLowerCase();
                    String Player = Bukkit.getOfflinePlayer(playername).getName();
                    if (Bukkit.getOfflinePlayer(Player).isBanned()) {
                        Bukkit.getOfflinePlayer(Player).setBanned(false);
                    }
                    if (Main.mysql.isConnected()) {
                        if (!BanManager.isBanned(getUUID(Player))) {
                            StringBuilder sb = new StringBuilder();

                            sender.sendMessage("§cSystem §7● §cDieser Spieler ist derzeit nicht gebannt§8!");
                            return true;
                        }
                        BanManager.unban(getUUID(Player));
                        StringBuilder sb2 = new StringBuilder();

                        sender.sendMessage(" §cSystem §7● §7Du hast den Spieler " + Player + "§7entbannt!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (all.hasPermission("ban.notify")) {
                                all.sendMessage("§cSystem §7● §7Der Spieler §e" + playername + " §7wurde von §e" + p.getName() + " §7vom Server entbannt.");
                            }
                        }
                        return true;
                    }
                    p.sendMessage("§cSystem §7● §4[ERROR] §cEs besteht keine Datenbankverbindung!");
                } else {
                    p.sendMessage("§cSystem §7● §c/unban <Spieler>");
                }
            } else {
                p.sendMessage("§cSystem §7● §cYou do not have permission to execute this command!");
            }
        } else if (((sender instanceof ConsoleCommandSender)) && (args.length == 1)) {
            String playername2 = args[0];
            playername2 = playername2.toLowerCase();
            String Player2 = Bukkit.getOfflinePlayer(playername2).getName();
            if (Bukkit.getOfflinePlayer(Player2).isBanned()) {
                Bukkit.getOfflinePlayer(Player2).setBanned(false);
            }
            if (Main.mysql.isConnected()) {
                if (!BanManager.isBanned(getUUID(Player2))) {
                    StringBuilder sb3 = new StringBuilder();

                    sender.sendMessage("§cSystem §7● §7Dieser Spieler ist derzeit nicht gebannt§8!");
                    return true;
                }
                BanManager.unban(getUUID(Player2));
                StringBuilder sb4 = new StringBuilder();

                sender.sendMessage("§cSystem §7● §7Der Spieler wurde entbannt.");

                return true;
            }
            sender.sendMessage("§cSystem §7● §4[ERROR] §cEs besteht keine Datenbankverbindung!");
        }
        return true;
    }

    private String getUUID(String Playername) {
        return Bukkit.getOfflinePlayer(Playername).getUniqueId().toString();
    }
}
