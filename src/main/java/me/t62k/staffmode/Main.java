package me.t62k.staffmode;

import me.t62k.staffmode.commands.StaffModeCommand;
import me.t62k.staffmode.commands.StaffVanishCommand;
import me.t62k.staffmode.listeners.Listeners;
import me.t62k.staffmode.manager.StaffModeManager;
import me.t62k.staffmode.task.Task;
import me.t62k.staffmode.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static StaffModeManager staffModeManager;
    private static ItemUtils itemUtils;

    @Override
    public void onEnable() {
        instance = this;
        staffModeManager = new StaffModeManager();
        itemUtils = new ItemUtils();

        registerCommands();
        getServer().getPluginManager().registerEvents(new Listeners(), this);

        Bukkit.getScheduler().runTaskTimer(this, new Task(), 0L, 1L);
    }

    private void registerCommands() {
        getCommand("staffmode").setExecutor(new StaffModeCommand());
        getCommand("staffvanish").setExecutor(new StaffVanishCommand());
    }

    public static Main getInstance() {
        return instance;
    }

    public static StaffModeManager getStaffModeManager() {
        return staffModeManager;
    }

    public static ItemUtils getItemUtils() {
        return itemUtils;
    }
}
