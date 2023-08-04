package com.seajee.opentpa.commands;

import com.seajee.opentpa.Message;
import com.seajee.opentpa.OpenTpa;
import com.seajee.opentpa.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CommandTpAccept extends AbstractCommand {

    public CommandTpAccept(String label, String usage) {
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
            commandSender.sendMessage(Message.error("You must use /tpaccept with a valid request"));
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

        // Teleport logic
        (new BukkitRunnable() {
            @Override
            public void run() {
                requestSender.teleport((Player) commandSender);
            }
        }).runTaskLater(this.getInstance(), TimeUtils.secondsToTicks(5));

        requestSender.sendMessage(Message.confirm("Teleporting in 5 seconds"));
        commandSender.sendMessage(Message.confirm(requestSenderName + " request accepted"));
    }
}
