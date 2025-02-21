package com.PyrexNetwork;

import com.PyrexNetwork.Commands.PyrexTokenCommand;
import com.PyrexNetwork.Commands.PyrexTokenTabCompleter;
import com.PyrexNetwork.Placeholder.PyrexTokenExpansion;
import com.PyrexNetwork.Storage.FileManager;
import com.PyrexNetwork.Storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;

import java.io.File;

public class PyrexTokenPlugin extends JavaPlugin {
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
            getCommand("pyrextoken").setExecutor(new PyrexTokenCommand(this, storageManager));

            // Register the tab completer for the /pyrextoken command
            getCommand("pyrextoken").setTabCompleter(new PyrexTokenTabCompleter());

            // Check if PlaceholderAPI is available and register placeholders
            Plugin placeholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
            if (placeholderAPI != null) {
                getLogger().info("PlaceholderAPI found, registering placeholders.");
                new PyrexTokenExpansion(this).register();  // Registering the placeholder expansion here
            } else {
                getLogger().warning("PlaceholderAPI not found, some features may not work.");
            }
            getLogger().info("\u001B[37m==========================================");
            getLogger().info("\u001B[32mPyrexToken plugin successfully loaded!");
            getLogger().info("\u001B[37m==========================================");
        } catch (Exception e) {
            getLogger().severe("\u001B[37m==========================================");
            getLogger().severe("\u001B[31mError loading PyrexToken plugin!");
            getLogger().severe(e.getMessage());
            getLogger().severe("\u001B[37m==========================================");
        }
    }

    @Override
    public void onDisable() {
        // Any cleanup if needed
    }

    // Method to load or reload the storage manager based on the config
    public void loadStorageManager() {
        try {
            String storageMode = getConfig().getString("storage_mode");
            getLogger().info("Storage mode: " + storageMode);

            if ("mysql".equalsIgnoreCase(storageMode)) {
                String host = getConfig().getString("mysql.host");
                String port = getConfig().getString("mysql.port");
                String database = getConfig().getString("mysql.database");
                String username = getConfig().getString("mysql.username");
                String password = getConfig().getString("mysql.password");

                // Initialize MySQL storage manager here

            } else {
                String directory = getDataFolder() + File.separator + "data" + File.separator + "saveddata";
                String fileName = "saveddata.dat";

                getLogger().info("File storage configuration - Directory: " + directory + ", File name: " + fileName);

                storageManager = new FileManager(directory, fileName);
            }
        } catch (Exception e) {
            getLogger().severe("Error loading storage manager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    // Load lang.yml file directly
    private void loadLangConfig() {
        try {
            File langFile = new File(getDataFolder(), "lang.yml");
            if (!langFile.exists()) {
                saveResource("lang.yml", false);
            }
            langConfig = YamlConfiguration.loadConfiguration(langFile);
            prefix = langConfig.getString("messages.prefix", "");
        } catch (Exception e) {
            getLogger().severe("Error loading language configuration: " + e.getMessage());
        }
    }

    // Get a message from lang.yml with prefix
    public String getMessage(String key) {
        if (langConfig == null) {
            getLogger().severe("Language configuration is not loaded.");
            return ChatColor.RED + "Error: Language configuration not loaded.";
        }
        String message = prefix + langConfig.getString("messages." + key);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    // Get a raw message from lang.yml without prefix
    public String getRawMessage(String key) {
        if (langConfig == null) {
            getLogger().severe("Language configuration is not loaded.");
            return ChatColor.RED + "Error: Language configuration not loaded.";
        }
        String message = langConfig.getString("messages." + key);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void reloadPlugin() {
        try {
            reloadConfig();
            loadLangConfig();
            loadStorageManager();
        } catch (Exception e) {
            getLogger().severe("Error reloading plugin: " + e.getMessage());
        }
    }
}