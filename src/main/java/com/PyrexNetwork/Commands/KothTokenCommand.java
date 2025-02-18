package com.PyrexNetwork.Commands;

import com.PyrexNetwork.Storage.StorageManager;
import com.PyrexNetwork.Storage.FileManager;
import com.PyrexNetwork.KothTokenPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KothTokenCommand implements CommandExecutor, TabCompleter {

    private StorageManager storageManager;
    private KothTokenPlugin plugin;

    public KothTokenCommand(KothTokenPlugin plugin, StorageManager storageManager) {
        this.plugin = plugin;
        this.storageManager = storageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /kothtoken <balance|add|remove|set|reload>");
            return false;
        }

        String subCommand = args[0];

        switch (subCommand.toLowerCase()) {
            case "balance":
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    int balance = storageManager.getTokenBalance(player.getName());
                    sender.sendMessage("Your token balance: " + balance);
                } else {
                    sender.sendMessage("This command can only be used by players.");
                }
                break;

            case "add":
                if (args.length < 2) {
                    sender.sendMessage("Usage: /kothtoken add <amount> [player]");
                    return false;
                }

                try {
                    int amount = Integer.parseInt(args[1]);
                    if (amount <= 0) {
                        sender.sendMessage("The amount must be a positive number.");
                        return false;
                    }

                    // If the player has the kothtoken.admin permission, they can add tokens to others
                    if (sender instanceof Player) {
                        Player player = (Player) sender;

                        // Check if the player has the admin permission
                        if (player.hasPermission("kothtoken.admin") && args.length >= 3) {
                            String targetPlayerName = args[2];
                            if (!storageManager.getAllPlayers().contains(targetPlayerName)) {
                                sender.sendMessage("Player " + targetPlayerName + " has never played on the server.");
                                return false;
                            }
                            storageManager.addTokens(targetPlayerName, amount);
                            sender.sendMessage("Added " + amount + " tokens to " + targetPlayerName + "'s account.");
                        } else {
                            // Add tokens to the player's own account if no target player is provided
                            storageManager.addTokens(player.getName(), amount);
                            sender.sendMessage("Added " + amount + " tokens to your account.");
                        }
                    } else {
                        sender.sendMessage("This command can only be used by players.");
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage("Invalid amount. Please enter a valid number.");
                    return false;
                }
                break;

            case "remove":
                if (args.length < 2) {
                    sender.sendMessage("Usage: /kothtoken remove <amount>");
                    return false;
                }
                try {
                    int amount = Integer.parseInt(args[1]);
                    if (amount <= 0) {
                        sender.sendMessage("The amount must be a positive number.");
                        return false;
                    }
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        storageManager.removeTokens(player.getName(), amount);
                        sender.sendMessage("Removed " + amount + " tokens from your account.");
                    } else {
                        sender.sendMessage("This command can only be used by players.");
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage("Invalid amount. Please enter a valid number.");
                    return false;
                }
                break;

            case "set":
                if (args.length < 2) {
                    sender.sendMessage("Usage: /kothtoken set <amount>");
                    return false;
                }
                try {
                    int amount = Integer.parseInt(args[1]);
                    if (amount < 0) {
                        sender.sendMessage("The amount cannot be negative.");
                        return false;
                    }
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        storageManager.setTokens(player.getName(), amount);
                        sender.sendMessage("Your token balance has been set to " + amount + ".");
                    } else {
                        sender.sendMessage("This command can only be used by players.");
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage("Invalid amount. Please enter a valid number.");
                    return false;
                }
                break;

            case "reload":
                // Reload configuration and reset storage method
                plugin.reloadConfig();
                plugin.loadStorageManager(); // Assuming this method reloads the storage manager
                sender.sendMessage("Plugin configuration reloaded successfully.");
                break;

            default:
                sender.sendMessage("Unknown subcommand. Usage: /kothtoken <balance|add|remove|set|reload>");
                break;
        }
        return true;
    }

    // Tab completion for player names who have played
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 3 && sender instanceof Player) {
            // Suggest player names only for the 'add' command
            if ("add".equalsIgnoreCase(args[0])) {
                suggestions.addAll(storageManager.getAllPlayers()); // Add all player names from the data file
            }
        }
        return suggestions;
    }
}
