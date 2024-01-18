package com.github.luriel0228.autopickup.listener;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

import java.util.List;

public class AutoPickupListener implements Listener {

    @EventHandler
    public void onPlayerBlockDropItemEvent(BlockDropItemEvent event) {
        Player player = event.getPlayer();
        List<Item> dropItems = event.getItems();
        if (player.hasPermission("autopickup.pickup")) {
            if (dropItems.isEmpty()) return;
            event.setCancelled(true);
            for (Item item : dropItems) {
                player.getInventory().addItem(item.getItemStack());
            }
        }
    }

}
