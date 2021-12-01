package com.somemone.itemsorters;

import java.util.HashMap;

// TODO: Add imports

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

    private static Economy economy;

    private static ConfigSettings configs;

    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new ClickListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        getCommand("sorter").setExecutor(new SorterCommand());

        FileHandler fileHandler = new FileHandler();
        configs = fileHandler.getConfig();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
      return economy;
    }
}
