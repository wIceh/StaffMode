package me.t62k.staffmode.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Task implements Runnable {
    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.hasMetadata("vanish")) {
                // todo: Action bar "Sei in vanish!"
            }
        }
    }
}
