package me.t62k.staffmode.commands;

import me.t62k.staffmode.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("staffmode.use")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNon hai il permesso!"));
            return true;
        }

        if(Main.getStaffModeManager().isStaffMode(player)) {
            Main.getStaffModeManager().disableStaffMode(player);
        }else {
            Main.getStaffModeManager().enableStaffMode(player);
        }

        return true;
    }
}
