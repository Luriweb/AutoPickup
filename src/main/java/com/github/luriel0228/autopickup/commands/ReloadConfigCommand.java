package com.github.luriel0228.autopickup.commands;

import com.github.luriel0228.autopickup.handler.command.ReloadConfigHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadConfigCommand implements CommandExecutor {

    private final ReloadConfigHandler reloadConfigHandler;

    public ReloadConfigCommand() {
        this.reloadConfigHandler = ReloadConfigHandler.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if (!player.isOp()) return true;

        reloadConfigHandler.handleReloadConfig();
        player.sendMessage("AutoPickup config reloaded.");

        return true;
    }

}
