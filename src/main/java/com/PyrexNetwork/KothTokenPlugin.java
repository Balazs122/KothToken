package com.PyrexNetwork;

import com.PyrexNetwork.Commands.KothTokenCommand;
import com.PyrexNetwork.Commands.KothTokenTabCompleter;
import com.PyrexNetwork.Placeholder.KothTokenExpansion;
import com.PyrexNetwork.Storage.FileManager;
import com.PyrexNetwork.Storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;

public class KothTokenPlugin extends JavaPlugin {
    private StorageManager storageManager;
    private FileConfiguration langConfig;
    private String prefix;



    @Override
    public void onEnable() {
        try {
            saveDefaultConfig(); // Save default config.yml
            loadLangConfig(); // Load lang.yml

            loadStorageManager(); // Initialize the storage manager based on config

            // Register the main command
            getCommand("kothtoken").setExecutor(new KothTokenCommand(this, storageManager));

            // Register the tab completer for the /kothtoken command
            getCommand("kothtoken").setTabCompleter(new KothTokenTabCompleter());

            // Check if PlaceholderAPI is available and register placeholders
            Plugin placeholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
            if (placeholderAPI != null) {
                getLogger().info("PlaceholderAPI found, registering placeholders.");
                new KothTokenExpansion(this).register();  // Registering the placeholder expansion here
            } else {
                getLogger().warning("PlaceholderAPI not found, some features may not work.");
            }
            getLogger().info("\u001B[37m==========================================");
            getLogger().info("\u001B[32mKothToken plugin successfully loaded!");
            getLogger().info("\u001B[37m==========================================");
        } catch (Exception e) {

            getLogger().severe("\u001B[37m==========================================");
            getLogger().severe("\u001B[31mError loading KothToken plugin!");
            getLogger().severe("\u001B[37m==========================================");
        }
    }

    @Override
    public void onDisable() {
        // Any cleanup if needed
    }

    // Method to load or reload the storage manager based on the config
    public void loadStorageManager() {
        String storageMode = getConfig().getString("storage_mode");

        if ("mysql".equalsIgnoreCase(storageMode)) {
            String host = getConfig().getString("mysql.host");
            String port = getConfig().getString("mysql.port");
            String database = getConfig().getString("mysql.database");
            String username = getConfig().getString("mysql.username");
            String password = getConfig().getString("mysql.password");

            // Initialize MySQL storage manager here

        } else if ("file".equalsIgnoreCase(storageMode)) {
            String directory = getConfig().getString("file_storage.directory");
            String fileName = getConfig().getString("file_storage.file_name");

            storageManager = new FileManager(directory, fileName);

        } else {
            storageManager = new FileManager("plugins/KothToken/data", "saveddata.dat");
        }
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    // Load lang.yml file
    private void loadLangConfig() {
        File langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            saveResource("lang.yml", false);
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);
        prefix = langConfig.getString("messages.prefix", "");
    }

    // Get a message from lang.yml with prefix
    public String getMessage(String key) {
        String message = prefix + langConfig.getString("messages." + key);
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public void reloadPlugin() {
        reloadConfig();
        loadLangConfig();
        loadStorageManager();
    }
}