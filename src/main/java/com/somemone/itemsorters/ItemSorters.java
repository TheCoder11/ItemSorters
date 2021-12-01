package com.somemone.itemsorters;

import java.util.HashMap;

import com.somemone.itemsorters.command.SorterCommand;
import com.somemone.itemsorters.inventory.PlayerState;
import com.somemone.itemsorters.inventory.SorterInventory;
import com.somemone.itemsorters.listener.ClickListener;
import com.somemone.itemsorters.listener.InventoryListener;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemSorters extends JavaPlugin {

    public static HashMap<Player, PlayerState> playerStates = new HashMap<>();

    public static HashMap<Player, SorterInventory> openInventories = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        getCommand("sorter").setExecutor(new SorterCommand());
    }
}
