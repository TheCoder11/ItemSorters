package com.somemone.itemsorters.sorter;

import com.somemone.itemsorters.sorter.item.SorterItem;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Sorter {

    private UUID id;
    private HashMap<Material, SorterItemCollection> itemCollections;
    private ArrayList<Container> containers;
    private ArrayList<UUID> accessors;
    private Container mainContainer;
    private UUID owner;

    public Sorter (UUID owner, Container mainContainer) {
        this.owner = owner;
        this.mainContainer = mainContainer;
        this.accessors = new ArrayList<>();
        this.containers = new ArrayList<>();
        this.itemCollections = new HashMap<>();
        this.id = UUID.randomUUID();
    }

    /*
    Adds item to the sorter, returns false if full
     */
    public boolean addItem (ItemStack item) {
        if (itemCollections.containsKey(item.getType())) {
            Container container = findNextEmptyContainer();
            if (container != null) {
                container.getInventory().addItem(item);
                SorterItem sorterItem = new SorterItem(item, container, id);
                itemCollections.get(item.getType()).addItem(sorterItem);
            } else return false;
        } else {
            SorterItemCollection sic = new SorterItemCollection(item.getType());
            Container container = findNextEmptyContainer();
            if (container != null) {
                container.getInventory().addItem(item);
                SorterItem sorterItem = new SorterItem(item, container, id);
                sic.addItem(sorterItem);

                itemCollections.put(item.getType(), sic);
            } else return false;
        }
        return true;
    }
    
    /*
     * Removes item from sorter and container. Returns false if not found
     */
    public boolean removeItem (ItemStack item) {
        if (itemCollections.containsKey(item.getType())) {
            SorterItemCollection itemCollection = itemCollections.get(item.getType());

            boolean flag = false;
            SorterItem trash = null;

            for (SorterItem sItem : itemCollection.getItems()) {
                if (sItem.getItem().equals(item)) {
                    sItem.getContainer().getInventory().remove(item);
                    flag = true;
                    trash = sItem;
		    break;
                }
            }
            if (flag) itemCollection.removeItem(trash);

        } else return false;
        return true;
    }

    /*
     * Finds next container with empty slots. Returns Optional.empty() if sorter is full.
     */

    public Optional<Container> findNextEmptyContainer () {
        for (Container container : containers) {
            if (container.getInventory().getStorageContents().length != container.getInventory().getSize()) {
                return Optional.of(container);
            }
        }
        return Optional.empty();
    }

    public void addAccessor (UUID uuid) {
        accessors.add(uuid);
    }

    public void removeAccessor (UUID uuid) {
        accessors.remove(uuid);
    }

    public boolean isAccessor (UUID uuid) { return accessors.contains(uuid); }

    public UUID getID () {return id;}

    public HashMap<Material, SorterItemCollection> getCollection() {return itemCollections;}

    public ArrayList<Container> getContainers() {return containers;}

    public Container getMainContainer() {return mainContainer;}

    public void addContainer (Container container) {
        containers.add(container);
    }

    public UUID getOwner() {return owner;}

    public void getOwner(UUID uuid) {owner = uuid;}



}
