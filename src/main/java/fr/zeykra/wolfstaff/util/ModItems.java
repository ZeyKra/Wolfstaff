package fr.zeykra.wolfstaff.util;

import fr.zeykra.wolfstaff.WolfStaff;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModItems implements Listener {

    private static YmlFileUtil config = WolfStaff.config;
    private static YmlFileUtil lang = WolfStaff.lang;


    public static ItemStack invsee() {
        ItemStack item = new ItemStack(Material.CHEST, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ColorUtil.format(lang.getString("item-invsee-name")));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack freeze() {
        ItemStack item = new ItemStack(Material.ICE, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ColorUtil.format(lang.getString("item-freeze-name")));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack minerais() {
        ItemStack item = new ItemStack(Material.HOPPER, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ColorUtil.format(lang.getString("item-minerais-name")));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack informations() {
        ItemStack item = new ItemStack(Material.WATCH, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ColorUtil.format(lang.getString("item-informations-name")));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack kill() {
        ItemStack item = new ItemStack(Material.REDSTONE_TORCH_ON, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ColorUtil.format(lang.getString("item-kill-name")));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack vanishDesactivated() {
        ItemStack item = new ItemStack(351, 1, (byte) 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ColorUtil.format(lang.getString("item-vanish-desactivated-name")));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack vanishActivated() {
        ItemStack item = new ItemStack(351, 1, (byte) 10); //would be purple dye
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ColorUtil.format(lang.getString("item-vanish-activated-name")));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack quit() {
        ItemStack item = new ItemStack(Material.BARRIER); //would be purple dye
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ColorUtil.format(lang.getString("item-quit-name")));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack randomTP() {
        ItemStack item = new ItemStack(Material.DAYLIGHT_DETECTOR); //would be purple dye
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ColorUtil.format(lang.getString("item-randomtp-name")));
        item.setItemMeta(im);
        return item;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(!e.getItemInHand().hasItemMeta()) { return; }
        if(e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(invsee().getItemMeta().getDisplayName()) ||
                e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(freeze().getItemMeta().getDisplayName()) ||
                e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(minerais().getItemMeta().getDisplayName()) ||
                e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(kill().getItemMeta().getDisplayName()) ||
                e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(randomTP().getItemMeta().getDisplayName()) ||
                e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(quit().getItemMeta().getDisplayName())
        ) {
            e.setCancelled(true);
        }
    }

}
