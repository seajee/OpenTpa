package com.seajee.opentpa.commands;

import com.seajee.opentpa.Message;
import com.seajee.opentpa.OpenTpa;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandTpaCancel extends AbstractCommand {

    public CommandTpaCancel(String label, String usage) {
        super(label, usage);
    }

    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        final String commandSenderName = commandSender.getName();

        // Check if command sender has any requests active
        if (OpenTpa.requests.get(commandSenderName) == null) {
            commandSender.sendMessage(Message.info("You do not have any requests active"));
            return;
        }

        // Cancel the request
        OpenTpa.requests.remove(commandSenderName);
        commandSender.sendMessage(Message.confirm("Request cancelled"));
    }
}
