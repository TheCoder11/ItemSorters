package com.somemone.itemsorters.listener;

import com.somemone.itemsorters.ItemSorters;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickListener implements Listener {

    @EventHandler
    public void onItemClick (PlayerInteractEvent event) {

        if (event.getAction().equals( Action.LEFT_CLICK_BLOCK )) {
            if (ItemSorters.playerStates.containsKey(event.getPlayer())) {

                ItemSorters.playerStates.get(event.getPlayer()).handle(event.getClickedBlock(), event.getPlayer());

            }
        }

    }

}