package com.seajee.opentpa;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Message {

    public static TextComponent info(String message) {
        return header()
                .append(Component.text(message)
                .color(NamedTextColor.WHITE));
    }

    public static TextComponent confirm(String message) {
        return header()
                .append(Component.text(message)
                .color(NamedTextColor.GREEN));
    }

    public static TextComponent warn(String message) {
        return header()
                .append(Component.text(message)
                .color(NamedTextColor.YELLOW));
    }

    public static TextComponent error(String message) {
        return header()
                .append(Component.text(message)
                .color(NamedTextColor.RED));
    }

    private static TextComponent header() {
        return Component
                .text("[")
                .color(NamedTextColor.GOLD)
                .append(Component.text("OpenTpa")
                .color(NamedTextColor.WHITE)
                .append(Component.text("] ")
                .color(NamedTextColor.GOLD)));
    }
}
