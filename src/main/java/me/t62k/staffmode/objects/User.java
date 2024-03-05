package me.t62k.staffmode.objects;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class User {

    private ItemStack[] items;

    private ItemStack[] armor;

    private Collection<PotionEffect> effects;

    private GameMode gameMode;

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public void setEffects(Collection<PotionEffect> effects) {
        this.effects = effects;
    }

    public Collection<PotionEffect> getEffects() {
        return effects;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}
