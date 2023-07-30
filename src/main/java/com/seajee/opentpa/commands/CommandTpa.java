package com.seajee.opentpa.commands;

import com.seajee.opentpa.OpenTpa;
import com.seajee.opentpa.Message;
import com.seajee.opentpa.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class CommandTpa implements CommandExecutor {

    private final OpenTpa plugin;

    public CommandTpa(OpenTpa plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // Check if command sender is a player
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Message.error("You are not a player"));
            return true;
        }

        // Check if usage is correct, otherwise reply with the correct usage
        if (args.length < 1) {
            commandSender.sendMessage(Message.info("Usage: /tpa <player>"));
            return true;
        }

        final String targetPlayerName = args[0];
        final String commandSenderName = commandSender.getName();

        // Check if request is sent to sender himself
        if (targetPlayerName.equals(commandSenderName)) {
            commandSender.sendMessage(Message.error("You cannot send a tpa request to yourself"));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        // Check if the target player is a valid player
        if (targetPlayer == null) {
            commandSender.sendMessage(Message.error(targetPlayerName + " is not a valid player"));
            return true;
        }

        // Check if sender already sent a tpa request to someone
        if (OpenTpa.requests.get(commandSenderName) != null) {
            commandSender.sendMessage(Message.error("You already have an open tpa request"));
            return true;
        }

        // Register the request
        OpenTpa.requests.put(commandSenderName, targetPlayerName);

        // Run a task for the request timeout
        (new BukkitRunnable() {
            @Override
            public void run() {
                OpenTpa.requests.remove(commandSenderName);
            }
        }).runTaskLater(plugin, TimeUtils.secondsToTicks(10));

        // Send info to players about the request
        targetPlayer.sendMessage(Message.warn(commandSenderName + " sent you a tpa request"));
        commandSender.sendMessage(Message.confirm("Request sent to " + targetPlayerName));

        return true;
    }
}
