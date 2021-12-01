package com.somemone.itemsorters.command;

import java.util.Optional;

import com.somemone.itemsorters.ItemSorters;
import com.somemone.itemsorters.file.FileHandler;
import com.somemone.itemsorters.inventory.PlayerState;
import com.somemone.itemsorters.inventory.SorterInventory;
import com.somemone.itemsorters.sorter.Sorter;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;

public class SorterCommand implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
    @NotNull String[] args) {
        
        if (args.length == 0) return false;
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        switch (args[0]) {

            case "add":
                if (!ItemSorters.playerStates.containsKey(player)) {
                    player.sendActionBar(Component.text().content("Now in ADDING mode.").color(NamedTextColor.GOLD).build());
                    ItemSorters.playerStates.put(player, PlayerState.PLAYER_ADDING);
                } else {
                    player.sendActionBar(Component.text().content("Removed ADDING mode").color(NamedTextColor.GOLD).build());
                    ItemSorters.playerStates.remove(player);
                }
                break;
            case "remove":
                if (!ItemSorters.playerStates.containsKey(player)) {
                    player.sendActionBar(Component.text().content("Now in REMOVING mode.").color(NamedTextColor.GOLD).build());
                    ItemSorters.playerStates.put(player, PlayerState.PLAYER_REMOVING);
                } else {
                    player.sendActionBar(Component.text().content("Removed REMOVING mode").color(NamedTextColor.GOLD).build());
                    ItemSorters.playerStates.remove(player);
                }
                break;
            case "view":
                FileHandler handler = new FileHandler();
                Optional<Sorter> optionalSorter = handler.getSorterFromPlayer(player.getUniqueId());
                if (optionalSorter.isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "You don't have an item sorter!");
                    return true;
                }
                
                if (args.length != 2) return false;
                Material chosenMaterial = Material.valueOf( args[1].toUpperCase() );

                Sorter sorter = optionalSorter.get();
                SorterInventory sorterInventory = new SorterInventory(sorter, chosenMaterial);
                ItemSorters.openInventories.put(player, sorterInventory);
                player.openInventory(sorterInventory.drawInventory(1));
                break;
            case "create":
                if (!ItemSorters.playerStates.containsKey(player)) {
                    player.sendActionBar(Component.text().content("Now in CREATING mode.").color(NamedTextColor.GOLD).build());
                    ItemSorters.playerStates.put(player, PlayerState.PLAYER_CREATING);
                } else {
                    player.sendActionBar(Component.text().content("Removed CREATING mode").color(NamedTextColor.GOLD).build());
                    ItemSorters.playerStates.remove(player);
                }
                break;
            case "invite":
                // TODO: Add invite functionality
                break;
            case "leave":
                // TODO: Add invite functionality
                break;

        }

        return false;
    }
}
