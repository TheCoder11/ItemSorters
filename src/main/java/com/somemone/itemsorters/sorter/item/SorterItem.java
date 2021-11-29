package com.somemone.itemsorters.sorter.item;

import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SorterItem {

    private ItemStack item;
    private Container container;
    private Material material;
    private UUID sorterID;

    public SorterItem(ItemStack item, Container container, UUID sorterID) {
        this.item = item;
        this.container = container;
        this.sorterID = sorterID;

        material = item.getType();
    }

    public int getItemAmount () {return item.getAmount();}

    public void setItemAmount (int amount) {
        item.setAmount(amount);
    }

    public Material getMaterial() {return material;}

    public Container getContainer() {
        return container;
    }

    public UUID getSorterID() {
        return sorterID;
    }

    public ItemStack getItem() {
        return item;
    }

    public void remove() {
        container.getInventory().remove(item);
    }


}
