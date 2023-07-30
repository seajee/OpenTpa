package com.seajee.opentpa;

import com.seajee.opentpa.commands.CommandTpAccept;
import com.seajee.opentpa.commands.CommandTpa;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class OpenTpa extends JavaPlugin {

    public static HashMap<String, String> requests = new HashMap<>();

    @Override
    public void onEnable() {
        this.getCommand("tpa").setExecutor(new CommandTpa(this));
        this.getCommand("tpaccept").setExecutor(new CommandTpAccept(this));
    }

    @Override
    public void onDisable() {
    }
}
