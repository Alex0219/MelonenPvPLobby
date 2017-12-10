package de.fileinputstream.lobby.listeners;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

public class PlayerInteractListener implements Listener {

    public static ArrayList<Player> hide = new ArrayList();
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        try {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.getItemInHand().getType().equals(Material.COMPASS) && player.getWorld().getName().equalsIgnoreCase("world")) {
                    Inventory inv = Bukkit.createInventory(null, 9 * 3, "Wähle einen Spielmodus");

                    ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
                    inv.setItem(0, item);
                    inv.setItem(1, item);
                    inv.setItem(2, item);
                    inv.setItem(3, item);
                    inv.setItem(4, build(Material.DIAMOND_SWORD, "§6Survivallife"));
                    inv.setItem(5, item);
                    inv.setItem(6, item);
                    inv.setItem(7, item);
                    inv.setItem(8, item);
                    inv.setItem(9, item);
                    inv.setItem(10, item);
                    inv.setItem(11, build(Material.BARRIER,"§cKommt bald"));
                    inv.setItem(12, build(Material.IRON_AXE, "§6Citybuild"));
                    inv.setItem(13, build(Material.COOKIE, "§6Spawn"));
                    inv.setItem(14, build(Material.PAPER, "§6Handbuch"));
                    inv.setItem(15, getHead(player.getName()));
                    inv.setItem(16, item);
                    inv.setItem(17, item);
                    inv.setItem(18, item);
                    inv.setItem(19, item);
                    inv.setItem(20, item);
                    inv.setItem(21, item);
                    inv.setItem(22, build(Material.IRON_PICKAXE, "§6Freebuild"));
                    inv.setItem(23, item);
                    inv.setItem(24, item);
                    inv.setItem(25, item);
                    inv.setItem(26, item);


                    player.openInventory(inv);
                }
            }

        } catch (Exception exception) {

        }

        try
        {

                if (event.getItem().getType() == Material.BLAZE_ROD)
                {
                    ItemStack n1 = new ItemStack(Material.STICK);
                    ItemMeta nm1 = n1.getItemMeta();
                    nm1.setDisplayName("§7● §6Spieler Anzeigen §7● §cunsichtbar");
                    n1.setItemMeta(nm1);

                    hide.add(player);

                    player.getInventory().setItem(1, n1);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        player.hidePlayer(all);
                    }
                    player.sendMessage("§cSystem §7● Alle Spieler sind nun §cunsichtbar!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                }
                else if (event.getItem().getType() == Material.STICK)
                {
                    ItemStack n1 = new ItemStack(Material.BLAZE_ROD);
                    ItemMeta nm1 = n1.getItemMeta();
                    nm1.setDisplayName("§7● §6Spieler Verstecken §7● §asichtbar");
                    n1.setItemMeta(nm1);

                    hide.remove(player);

                    player.getInventory().setItem(1, n1);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        player.showPlayer(all);
                    }
                    player.sendMessage("§cSystem §7● Die Spieler sind nun §asichtbar");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                }

        }
        catch (Exception localException) {}
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            if (event.getInventory().getName().equalsIgnoreCase("Wähle einen Spielmodus")) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                if (event.getCurrentItem().getType().equals(Material.COOKIE)) {
                    player.chat("/warp " + "Spawn");
                }
                if (event.getCurrentItem().getType().equals(Material.IRON_PICKAXE)) {
                    player.chat("/warp " + "Freebuild");
                }
                if (event.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)) {
                    player.chat("/warp " + "Survivallife");
                }
                if (event.getCurrentItem().getType().equals(Material.IRON_AXE)) {
                    player.chat("/warp " + "Citybuild");
                }
                if (event.getCurrentItem().getType().equals(Material.PAPER)) {
                    player.chat("/warp " + "Handbuch");
                }
                if (event.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                    player.chat("/warp " + "Community");
                }
            }
        } catch(Exception exception) {

        }


    }

    public ItemStack build(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getHead(String name) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(name);
        meta.setDisplayName("§6Community");
        skull.setItemMeta(meta);

        return skull;
    }
}
