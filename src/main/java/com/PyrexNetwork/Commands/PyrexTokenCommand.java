package com.PyrexNetwork.Commands;

import com.PyrexNetwork.Storage.StorageManager;
import com.PyrexNetwork.PyrexTokenPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class PyrexTokenCommand implements CommandExecutor {
    private final PyrexTokenPlugin plugin;
    private final StorageManager storageManager;

    public PyrexTokenCommand(PyrexTokenPlugin plugin, StorageManager storageManager) {
        this.plugin = plugin;
        this.storageManager = storageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.getMessage("usage"));
            return true;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "add":
                if (args.length < 3) {
                    sender.sendMessage(plugin.getMessage("usage"));
                    return true;
                }
                String addPlayerName = args[1];
                int addAmount;
                try {
                    addAmount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(plugin.getMessage("amount_must_be_number"));
                    return true;
                }
                UUID addPlayerUUID = getPlayerUUID(addPlayerName);
                if (addPlayerUUID == null) {
                    sender.sendMessage(plugin.getMessage("never_online"));
                    return true;
                }
                storageManager.addTokens(addPlayerUUID, addAmount);
                sender.sendMessage(plugin.getMessage("added_tokens").replace("{amount}", String.valueOf(addAmount)).replace("{player}", addPlayerName));
                break;

            case "set":
                if (args.length < 3) {
                    sender.sendMessage(plugin.getMessage("usage"));
                    return true;
                }
                String setPlayerName = args[1];
                int setAmount;
                try {
                    setAmount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(plugin.getMessage("amount_must_be_number"));
                    return true;
                }
                UUID setPlayerUUID = getPlayerUUID(setPlayerName);
                if (setPlayerUUID == null) {
                    sender.sendMessage(plugin.getMessage("never_online"));
                    return true;
                }
                storageManager.setTokens(setPlayerUUID, Math.max(setAmount, 0));
                sender.sendMessage(plugin.getMessage("set_tokens").replace("{amount}", String.valueOf(setAmount)).replace("{player}", setPlayerName));
                break;

            case "remove":
                if (args.length < 3) {
                    sender.sendMessage(plugin.getMessage("usage"));
                    return true;
                }
                String removePlayerName = args[1];
                int removeAmount;
                try {
                    removeAmount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(plugin.getMessage("amount_must_be_number"));
                    return true;
                }
                UUID removePlayerUUID = getPlayerUUID(removePlayerName);
                if (removePlayerUUID == null) {
                    sender.sendMessage(plugin.getMessage("never_online"));
                    return true;
                }
                int currentBalance = storageManager.getTokens(removePlayerUUID);
                if (currentBalance == 0) {
                    sender.sendMessage(plugin.getMessage("balance_cannot_be_below_zero"));
                    return true;
                }
                int newBalance = Math.max(currentBalance - removeAmount, 0);
                storageManager.setTokens(removePlayerUUID, newBalance);
                sender.sendMessage(plugin.getMessage("removed_tokens").replace("{amount}", String.valueOf(removeAmount)).replace("{player}", removePlayerName));
                break;

            case "balance":
                if (args.length < 2) {
                    sender.sendMessage(plugin.getMessage("usage"));
                    return true;
                }
                String balancePlayerName = args[1];
                UUID balancePlayerUUID = getPlayerUUID(balancePlayerName);
                if (balancePlayerUUID == null) {
                    sender.sendMessage(plugin.getMessage("never_online"));
                    return true;
                }
                int balance = storageManager.getTokens(balancePlayerUUID);
                sender.sendMessage(plugin.getMessage("balance").replace("{player}", balancePlayerName).replace("{balance}", String.valueOf(balance)));
                break;

            case "version":
                sender.sendMessage(plugin.getMessage("version").replace("{version}", plugin.getDescription().getVersion()));
                break;

            case "help":
                sender.sendMessage(plugin.getRawMessage("help"));
                break;

            case "reload":
                plugin.reloadPlugin();
                sender.sendMessage(plugin.getMessage("reload_success"));
                break;

            default:
                sender.sendMessage(plugin.getMessage("unknown_action").replace("{action}", subCommand));
                break;
        }
        return true;
    }

    private UUID getPlayerUUID(String playerName) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        return player.hasPlayedBefore() ? player.getUniqueId() : null;
    }
}