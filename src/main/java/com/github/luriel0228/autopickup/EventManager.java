package com.github.luriel0228.autopickup;

import com.github.luriel0228.autopickup.commands.ReloadConfigCommand;
import com.github.luriel0228.autopickup.listener.AutoPickupListener;

import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class EventManager{

    public static void registerCommands() {
        Objects.requireNonNull(getServer().getPluginCommand("autopickup")).setExecutor(new ReloadConfigCommand());
    }

    public static void registerEvents() {
        getServer().getPluginManager().registerEvents(new AutoPickupListener(), AutoPickup.getInstance());
    }

}
