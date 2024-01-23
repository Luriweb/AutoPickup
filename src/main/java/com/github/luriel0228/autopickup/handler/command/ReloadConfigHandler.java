package com.github.luriel0228.autopickup.handler.command;

import com.github.luriel0228.autopickup.AutoPickup;

public class ReloadConfigHandler {

    private static ReloadConfigHandler instance;

    private ReloadConfigHandler() {
    }

    public static ReloadConfigHandler getInstance() {
        if (instance == null) instance = new ReloadConfigHandler();
        return instance;
    }

    public void handleReloadConfig() {
        AutoPickup.getInstance().reloadConfig();
    }

}
