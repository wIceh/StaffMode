package me.t62k.staffmode.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtils {

    public ItemStack makeItem(Material material, int amount, int damage, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) damage);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeItem(Material material, int amount, String displayName) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeItem(Material material, int amount, int damage, String displayName) {
        ItemStack item = new ItemStack(material, amount, (short) damage);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        item.setItemMeta(meta);

        return item;
    }
}
