package de.fileinputstream.lobby.protocol;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AntiSpy implements Listener {

    @EventHandler
    public void onPerfomCommand(PlayerCommandPreprocessEvent e)
    {
        String msg = e.getMessage();
        if ((msg.equalsIgnoreCase("/plugins")) || (msg.equalsIgnoreCase("/pl")) || (msg.startsWith("/bukkit:plugins")) || (msg.startsWith("/bukkit:pl")) || ((msg.startsWith("/bukkit:ver") | msg.startsWith("/bukkit:version"))) || (msg.startsWith("/ver")) || (msg.startsWith("/version")) || (msg.startsWith("/?")) || (msg.equalsIgnoreCase("/help")) || (msg.startsWith("/bukkit:?")) || (msg.equalsIgnoreCase("/bukkit:?")) || (msg.equalsIgnoreCase("/bukkit:help")) || (msg.equalsIgnoreCase("/icanhasbukkit"))|| (msg.equalsIgnoreCase("/me")) || (msg.startsWith("/minecraft:me"))) {
            if (!e.getPlayer().hasPermission("lobby.bypass"))
            {

                e.setCancelled(true);
            }
            else {}
        }
    }
}
