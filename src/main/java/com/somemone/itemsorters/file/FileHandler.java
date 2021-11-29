package com.somemone.itemsorters.file;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.somemone.itemsorters.ItemSorters;
import com.somemone.itemsorters.sorter.Sorter;

import org.bukkit.entity.Player;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class FileHandler {

    public File resourceFile;

    public FileHandler () {
        resourceFile = new File(ItemSorters.getPlugin(ItemSorters.class).getDataFolder() + "groups.yml");
    }

    public Optional<Sorter> getSorterFromPlayer (UUID player) {
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
            }

            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader(resourceFile));

            ArrayList<Sorter> results = gson.fromJson(reader, new TypeToken<ArrayList<Sorter>>(){}.getType());

            for (Sorter result : results) {
                if (result.getOwner().equals(player) || result.isAccessor(player)) return Optional.of(result);
            }
            return Optional.empty();

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean saveSorter (Sorter sorter) {
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
            }

            Gson gson = new Gson();

            BufferedReader reader = new BufferedReader(new FileReader(resourceFile));

            ArrayList<Sorter> results = gson.fromJson(reader, new TypeToken<ArrayList<Sorter>>(){}.getType());

            for (Sorter result : results) {
                if (result.getID().equals(sorter.getID())) {
                    results.set( results.indexOf(result), sorter );
                    break;
                }
            }

            String result = gson.toJson(results);

            BufferedWriter writer = new BufferedWriter(new FileWriter(resourceFile, true));
            writer.write(result);
            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
