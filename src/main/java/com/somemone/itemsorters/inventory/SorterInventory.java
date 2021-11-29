package com.somemone.itemsorters.inventory;

import com.somemone.itemsorters.sorter.Sorter;
import com.somemone.itemsorters.sorter.SorterItemCollection;
import com.somemone.itemsorters.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SorterInventory {

    public Sorter sorter;

    public static ItemStack nextButton;
    public static ItemStack prevButton;
    public static ItemStack backButton;
    public static ItemStack filler;

    public SorterInventory(Sorter sorter) {
        this.sorter = sorter;

        nextButton = new ItemBuilder(Material.ARROW).setName(ChatColor.GREEN + "Next Page").toItemStack();
        prevButton = new ItemBuilder(Material.ARROW).setName(ChatColor.RED + "Previous Page").toItemStack();
        backButton = new ItemBuilder(Material.REDSTONE_BLOCK).setName(ChatColor.RED + "Back").toItemStack();
        filler = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("").toItemStack();
    }

    public Inventory drawInventory(Material material, int page) {

        page--;

        SorterItemCollection sic = sorter.getCollection().get(material);
        if ( sic.getItems().size() / 27 > page ) return null;

        Inventory inventory = Bukkit.createInventory(null, 36, "Connected to ISN!");

        inventory.setItem(27, filler);
        inventory.setItem(28, filler);
        inventory.setItem(29, filler);
        inventory.setItem(30, prevButton);
        inventory.setItem(31, backButton);
        inventory.setItem(32, nextButton);
        inventory.setItem(33, filler);
        inventory.setItem(34, filler);
        inventory.setItem(35, filler);

        page *= 27;
        int limit = 27;
        if (page + 27 > sic.getItems().size()) {
            limit = sic.getItems().size() - page;
        }

        for (int i = page; i < limit; i++) {
            inventory.addItem( sic.getItems().get(i).getItem() );
        }

        return inventory;

    }

}
