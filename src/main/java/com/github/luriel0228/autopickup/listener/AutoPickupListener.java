package com.github.luriel0228.autopickup.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<Material, Material> oreToIngotMap = new HashMap<>();

    public AutoPickupListener() {
        oreToIngotMap.put(Material.RAW_IRON, Material.IRON_INGOT);
        oreToIngotMap.put(Material.RAW_GOLD, Material.GOLD_INGOT);
        oreToIngotMap.put(Material.RAW_COPPER, Material.COPPER_INGOT);
        oreToIngotMap.put(Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK);
        oreToIngotMap.put(Material.RAW_IRON_BLOCK, Material.IRON_BLOCK);
        oreToIngotMap.put(Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK);
        oreToIngotMap.put(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP);
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
            handleAutoSmeltPickup(player, dropItems, block);
        } else if (itemLore.contains(ItemType.AUTO_SMELT.getLore())) {
            event.setCancelled(true);
            handleAutoSmelt(dropItems, block);
        } else if (itemLore.contains(ItemType.AUTO_PICKUP.getLore())) {
            event.setCancelled(true);
            handleAutoPickup(player, dropItems);
        }
    }

    private void handleAutoSmeltPickup(Player player, List<Item> dropItems, Block block) {
        for (Item item : dropItems) {
            Material oreType = item.getItemStack().getType();
            Material ingotType = oreToIngotMap.get(oreType);

            if (ingotType != null) {
                player.getInventory().addItem(new ItemStack(ingotType, item.getItemStack().getAmount()));
            } else {
                player.getInventory().addItem(item.getItemStack());
            }
        }
    }

    private void handleAutoSmelt(List<Item> dropItems, Block block) {
        for (Item item : dropItems) {
            Material oreType = item.getItemStack().getType();
            Material ingotType = oreToIngotMap.get(oreType);

            if (ingotType != null) {
                spawnDroppedItem(block, ingotType, item.getItemStack().getAmount());
            }
        }
    }

    private void handleAutoPickup(Player player, List<Item> dropItems) {
        for (Item item : dropItems) {
            player.getInventory().addItem(item.getItemStack());
        }
    }

    private void spawnDroppedItem(Block block, Material material, int amount) {
        ItemStack item = new ItemStack(material, amount);
        block.getWorld().dropItemNaturally(block.getLocation(), item);
    }
}