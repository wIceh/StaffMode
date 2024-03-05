package me.t62k.staffmode.manager;

import com.lunarclient.bukkitapi.LunarClientAPI;
import me.t62k.staffmode.Main;
import me.t62k.staffmode.objects.User;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaffModeManager {

    private HashMap<UUID, User> inventories = new HashMap<>();

    public boolean isStaffMode(Player player) {
        return player.hasMetadata("staffmode");
    }

    public void enableStaffMode(Player player) {
        User user = new User();
        user.setItems(player.getInventory().getContents());
        user.setArmor(player.getInventory().getArmorContents());
        user.setEffects(player.getActivePotionEffects());
        user.setGameMode(player.getGameMode());
        inventories.put(player.getUniqueId(), user);
        player.getInventory().clear();
        setupInventory(player);
        player.setMetadata("staffmode", new FixedMetadataValue(Main.getInstance(), true));
        player.setAllowFlight(true);
        player.setGameMode(GameMode.CREATIVE);
        net.luckperms.api.model.user.User luckpermsUser = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        String prefix = luckpermsUser.getCachedData().getMetaData().getPrefix() != null ? luckpermsUser.getCachedData().getMetaData().getPrefix() : "&2";
        List<String> nametag = new ArrayList<>();
        nametag.add(ChatColor.translateAlternateColorCodes('&', "&7[Staff Mode]"));
        nametag.add(ChatColor.translateAlternateColorCodes('&', getColor(prefix) + player.getName()));
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            LunarClientAPI.getInstance().overrideNametag(player, nametag, onlinePlayer);
        }
        player.sendMessage(ChatColor.GREEN + "Sei entrato in staff mode!");

        String vanish = player.hasMetadata("vanish") ? "vanish on" : "vanish off";

        for(Player staffer : Bukkit.getOnlinePlayers()) {
            if(!staffer.hasPermission("staffmode.use")) continue;
            staffer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[Staff] &b" + player.getName() + " &7è entrato in staff mode! (" + vanish + ")"));
        }
    }

    private String getColor(String string) {
        String regex = "(?<!&)(?:&&)*&[0-9a-fk-or]+";

        Matcher matcher = Pattern.compile(regex).matcher(string);

        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            builder.append(matcher.group());
        }

        return builder.toString();
    }

    private void setupInventory(Player player) {
        ItemStack vanishOn = Main.getItemUtils().makeItem(Material.INK_SACK, 1, 1, "&a&lVanish ON");
        ItemStack vanishOff = Main.getItemUtils().makeItem(Material.INK_SACK, 1, 8, "&c&lVanish OFF");

        ItemStack invsee = Main.getItemUtils().makeItem(Material.BOOK, 1, "&6&lInvsee");
        ItemStack randomTp = Main.getItemUtils().makeItem(Material.COMPASS, 1, "&e&lRandom TP");

        ItemStack item = player.hasMetadata("vanish") ? vanishOn : vanishOff;

        player.getInventory().setItem(0, randomTp);
        player.getInventory().setItem(1, invsee);
        player.getInventory().setItem(8, item);
    }

    public boolean isVanished(Player player) {
        return player.hasMetadata("vanish");
    }

    public void toggleVanish(Player player) {
        if(isVanished(player)) {
            player.removeMetadata("vanish", Main.getInstance());
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(player);
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l» &bVanish disabilitata!"));
        }else {
            player.setMetadata("vanish", new FixedMetadataValue(Main.getInstance(), true));
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if(onlinePlayer.hasPermission("staffmode.use")) continue;
                onlinePlayer.hidePlayer(player);
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l» &bVanish abilitata!"));
        }
    }

    public void disableStaffMode(Player player) {
        User user = inventories.get(player.getUniqueId());
        player.getInventory().setContents(user.getItems());
        player.getInventory().setArmorContents(user.getArmor());
        player.addPotionEffects(user.getEffects());
        player.setGameMode(user.getGameMode());
        inventories.remove(player.getUniqueId());
        player.removeMetadata("staffmode", Main.getInstance());
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            LunarClientAPI.getInstance().resetNametag(player, onlinePlayer);
        }
        player.sendMessage(ChatColor.RED + "Sei uscito dalla staff mode.");

        String vanish = player.hasMetadata("vanish") ? "vanish on" : "vanish off";

        for(Player staffer : Bukkit.getOnlinePlayers()) {
            if(!staffer.hasPermission("staffmode.use")) continue;
            staffer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[Staff] &b" + player.getName() + " &7è uscito dalla staff mode. (" + vanish + ")"));
        }
    }
}
