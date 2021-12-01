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

            
            if (sorter.isPresent()) {
                Sorter bigSorter = sorter.get();
                if (bigSorter.getContainers().contains(container)) {
                    player.sendMessage(Component.text("Container already in sorter!").color(NamedTextColor.RED));
                } else {
                    boolean result = ItemSorters.getEconomy().withdrawPlayer(player, ItemSorters.configs.getCost(sorter.getContainers().size()));
                    if (result) {
                      bigSorter.addContainer(container);
                      handler.saveSorter(bigSorter);
                      player.sendMessage(Component.text("Added container").color(NamedTextColor.GREEN));
                    } else {
                      player.sendMessage(Component.text("Insuffienct Funds").color(NamedTextColor.GREEN));
                    }
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

            boolean result = ItemSorters.getEconomy().withdrawPlayer(player, ItemSorters.configs.getCost(0));
            
            if (result) {
              Container holder = (Container) block;
              Sorter sorter = new Sorter(player.getUniqueId(), holder);
              FileHandler handler = new FileHandler();
              handler.saveSorter(sorter);
              player.sendMessage(Component.text("Created Sorter").color(NamedTextColor.GREEN));
            } else {
              player.sendMessage(Component.text("Insuffienct Funds").color(NamedTextColor.GREEN));
            }
        }
    };

    public abstract void handle(Block block, Player player);

}
