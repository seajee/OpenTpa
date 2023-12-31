package com.seajee.opentpa.commands;

import com.seajee.opentpa.Message;
import com.seajee.opentpa.OpenTpa;
import com.seajee.opentpa.utils.TimeUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CommandTpa extends AbstractCommand {

    public CommandTpa(String label, String usage) {
        super(label, usage);
    }

    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        // Check if usage is correct, otherwise reply with the correct usage
        if (args.size() == 0) {
            this.sendUsage((Player) commandSender);
            return;
        }

        final String targetPlayerName = args.get(0);
        final String commandSenderName = commandSender.getName();

        // Check if request is sent to sender himself
        if (targetPlayerName.equals(commandSenderName)) {
            commandSender.sendMessage(Message.error("You cannot send a tpa request to yourself"));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        // Check if the target player is a valid player
        if (targetPlayer == null) {
            commandSender.sendMessage(Message.error(targetPlayerName + " is not a valid player"));
            return;
        }

        // Check if sender already sent a tpa request to someone
        if (OpenTpa.requests.get(commandSenderName) != null) {
            commandSender.sendMessage(Message.error("You already have an open tpa request"));
            return;
        }

        // Register the request
        OpenTpa.requests.put(commandSenderName, targetPlayerName);

        // Run a task for the request timeout
        (new BukkitRunnable() {
            @Override
            public void run() {
                OpenTpa.requests.remove(commandSenderName);
            }
        }).runTaskLater(this.getInstance(), TimeUtils.secondsToTicks(30));

        // Send messages to players about the request
        targetPlayer.sendMessage(Message.warn("New tpa request by " + commandSenderName)
                .append(Component.text("\n[ACCEPT]").color(NamedTextColor.GREEN)
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + commandSenderName))
                .append(Component.text(" [DENY]").color(NamedTextColor.RED)
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpadeny " + commandSenderName)))));

        commandSender.sendMessage(Message.confirm("Request sent to " + targetPlayerName));
    }
}
