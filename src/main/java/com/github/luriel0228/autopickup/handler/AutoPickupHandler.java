package com.github.luriel0228.autopickup.handler;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.List;

public class AutoPickupHandler {

    private static AutoPickupHandler instance;

    private AutoPickupHandler() {
    }

    public static AutoPickupHandler getInstance() {
        if (instance == null) instance = new AutoPickupHandler();
        return instance;
    }

    public void handleAutoPickup(Player player, List<Item> dropItems) {
        for (Item item : dropItems) {
            player.getInventory().addItem(item.getItemStack());
        }
    }
}
