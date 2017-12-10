package de.fileinputstream.lobby.listeners;

import de.fileinputstream.lobby.Main;
import de.fileinputstream.lobby.manager.BanManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        if ((Main.mysql.isConnected()) && (BanManager.isBanned(p.getUniqueId().toString()))) {
            long current = System.currentTimeMillis();
            long end = BanManager.getEnd(p.getUniqueId().toString());
            String Dauer = BanManager.getDauer(p.getUniqueId().toString());
            if ((end > current) || (end == -1L)) {
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, "§7Du wurdest §cgebannt. \n\n§aGrund : " +

                        BanManager.getReason(e.getPlayer().getUniqueId().toString()) +

                        "\n§bBanID §e" + BanManager.getBanID(p.getUniqueId().toString()) +

                        "\nDu kannst im Teamspeak einen Entbannungsantrag stellen :");
            } else {
                BanManager.unban(p.getUniqueId().toString());
                e.allow();
            }
            if ((Main.mysql.isConnected()) &&
                    (BanManager.isBanned(p.getUniqueId().toString()))) {
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, "§7Du wurdest §cgebannt.. \n\n§aGrund : " +

                        BanManager.getReason(e.getPlayer().getUniqueId().toString()) +

                        "\n§eVerbleibende Zeit : " + BanManager.getRemainingTime(e.getPlayer().getUniqueId().toString()) +

                        "\n§bBanID §e" + BanManager.getBanID(e.getPlayer().getUniqueId().toString()));
            } else {
                e.setResult(PlayerLoginEvent.Result.ALLOWED);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if ((Main.mysql.isConnected()) &&
                (BanManager.isBanned(e.getPlayer().getUniqueId().toString()))) {
            BanManager.unban(e.getPlayer().getUniqueId().toString());
        }
    }
}
