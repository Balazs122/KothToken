package com.PyrexNetwork.Storage;

import com.PyrexNetwork.KothTokenPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager implements StorageManager {

    private final File dataFile;
    private final YamlConfiguration dataConfig;

    // Constructor for FileManager
    public FileManager(String directory, String fileName) {
        File pluginDir = new File(KothTokenPlugin.getPlugin(KothTokenPlugin.class).getDataFolder(), directory);
        if (!pluginDir.exists()) {
            pluginDir.mkdirs(); // Create directory if it doesn't exist
        }
        this.dataFile = new File(pluginDir, fileName);
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);

        // Ensure the data file exists, create it if not
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile(); // Create the file if it does not exist
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getTokenBalance(String playerName) {
        return dataConfig.getInt(playerName + ".tokens", 0);
    }

    @Override
    public void addTokens(String playerName, int amount) {
        int currentBalance = getTokenBalance(playerName);
        dataConfig.set(playerName + ".tokens", currentBalance + amount);
        saveData();
    }

    @Override
    public void removeTokens(String playerName, int amount) {
        int currentBalance = getTokenBalance(playerName);
        dataConfig.set(playerName + ".tokens", Math.max(0, currentBalance - amount)); // Prevent negative balance
        saveData();
    }

    @Override
    public void setTokens(String playerName, int amount) {
        dataConfig.set(playerName + ".tokens", amount);
        saveData();
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
