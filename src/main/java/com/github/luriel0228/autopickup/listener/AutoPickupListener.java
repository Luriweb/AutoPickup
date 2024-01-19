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

    @EventHandler
    public void handleBlockDropItemEvent(BlockDropItemEvent event) {
        Player player = event.getPlayer();
        List<Item> dropItems = event.getItems();
        Block block = event.getBlock();

        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (dropItems.isEmpty()) return;

        ItemStack inv = player.getInventory().getItemInMainHand();
        if (inv.getType() == Material.AIR || inv.getItemMeta() == null || inv.getItemMeta().getLore() == null) return;

        List<String> itemLore = inv.getItemMeta().getLore();

        if (itemLore.toString().contains("자동굽기")) {
            event.setCancelled(true);
            handleAutoSmelt(dropItems, block);
        } else if (itemLore.toString().contains("자동줍기")) {
            event.setCancelled(true);
            handleAutoPickup(player, dropItems);
        }
    }

    private void handleAutoSmelt(List<Item> dropItems, Block block) {

        Map<Material, Material> oreToIngotMap = new HashMap<>();
        oreToIngotMap.put(Material.RAW_IRON, Material.IRON_INGOT); // 철 원석
        oreToIngotMap.put(Material.RAW_GOLD, Material.GOLD_INGOT); // 금 원석
        oreToIngotMap.put(Material.RAW_COPPER, Material.COPPER_INGOT); // 구리 원석

        oreToIngotMap.put(Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK); // 구리 원석 블록
        oreToIngotMap.put(Material.RAW_IRON_BLOCK, Material.IRON_BLOCK); // 철 원석 블록
        oreToIngotMap.put(Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK); // 금 원석 블록

        oreToIngotMap.put(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP); // 고대 잔해

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
