package com.PyrexNetwork.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import com.PyrexNetwork.PyrexTokenPlugin;

public class PyrexTokenExpansion extends PlaceholderExpansion {

    private final PyrexTokenPlugin plugin;

    public PyrexTokenExpansion(PyrexTokenPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "0"; // Return 0 if player is null
        }

        // Only handle the "token_value" placeholder
        if (identifier.equals("token_value")) {
            int tokenBalance = plugin.getStorageManager().getTokens(player.getUniqueId());
            return String.valueOf(tokenBalance); // Return token balance
        }

        return null; // Return null for unrecognized placeholders
    }

    @Override
    public String getIdentifier() {
        return "pyrextoken"; // The identifier for placeholders (e.g., %pyrextoken_token_value%)
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