package com.seajee.opentpa.commands;

import com.seajee.opentpa.OpenTpa;
import com.seajee.opentpa.globals.Defaults;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTpaCancel implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Defaults.TextComponent()
                    .append(Component.text("Error: You are not a player")));
            return true;
        }

        final String commandSenderName = commandSender.getName();

        // Check if command sender has any requests active
        if (OpenTpa.requests.get(commandSenderName) == null) {
            commandSender.sendMessage(Defaults.TextComponent()
                    .append(Component.text("You do not have any requests active")));
            return true;
        }

        // Cancel the request
        OpenTpa.requests.remove(commandSenderName);

        commandSender.sendMessage(Defaults.TextComponent()
                .append(Component.text("Request cancelled")));

        return true;
    }
}
