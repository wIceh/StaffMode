package me.t62k.staffmode.listeners;

import me.t62k.staffmode.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Listeners implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(Main.getStaffModeManager().isStaffMode(player))
            Main.getStaffModeManager().disableStaffMode(player);
    }

    @EventHandler
    public void onItemMove(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(Main.getStaffModeManager().isStaffMode(player)) {
            if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(Main.getStaffModeManager().isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(Main.getStaffModeManager().isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (Main.getStaffModeManager().isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(!Main.getStaffModeManager().isStaffMode(player)) return;

        event.setCancelled(true);

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if(event.getItem() != null) {
                if(event.getItem().getItemMeta().getDisplayName().contains("Vanish")) {
                    if(Main.getStaffModeManager().isVanished(player)) {
                        player.removeMetadata("vanish", Main.getInstance());
                        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            onlinePlayer.showPlayer(player);
                        }
                        ItemStack vanishOff = Main.getItemUtils().makeItem(Material.INK_SACK, 1, 8, "&c&lVanish OFF");
                        player.getInventory().setItem(8, vanishOff);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l» &bVanish disabilitata!"));
                    }else {
                        player.setMetadata("vanish", new FixedMetadataValue(Main.getInstance(), true));
                        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            if(onlinePlayer.hasPermission("staffmode.use")) continue;
                            onlinePlayer.hidePlayer(player);
                        }
                        ItemStack vanishOn = Main.getItemUtils().makeItem(Material.INK_SACK, 1, 1, "&a&lVanish ON");
                        player.getInventory().setItem(8, vanishOn);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l» &bVanish abilitata!"));
                    }
                }else if(event.getItem().getItemMeta().getDisplayName().contains("Random TP")) {
                    List<Player> players = new ArrayList<>();

                    for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if(onlinePlayer.getUniqueId() == player.getUniqueId()) continue;
                        players.add(onlinePlayer);
                    }

                    if(players.isEmpty()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l» &cNon c'è nessun giocatore online!"));
                        return;
                    }

                    int randomIndex = new Random().nextInt(players.size());
                    Player randomPlayer = players.get(randomIndex);

                    player.teleport(randomPlayer);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractPlayer(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(event.getRightClicked() instanceof Player) {
            Player target = (Player) event.getRightClicked();
            if(Main.getStaffModeManager().isStaffMode(player)) {
                event.setCancelled(true);
                if(player.getInventory().getItemInHand().getType() == Material.BOOK) {
                    player.openInventory(target.getInventory());
                }
            }
        }
    }
}
