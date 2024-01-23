package com.github.luriel0228.autopickup.handler;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoSmeltHandler {

    private static AutoSmeltHandler instance;

    private final Map<Material, Material> oreToIngotMap;

    private AutoSmeltHandler() {
        this.oreToIngotMap = new HashMap<>();
        initializeOreToIngotMap();
    }

    public static AutoSmeltHandler getInstance() {
        if (instance == null) instance = new AutoSmeltHandler();
        return instance;
    }

    private void initializeOreToIngotMap() {
        oreToIngotMap.put(Material.RAW_IRON, Material.IRON_INGOT);
        oreToIngotMap.put(Material.RAW_GOLD, Material.GOLD_INGOT);
        oreToIngotMap.put(Material.RAW_COPPER, Material.COPPER_INGOT);
        oreToIngotMap.put(Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK);
        oreToIngotMap.put(Material.RAW_IRON_BLOCK, Material.IRON_BLOCK);
        oreToIngotMap.put(Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK);
        oreToIngotMap.put(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP);
    }

    public void handleAutoSmeltPickup(Player player, List<Item> dropItems) {
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

    public void handleAutoSmelt(List<Item> dropItems, Block block) {
        for (Item item : dropItems) {
            Material oreType = item.getItemStack().getType();
            Material ingotType = oreToIngotMap.get(oreType);

            if (ingotType != null) {
                spawnDroppedItem(block, ingotType, item.getItemStack().getAmount());
            }
        }
    }

    private void spawnDroppedItem(Block block, Material material, int amount) {
        ItemStack item = new ItemStack(material, amount);
        block.getWorld().dropItemNaturally(block.getLocation(), item);
    }
}
