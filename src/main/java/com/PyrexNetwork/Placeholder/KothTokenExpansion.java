package com.PyrexNetwork.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import com.PyrexNetwork.KothTokenPlugin;

public class KothTokenExpansion extends PlaceholderExpansion {

    private final KothTokenPlugin plugin;

    public KothTokenExpansion(KothTokenPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "0"; // Return 0 if player is null
        }

        // Only handle the "koth_token_value" placeholder
        if (identifier.equals("koth_token_value")) {
            int tokenBalance = plugin.getStorageManager().getTokenBalance(player.getName());
            return String.valueOf(tokenBalance); // Return token balance
        }

        return null; // Return null for unrecognized placeholders
    }

    @Override
    public String getIdentifier() {
        return "kothtoken"; // The identifier for placeholders (e.g., %kothtoken_koth_token_value%)
    }

    @Override
    public String getAuthor() {
        return "PyrexNetwork"; // Author name
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion(); // Version of the plugin
    }
}
