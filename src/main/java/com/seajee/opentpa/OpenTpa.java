package com.seajee.opentpa;

import com.seajee.opentpa.commands.CommandTpAccept;
import com.seajee.opentpa.commands.CommandTpa;
import com.seajee.opentpa.commands.CommandTpaCancel;
import com.seajee.opentpa.commands.CommandTpaDeny;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class OpenTpa extends JavaPlugin {

    public static HashMap<String, String> requests = new HashMap<>();

    private static OpenTpa instance;

    public static OpenTpa getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Register commands
        new CommandTpa("tpa", "/tpa <player>");
        new CommandTpaCancel("tpacancel", "/tpacancel");
        new CommandTpAccept("tpaccept", "/tpaccept <request>");
        new CommandTpaDeny("tpadeny", "/tpadeny <request>");
    }

    @Override
    public void onDisable() {
    }
}
