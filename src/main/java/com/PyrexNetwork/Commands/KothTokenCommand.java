package com.PyrexNetwork.Commands;

import com.PyrexNetwork.KothTokenPlugin;
import com.PyrexNetwork.Storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KothTokenCommand implements CommandExecutor {
    private final KothTokenPlugin plugin;
    private final StorageManager storageManager;

    public KothTokenCommand(KothTokenPlugin plugin, StorageManager storageManager) {
        this.plugin = plugin;
        this.storageManager = storageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(plugin.getMessage("usage"));
            return false;
        }

        String action = args[0];

        if (action.equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("kothtoken.admin.reload")) {
                sender.sendMessage(plugin.getMessage("no_permission"));
                return false;
            }
            plugin.reloadPlugin();
            sender.sendMessage(plugin.getMessage("reload_success"));
            return true;
        }

        if (action.equalsIgnoreCase("balance")) {
            String playerName;
            if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(plugin.getMessage("specify_player"));
                    return false;
                }
                playerName = sender.getName();
            } else {
                if (!sender.hasPermission("kothtoken.admin.manage")) {
                    sender.sendMessage(plugin.getMessage("no_permission"));
                    return false;
                }
                playerName = args[1];
            }

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            if (!offlinePlayer.hasPlayedBefore() && !offlinePlayer.isOnline()) {
                sender.sendMessage(plugin.getMessage("never_online"));
                return false;
            }

            int balance = storageManager.getTokens(offlinePlayer.getUniqueId());
            sender.sendMessage(plugin.getMessage("balance").replace("{player}", playerName).replace("{balance}", String.valueOf(balance)));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(plugin.getMessage("usage"));
            return false;
        }

        String playerName = args[1];
        int amount = 0;

        if (!action.equalsIgnoreCase("balance")) {
            if (!sender.hasPermission("kothtoken.admin.manage")) {
                sender.sendMessage(plugin.getMessage("no_permission"));
                return false;
            }
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(plugin.getMessage("amount_must_be_number"));
                return false;
            }
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        if (!offlinePlayer.hasPlayedBefore() && !offlinePlayer.isOnline()) {
            sender.sendMessage(plugin.getMessage("never_online"));
            return false;
        }

        switch (action.toLowerCase()) {
            case "add":
                storageManager.addTokens(offlinePlayer.getUniqueId(), amount);
                sender.sendMessage(plugin.getMessage("added_tokens").replace("{amount}", String.valueOf(amount)).replace("{player}", playerName));
                break;
            case "remove":
                storageManager.removeTokens(offlinePlayer.getUniqueId(), amount);
                sender.sendMessage(plugin.getMessage("removed_tokens").replace("{amount}", String.valueOf(amount)).replace("{player}", playerName));
                break;
            case "set":
                storageManager.setTokens(offlinePlayer.getUniqueId(), amount);
                sender.sendMessage(plugin.getMessage("set_tokens").replace("{amount}", String.valueOf(amount)).replace("{player}", playerName));
                break;
            default:
                sender.sendMessage(plugin.getMessage("unknown_action").replace("{action}", action));
                return false;
        }

        return true;
    }
}