package com.seajee.opentpa.commands;

import com.seajee.opentpa.Message;
import com.seajee.opentpa.OpenTpa;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractCommand implements CommandExecutor {

    protected final String usage;
    private final OpenTpa instance;
    private final String label;

    public AbstractCommand(String label, String usage) {
        this.label = label;
        this.usage = usage;
        this.instance = OpenTpa.getInstance();
        this.register();
    }

    public void register() {
        Objects.requireNonNull(this.instance.getCommand(this.label)).setExecutor(this);
    }

    public void sendUsage(Player target) {
        target.sendMessage(Message.info("Usage: " + this.usage));
    }

    public abstract void execute(CommandSender commandSender, List<String> args);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // Check if command sender is a player
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Message.error("This command can be executed only by a player"));
            return true;
        }

        // Execute command
        List<String> finalArgs = (args.length != 0) ? new ArrayList<>(Arrays.asList(args)) : new ArrayList<>();
        this.instance.getServer().getScheduler().runTask(this.instance, () -> this.execute(commandSender, finalArgs));

        return true;
    }

    public OpenTpa getInstance() {
        return this.instance;
    }
}
