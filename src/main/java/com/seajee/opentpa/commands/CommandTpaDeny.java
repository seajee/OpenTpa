package com.seajee.opentpa.commands;

import com.seajee.opentpa.Message;
import com.seajee.opentpa.OpenTpa;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandTpaDeny extends AbstractCommand {

    public CommandTpaDeny(String label, String usage) {
        super(label, usage);
    }

    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        // Check if usage is correct, otherwise reply with the correct usage
        if (args.size() == 0) {
            this.sendUsage((Player) commandSender);
            return;
        }

        final String commandSenderName = commandSender.getName();
        final String requestSenderName = args.get(0);

        // Check if command is used to sender himself
        if (commandSenderName.equals(requestSenderName)) {
            commandSender.sendMessage(Message.error("You must use /tpadeny with a valid request"));
            return;
        }

        Player requestSender = Bukkit.getPlayer(requestSenderName);

        // Check if the target player is a valid player
        if (requestSender == null) {
            commandSender.sendMessage(Message.error(requestSenderName + " is not a valid player"));
            return;
        }

        // Check if command sender has any requests pending
        if (OpenTpa.requests.get(requestSenderName) == null) {
            commandSender.sendMessage(Message.info("You do not have any tpa requests sent by " + requestSenderName));
            return;
        }

        // Deny the request
        OpenTpa.requests.remove(requestSenderName);
        commandSender.sendMessage(Message.confirm(requestSenderName + " request denied"));
        requestSender.sendMessage(Message.info(commandSenderName + " denied your tpa request"));
    }
}
