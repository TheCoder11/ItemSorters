package com.somemone.itemsorters.listener;

import com.somemone.itemsorters.ItemSorters;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import net.kyori.adventure.sound.SoundStop;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInvClick (InventoryClickEvent event) {

        if (ItemSorters.openInventories.containsKey(event.getWhoClicked())) {
            ItemSorters.openInventories.get(event.getWhoClicked()).handle(event);
        }

    }

    @EventHandler
    public void onInvClose (InventoryCloseEvent event) {
        if (ItemSorters.openInventories.containsKey(event.getPlayer())) {
            ItemSorters.openInventories.remove(event.getPlayer());
        }
    }

}