package de.fileinputstream.lobby.commands;

import com.comphenix.protocol.PacketType;
import de.fileinputstream.lobby.Main;
import de.fileinputstream.lobby.manager.BanManager;
import de.fileinputstream.lobby.manager.HistoryManager;
import de.fileinputstream.lobby.rank.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CommandBan implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            if (p.hasPermission("ban.ban")) {
                if (args.length >= 2) {
                    String playername = args[0];
                    if (Main.mysql.isConnected()) {
                        playername = playername.toLowerCase();

                        String Player = Bukkit.getOfflinePlayer(playername).getName();
                        if (BanManager.isBanned(Bukkit.getOfflinePlayer(Player).getUniqueId().toString())) {
                            sender.sendMessage("§cSystem §7● §7Dieser Spieler ist bereits §cgebannt");
                            return true;
                        }
                        String reason = "";
                        for (int i = 1; i < args.length; i++) {
                            reason = reason + args[i] + " ";
                        }
                        if (PermissionsEx.getUser(Player).has("ban.noban") && ((p.hasPermission("ban.banall")))) {
                            sender.sendMessage("§cSystem §7● §7Du darfst kein §cTeammitglied §7bannen!");
                            return true;
                        }
                        BanManager.ban(Bukkit.getOfflinePlayer(Player).getUniqueId().toString(), Player, reason, -1L, "p", p.getName(), BanManager.createbanID("abcdefghijklmnopqrstuvwxyz", 12));
                        HistoryManager.addHistoryEntry("Ban", Bukkit.getOfflinePlayer(Player).getUniqueId().toString(), reason, p.getName(), "permanent", Player);

                        sender.sendMessage(" §cSystem §7● §7Du hast den Spieler §c" + Player + " §7gebannt!");
                        return true;
                    }
                    p.sendMessage("§cSystem §7● §4[ERROR] §cEs besteht keine Datenbankverbindung");
                }
                sender.sendMessage("§cSystem §7● §c/ban <Spieler> <Grund>");
                return true;
            }
            p.sendMessage("§cSystem §7● §cYou do not have permission to execute this command!");

            return true;
        }
        if ((sender instanceof ConsoleCommandSender)) {
            if (args.length >= 2) {
                String playername2 = args[0];
                if (Main.mysql.isConnected()) {
                    playername2 = playername2.toLowerCase();
                    String Player2 = Bukkit.getOfflinePlayer(playername2).getName();
                    if (BanManager.isBanned(Bukkit.getOfflinePlayer(Player2).getUniqueId().toString())) {
                        sender.sendMessage("§cSystem §7● §7Dieser Spieler ist bereits §cgebannt");
                        return true;
                    }
                    String reason2 = "";
                    for (int j = 1; j < args.length; j++) {
                        reason2 = reason2 + args[j] + " ";
                    }
                    BanManager.ban(Bukkit.getOfflinePlayer(Player2).getUniqueId().toString(), playername2, reason2, -1L, "p", "CONSOLE", BanManager.createbanID("abcdefghijklmnopqrstuvwxyz", 7));
                    HistoryManager.addHistoryEntry("Ban", Bukkit.getOfflinePlayer(Player2).getUniqueId().toString(), reason2, "CONSOLE", "permanent", Player2);

                    sender.sendMessage(" §cSystem §7● §7Du hast den Spieler §c" + Player2 + " §7gebannt!");
                    return true;
                }
                if (Bukkit.getOfflinePlayer(playername2).isBanned()) {
                    Bukkit.getOfflinePlayer(playername2).setBanned(false);
                    sender.sendMessage(" §cSystem §7● §7Du hast den Spieler " + playername2 + " §7gebannt!");
                } else {
                    sender.sendMessage("§cSystem §7● §7Dieser Spieler ist nicht §cgebannt.");
                }
            }
            sender.sendMessage("§cSystem §7● §c/ban <Spieler> <Grund>");
            return true;
        }
        return true;
    }
}
