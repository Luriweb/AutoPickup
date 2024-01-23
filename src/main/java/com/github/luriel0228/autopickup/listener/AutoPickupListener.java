package com.github.luriel0228.autopickup.listener;

import com.github.luriel0228.autopickup.handler.AutoPickupHandler;
import com.github.luriel0228.autopickup.handler.AutoSmeltHandler;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AutoPickupListener implements Listener {

    private enum ItemType {
        AUTO_SMELT("자동굽기"),
        AUTO_PICKUP("자동줍기");

        private final String lore;

        ItemType(String lore) {
            this.lore = lore;
        }

        public String getLore() {
            return lore;
        }
    }

    private final AutoSmeltHandler autoSmeltHandler;
    private final AutoPickupHandler autoPickupHandler;

    public AutoPickupListener() {
        this.autoSmeltHandler = AutoSmeltHandler.getInstance();
        this.autoPickupHandler = AutoPickupHandler.getInstance();
    }

    @EventHandler
    public void handleBlockDropItemEvent(BlockDropItemEvent event) {
        Player player = event.getPlayer();
        List<Item> dropItems = event.getItems();
        Block block = event.getBlock();

        if (player.getGameMode() == GameMode.CREATIVE || dropItems.isEmpty()) {
            return;
        }

        ItemStack inv = player.getInventory().getItemInMainHand();
        if (inv.getType() == Material.AIR || inv.getItemMeta() == null || inv.getItemMeta().getLore() == null) {
            return;
        }

        List<String> itemLore = inv.getItemMeta().getLore();

        if (itemLore.contains(ItemType.AUTO_SMELT.getLore()) && itemLore.contains(ItemType.AUTO_PICKUP.getLore())) {
            event.setCancelled(true);
            autoSmeltHandler.handleAutoSmeltPickup(player, dropItems);
        } else if (itemLore.contains(ItemType.AUTO_SMELT.getLore())) {
            event.setCancelled(true);
            autoSmeltHandler.handleAutoSmelt(dropItems, block);
        } else if (itemLore.contains(ItemType.AUTO_PICKUP.getLore())) {
            event.setCancelled(true);
            autoPickupHandler.handleAutoPickup(player, dropItems);
        }
    }
}
