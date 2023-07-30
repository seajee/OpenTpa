package com.seajee.opentpa.commands;

import com.seajee.opentpa.globals.Defaults;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTpAccept implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Defaults.TextComponent()
                    .append(Component.text("You are not a player")));
            return true;
        }

        commandSender.sendMessage(Defaults.TextComponent()
                .append(Component.text("OK")));

        return true;
    }
}
