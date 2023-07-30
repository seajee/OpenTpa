package com.seajee.opentpa;

import com.seajee.opentpa.commands.CommandTpAccept;
import com.seajee.opentpa.commands.CommandTpa;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpenTpa extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("tpa").setExecutor(new CommandTpa());
        this.getCommand("tpaccept").setExecutor(new CommandTpAccept());
    }

    @Override
    public void onDisable() {
    }
}
