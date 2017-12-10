package de.fileinputstream.lobby.listeners;

import de.fileinputstream.lobby.Main;
import de.fileinputstream.lobby.manager.MuteManager;
import de.fileinputstream.lobby.rank.RankManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = "%2$s";


        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("mod".toLowerCase())) {
            e.setFormat("§cMod §7● §c " + p.getName() + "§7 » " + msg);
        }
        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("builder".toLowerCase())) {
            e.setFormat("§eBuilder §7● §e" + p.getName() + "§7 » " + msg);
        }
        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("spieler".toLowerCase())) {
            e.setFormat("§7" + p.getName() + "§7 » " + msg);
        }
        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("dev".toLowerCase())) {
            e.setFormat("§3Dev §7● §3" + p.getName() + "§7 » " + msg);
        }
        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("premiumplus".toLowerCase())) {
            e.setFormat("§6Premium+ §7● §6" + p.getName() + "§7 » " + msg);
        }
        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("admin".toLowerCase())) {
            e.setFormat("§4Admin §7● §4" + p.getName() + "§7 » " + msg);
        }
        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("sup".toLowerCase())) {
            e.setFormat("§2Sup §7● §2" + p.getName() + "§7 » " + msg);
        }
        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("youtuber".toLowerCase())) {
            e.setFormat("§5YouTuber §7● §5" + p.getName() + "§7 » " + msg);
        }
        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("premium".toLowerCase())) {
            e.setFormat("§6" + p.getName() + "§7 » " + msg);
        }
        if (RankManager.getRank(p.getUniqueId().toString()).equalsIgnoreCase("owner".toLowerCase())) {
            e.setFormat("§4Owner §7● §4" + p.getName() + "§7 » " + msg);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chatFormat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if ((Main.mysql.isConnected()) &&
                (MuteManager.isMuted(p.getUniqueId().toString()))) {
            long current = System.currentTimeMillis();
            long end = MuteManager.getEnd(p.getUniqueId().toString());
            String Dauer = MuteManager.getDauer(p.getUniqueId().toString());
            if ((end == -1L | Dauer.equals("§4PERMANENT"))) {
                e.setCancelled(true);
                p.sendMessage("§cSystem §7● §7Du wurdest §4PERMANENT §7aus dem Chat gebannt§8!");
                p.sendMessage("§cSystem §7● §eMutegrund§8: §7" + MuteManager.getReason(p.getUniqueId().toString()));
                return;
            }
            if (((current < end ? 1 : 0) | (end == current ? 1 : 0)) != 0) {
                e.setCancelled(true);
                String uuid = p.getUniqueId().toString();
                p.sendMessage("§cSystem §7● §7Du wurdest f§r §e" + MuteManager.getDauer(p.getUniqueId().toString()) + " §7aus dem Chat gebannt§8!");
                p.sendMessage("§cSystem §7● §aVerbleibende Zeit§8: §e" + MuteManager.getRemainingTime(uuid));
            } else {
                MuteManager.unmute(p.getUniqueId().toString());
                return;
            }
        }
    }
}



