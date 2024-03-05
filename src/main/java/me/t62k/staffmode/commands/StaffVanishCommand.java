package me.t62k.staffmode.commands;

import me.t62k.staffmode.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffVanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("staffvanish.use")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNon hai il permesso!"));
            return true;
        }

        Main.getStaffModeManager().toggleVanish(player);

        return true;
    }
}
