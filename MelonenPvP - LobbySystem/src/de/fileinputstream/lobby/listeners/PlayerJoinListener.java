package de.fileinputstream.lobby.listeners;

import de.fileinputstream.lobby.fetcher.UUIDFetcher;
import de.fileinputstream.lobby.rank.RankManager;
import de.fileinputstream.lobby.rank.score.NameTags;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.lang.reflect.Field;
import java.util.ArrayList;

public class PlayerJoinListener implements Listener {

    public static ArrayList<Player> build = new ArrayList<Player>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (int i = 0; i < 150; i++) {
            player.sendMessage("");
        }
        event.setJoinMessage(null);

        if (player.getWorld().getName().equalsIgnoreCase("world")) {
            player.getInventory().clear();
            ItemStack item = new ItemStack(Material.COMPASS);
            ItemMeta meta1 = item.getItemMeta();
            meta1.setDisplayName("§7● §cNavigator");
            item.setItemMeta(meta1);

            ItemStack Spieler = new ItemStack(Material.BLAZE_ROD);
            ItemMeta SpielerMeta = Spieler.getItemMeta();
            SpielerMeta.setDisplayName("§7● §6Spieler Verstecken §7● §aSichtbar");
            Spieler.setItemMeta(SpielerMeta);

            player.getInventory().setItem(0, item);
            player.getInventory().setItem(1, Spieler);


        }
        sendTablistHeaderAndFooter(player, "§aMelonenPvP", "§7Unser Teamspeak: §cts.melonenpvp.de");
        String uuid = UUIDFetcher.getUUID(player.getName()).toString();
        if (!RankManager.playerExists(uuid)) {
            RankManager.createPlayer(uuid);
            NameTags.addToTeam(player);
            NameTags.updateTeams();

        } else {

            NameTags.addToTeam(player);
            NameTags.updateTeams();
        }
    }

    public void sendTablistHeaderAndFooter(Player p, String header, String footer) {
        if (header == null) header = "";
        if (footer == null) footer = "";

        IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}");
        IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}");

        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabHeader);
        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFooter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(headerPacket);
        }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                event.setCancelled(true);
            }
            if (event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
                event.setCancelled(true);
            }
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE)) {
                event.setCancelled(true);
            }
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            event.setCancelled(true);
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            event.setFoodLevel(20);
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onWorldLeave(PlayerChangedWorldEvent event) {
        if (event.getFrom().getName().equalsIgnoreCase("world")) {
            if (event.getPlayer().getInventory().contains(Material.COMPASS)) {
                event.getPlayer().getInventory().remove(Material.COMPASS);
            }
            if (event.getPlayer().getInventory().contains(Material.BLAZE_ROD)) {
                event.getPlayer().getInventory().remove(Material.BLAZE_ROD);
            }


        }


    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            if (build.contains(event.getPlayer())) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            if (build.contains(event.getPlayer())) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onInvMove(InventoryClickEvent event) {
        try {
            if (event.getWhoClicked().getWorld().getName().equalsIgnoreCase("world")) {
                Player player = (Player) event.getClickedInventory();
                event.setCancelled(true);

            }

        } catch (Exception exception) {

        }
    }

    @EventHandler
    public void onMove(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getWhoClicked().getWorld().getName().equalsIgnoreCase("world")) {
            if (build.contains(player)) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onWorldEnter(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            player.getInventory().clear();
            ItemStack item = new ItemStack(Material.COMPASS);
            ItemMeta meta1 = item.getItemMeta();
            meta1.setDisplayName("§7● §cNavigator");
            item.setItemMeta(meta1);

            ItemStack Spieler = new ItemStack(Material.BLAZE_ROD);
            ItemMeta SpielerMeta = Spieler.getItemMeta();
            SpielerMeta.setDisplayName("§7● §6Spieler Verstecken §7● §aSichtbar");
            Spieler.setItemMeta(SpielerMeta);

            player.getInventory().setItem(0, item);
            player.getInventory().setItem(1, Spieler);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (event.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        if(event.getPlayer().getWorld().getName().equalsIgnoreCase("Citybuild-1")) {
            event.getPlayer().setGameMode(GameMode.CREATIVE);
        }
        if(event.getFrom().getName().equalsIgnoreCase("Citybuild-1")) {
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
        }
    }




}


