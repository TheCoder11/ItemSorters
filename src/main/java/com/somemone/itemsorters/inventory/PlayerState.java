package com.somemone.itemsorters.inventory;

import java.util.Optional;

import com.somemone.itemsorters.file.FileHandler;
import com.somemone.itemsorters.sorter.Sorter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlockInventoryHolder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public enum PlayerState {

    PLAYER_ADDING {
        @Override
        public void handle(Block block, Player player) {
            if (!(block instanceof Container)) return;
            if (block.getType() != Material.CHEST && block.getType() != Material.BARREL) return;
            if (!player.hasPermission( "itemsorter.add" )) return;

            Container container = (Container) block;
            FileHandler handler = new FileHandler();
            Optional<Sorter> sorter = handler.getSorterFromPlayer(player.getUniqueId());

            // TODO: Deduct money from player account based on config
            
            if (sorter.isPresent()) {
                Sorter bigSorter = sorter.get();
                if (bigSorter.getContainers().contains(container)) {
                    player.sendMessage(Component.text("Container already in sorter!").color(NamedTextColor.RED));
                } else {
                    bigSorter.addContainer(container);
                    handler.saveSorter(bigSorter);
                    player.sendMessage(Component.text("Added container").color(NamedTextColor.GREEN));
                }
            }
        }
    },
    PLAYER_REMOVING {
        @Override
        public void handle(Block block, Player player) {
            if (!(block instanceof Container)) return;
            if (block.getType() != Material.CHEST && block.getType() != Material.BARREL) return;
            if (!player.hasPermission( "itemsorter.remove" )) return;
            
            Container container = (Container) block;
            FileHandler handler = new FileHandler();
            Optional<Sorter> sorter = handler.getSorterFromPlayer(player.getUniqueId());
            
            if (sorter.isPresent()) {
                Sorter bigSorter = sorter.get();
                if (bigSorter.getContainers().contains(container)) {
                    bigSorter.removeContainer(container);
                    handler.saveSorter(bigSorter);
                    player.sendMessage(Component.text("Removed container").color(NamedTextColor.GREEN));
                } else {
                    player.sendMessage(Component.text("Container not in sorter!").color(NamedTextColor.RED));
                }
            }
        }
    },
    PLAYER_CREATING {
        @Override
        public void handle(Block block, Player player) {
            if (!(block instanceof Container)) return;
            if (block.getType() != Material.CHEST && block.getType() != Material.BARREL) return;
            if (!player.hasPermission( "itemsorter.create" )) return;

            // TODO: Deduct money from player account based on config

            Container holder = (Container) block;
            Sorter sorter = new Sorter(player.getUniqueId(), holder);
            FileHandler handler = new FileHandler();
            handler.saveSorter(sorter);
        }
    };

    public abstract void handle(Block block, Player player);

}
