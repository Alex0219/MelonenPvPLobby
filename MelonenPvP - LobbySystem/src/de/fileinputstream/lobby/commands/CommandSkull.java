package de.fileinputstream.lobby.commands;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class CommandSkull implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("lobby.skull")) {
                if (args.length == 1) {
                    player.getInventory().addItem(getHead(args[0]));
                    player.sendMessage("§cSystem §7● §7Der Kopf liegt in deinem Inventar.");
                } else {
                    player.sendMessage("§cSystem §7● §7Verwende /skull <Spieler>");
                    return true;
                }
            } else {
                player.sendMessage("§cSystem §7● §cYou do not have permission to execute this command!");
                return true;
            }
        }
        return false;
    }

    public ItemStack getHead(String name) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(name);
        meta.setDisplayName("§7Kopf von §c " + name);
        skull.setItemMeta(meta);

        return skull;
    }
}
