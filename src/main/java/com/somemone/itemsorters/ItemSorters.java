package com.somemone.itemsorters;

import java.util.HashMap;

import com.somemone.itemsorters.inventory.PlayerState;
import com.somemone.itemsorters.inventory.SorterInventory;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemSorters extends JavaPlugin {

    public static HashMap<Player, PlayerState> playerStates = new HashMap<>();

    public static HashMap<Player, SorterInventory> openInventories = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
