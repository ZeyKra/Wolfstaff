package fr.zeykra.wolfstaff.gui;

import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.util.ColorUtil;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GuiFreeze {

    WolfStaff instance = WolfStaff.Instance;
    static YmlFileUtil config = WolfStaff.config;
    YmlFileUtil lang = WolfStaff.lang;

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.DISPENSER, "§bVous êtes Freeze");

        inv.setItem(4, freezeItem());

        player.openInventory(inv);
    }

    static private ItemStack freezeItem() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.format(config.getString("guifreeze-item-name")));
        String[] lore = ColorUtil.format(config.getString("guifreeze-item-lore")).split("\\|\\|");
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);
        return item;
    }

    static protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

}
