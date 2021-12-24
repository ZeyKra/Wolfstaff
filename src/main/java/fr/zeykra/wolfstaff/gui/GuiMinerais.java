package fr.zeykra.wolfstaff.gui;

import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.enums.DynamicItemEnum;
import fr.zeykra.wolfstaff.util.ColorUtil;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import java.util.ArrayList;
import java.util.Arrays;

public class GuiMinerais implements Listener {

    static YmlFileUtil lang = WolfStaff.lang;

    public static void open(Player viewer, Player target) {
        Inventory inv = Bukkit.createInventory(null, 5 * 9, "Minerais " + target.getName());

        if(target.getItemInHand().getType() != null) {
            inv.setItem(9, generateItem(target, Material.COAL)); //items
            inv.setItem(10, generateItem(target, Material.IRON_INGOT));
            inv.setItem(11, generateItem(target, Material.GOLD_INGOT));
            inv.setItem(12, generateItem(target, Material.INK_SACK, 4)); // Lapiz
            inv.setItem(13, generateItem(target, Material.REDSTONE));
            inv.setItem(14, generateItem(target, Material.DIAMOND));
            inv.setItem(15, generateItem(target, Material.EMERALD));
            inv.setItem(16, generateItem(target, Material.QUARTZ));
            inv.setItem(17, generateItem(target, Material.MONSTER_EGG,50));
            //ore
            inv.setItem(18, generateItem(target, Material.COAL_ORE));
            inv.setItem(19, generateItem(target, Material.IRON_ORE));
            inv.setItem(20, generateItem(target, Material.GOLD_ORE));
            inv.setItem(21, generateItem(target, Material.LAPIS_ORE));
            inv.setItem(22, generateItem(target, Material.REDSTONE_ORE));
            inv.setItem(23, generateItem(target, Material.DIAMOND_ORE));
            inv.setItem(24, generateItem(target, Material.EMERALD_ORE));
            inv.setItem(25, generateItem(target, Material.QUARTZ_ORE));
            inv.setItem(26, generateItem(target, Material.OBSIDIAN));
            //blocks
            inv.setItem(27, generateItem(target, Material.COAL_BLOCK));
            inv.setItem(28, generateItem(target, Material.IRON_BLOCK));
            inv.setItem(29, generateItem(target, Material.GOLD_BLOCK));
            inv.setItem(30, generateItem(target, Material.LAPIS_BLOCK));
            inv.setItem(31, generateItem(target, Material.REDSTONE_BLOCK));
            inv.setItem(32, generateItem(target, Material.DIAMOND_BLOCK));
            inv.setItem(33, generateItem(target, Material.EMERALD_BLOCK));
            inv.setItem(34, generateItem(target, Material.QUARTZ_BLOCK));
            //inv.setItem(26, generateItem(target, Material.MONSTER_EGG));
        }

        viewer.openInventory(inv);
        ModCore.setCurrentGui(viewer, "minerais");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getName().contains("Minerais") && ModCore.isMod((Player) e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }

    private static ItemStack generateItem(Player player, Material mat) {
        int number = count(player, mat);
        ItemStack item = new ItemStack(mat, number);
        ItemMeta meta = item.getItemMeta();

        String name = ColorUtil.format(lang.getString("minerais-" + mat.name().replace("_", "-")));

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList("§f- " + number));

        item.setItemMeta(meta);

        return item;
    }
    private static ItemStack generateItem(Player player, Material mat, int id) {
        int number = count(player, mat, id);
        ItemStack item = new ItemStack(mat, number, (byte) id);
        ItemMeta meta = item.getItemMeta();

        String name = ColorUtil.format(lang.getString("minerais-" + mat.name().replace("_", "-")));

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList("§f- " + number));

        item.setItemMeta(meta);

        return item;
    }

    private static int count(Player player, Material itemType) {
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || slot.getType() != itemType)
                continue;
            amount += slot.getAmount();
        }
        return amount;
    }
    private static int count(Player player, Material itemType, int id) {
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || slot.getType() != itemType || slot.getData().getData() != (byte) id)
                continue;
            amount += slot.getAmount();
        }
        return amount;
    }

    private static int getAmount(Player player, ItemStack item) {
        if (item == null)
            return 0;
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || !slot.isSimilar(item))
                continue;
            amount += slot.getAmount();
        }
        return amount;
    }

}
