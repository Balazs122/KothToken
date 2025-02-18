package com.PyrexNetwork;

import com.PyrexNetwork.Commands.KothTokenCommand;
import com.PyrexNetwork.Commands.KothTokenTabCompleter;
import com.PyrexNetwork.MySQL.MySQLManager;
import com.PyrexNetwork.Placeholder.KothTokenExpansion;
import com.PyrexNetwork.Storage.FileManager;
import com.PyrexNetwork.Storage.StorageManager;
import me.clip.placeholderapi.PlaceholderAPI;  // Correct import
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

public class KothTokenPlugin extends JavaPlugin {
    private StorageManager storageManager;

    @Override
    public void onEnable() {
        saveDefaultConfig(); // Save default config.yml

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

            storageManager = new MySQLManager(host, port, database, username, password);

        } else if ("file".equalsIgnoreCase(storageMode)) {
            String directory = getConfig().getString("file_storage.directory");
            String fileName = getConfig().getString("file_storage.file_name");

            storageManager = new FileManager(directory, fileName);

        } else {
            getLogger().warning("Invalid storage mode in config.yml. Defaulting to file storage.");
            storageManager = new FileManager("plugins/KothToken/data", "saveddata.dat");
        }
    }

    // Register the placeholders
    private void registerPlaceholders() {
        PlaceholderAPI.registerPlaceholder(this, "koth_token_value", (player) -> {
            if (player != null && player.getName() != null) {
                int tokenBalance = storageManager.getTokenBalance(player.getName());
                return String.valueOf(tokenBalance); // Return the token balance as a string
            }
            return "0"; // Return a default value if player is null
        });
    }

    // Get the storage manager (either MySQL or File)
    public StorageManager getStorageManager() {
        return storageManager;
    }
}
