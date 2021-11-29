package com.somemone.itemsorters.sorter;

import com.somemone.itemsorters.sorter.item.SorterItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SorterItemCollection {

    private ArrayList<SorterItem> items;
    private Material material;

    public SorterItemCollection (Material material) {
        items = new ArrayList<>();
        this.material = material;
    }

    public void addItem (SorterItem item) {
        items.add(item);
    }

    public void removeItem (SorterItem item) {
        items.remove(item);
    }

    public ArrayList<SorterItem> getItems () {
        return items;
    }

    public int getTotalItems() {
        int num = 0;
        for (SorterItem item : items) num += item.getItemAmount();
        return num;
    }
}
