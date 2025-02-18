package com.PyrexNetwork.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class KothTokenTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        // If it's the first argument, show subcommands like balance, add, remove, etc.
        if (args.length == 1) {
            suggestions.add("balance");
            suggestions.add("add");
            suggestions.add("remove");
            suggestions.add("set");
            suggestions.add("reload");
        }

        return suggestions;
    }
}
