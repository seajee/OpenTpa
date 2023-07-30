package com.seajee.opentpa.commands;

import com.seajee.opentpa.OpenTpa;
import com.seajee.opentpa.globals.Defaults;
import com.seajee.opentpa.utils.TimeUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class CommandTpAccept implements CommandExecutor {

    private final OpenTpa plugin;

    public CommandTpAccept(OpenTpa plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Defaults.TextComponent()
                    .append(Component.text("Error: You are not a player")));
            return true;
        }

        // Check if usage is correct, otherwise reply with the correct usage
        if (args.length < 1) {
            commandSender.sendMessage(Defaults.TextComponent()
                    .append(Component.text("Usage: /tpaccept <player>")));
            return true;
        }

        final String commandSenderName = commandSender.getName();
        final String requestSenderName = args[0];

        // Check if command is used to sender himself
        if (commandSenderName.equals(requestSenderName)) {
            commandSender.sendMessage(Defaults.TextComponent()
                    .append(Component.text("You must use /tpaccept with a valid request")));
            return true;
        }

        Player requestSender = Bukkit.getPlayer(requestSenderName);

        // Check if the target player is a valid player
        if (requestSender == null) {
            commandSender.sendMessage(Defaults.TextComponent()
                    .append(Component.text(requestSenderName + " is not a valid player")));
            return true;
        }

        // Check if command sender has any requests pending
        if (OpenTpa.requests.get(requestSenderName) == null) {
            commandSender.sendMessage(Defaults.TextComponent()
                    .append(Component.text("You do not have any tpa requests sent by " + requestSenderName)));
            return true;
        }

        // Teleport logic
        (new BukkitRunnable() {
            @Override
            public void run() {
                requestSender.teleport((Player) commandSender);
            }
        }).runTaskLater(this.plugin, TimeUtils.secondsToTicks(5));

        requestSender.sendMessage(Defaults.TextComponent()
                .append(Component.text("Teleporting in 5 seconds")));

        commandSender.sendMessage(Defaults.TextComponent()
                .append(Component.text(requestSenderName + " request accepted")));

        return true;
    }
}