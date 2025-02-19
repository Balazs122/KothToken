package com.PyrexNetwork.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileManager extends StorageManager {
    private final File dataFile;
    private final FileConfiguration dataConfig;

    public FileManager(String directory, String fileName) {
        File pluginDir = new File(directory);
        if (!pluginDir.exists()) {
            pluginDir.mkdirs();
        }
        this.dataFile = new File(pluginDir, fileName);
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);

        // Ensure the data file exists, create it if not
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addTokens(UUID playerUUID, int amount) {
        String playerName = playerUUID.toString();
        int currentBalance = getTokenBalance(playerName);
        dataConfig.set(playerName + ".tokens", currentBalance + amount);
        saveData();
    }

    @Override
    public void setTokens(UUID playerUUID, int amount) {
        String playerName = playerUUID.toString();
        dataConfig.set(playerName + ".tokens", amount);
        saveData();
    }

    @Override
    public int getTokens(UUID playerUUID) {
        String playerName = playerUUID.toString();
        return getTokenBalance(playerName);
    }

    private int getTokenBalance(String playerName) {
        return dataConfig.getInt(playerName + ".tokens", 0);
    }

    private void saveData() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Return a list of all player names who have played on the server
    public List<String> getAllPlayers() {
        List<String> players = new ArrayList<>();
        for (String key : dataConfig.getKeys(false)) {
            players.add(key); // Add each player's name from the data file
        }
        return players;
    }
}