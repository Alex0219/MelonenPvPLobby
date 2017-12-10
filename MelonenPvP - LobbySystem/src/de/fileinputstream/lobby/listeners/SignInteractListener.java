package de.fileinputstream.lobby.listeners;

import net.minecraft.server.v1_8_R3.BlockWallSign;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;

public class SignInteractListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if(event.getLine(0).equalsIgnoreCase("[Warp]")) {
            String warp = event.getLine(1);

            Player player = event.getPlayer();
            if(player.hasPermission("lobby.warp")) {
                File file = new File("plugins//MelonLobbySystem//Warps//" + event.getLine(1));

                if (file.exists()) {
                    event.setCancelled(false);
                    event.setLine(0,"§2[Warp]");
                } else {
                    player.sendMessage("§cWarp §7● §4Dieser Warp existiert nichts!");
                    event.setCancelled(true);
                    event.getBlock().breakNaturally();

                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getClickedBlock().getState() instanceof Sign ) {


                BlockState stateBlock = event.getClickedBlock().getState();
                Sign sign = (Sign)stateBlock;
                if(sign.getLine(0).contains("[Warp]"))
                 event.getPlayer().chat("/warp " + sign.getLine(1));
            }

            //world
        }

    }

}
