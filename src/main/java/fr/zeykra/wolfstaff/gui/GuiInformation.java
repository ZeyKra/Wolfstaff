package fr.zeykra.wolfstaff.gui;

import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.enums.DynamicItemEnum;
import fr.zeykra.wolfstaff.util.TimeUtil;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GuiInformation implements Listener {

    private static WolfStaff instance = WolfStaff.Instance;
    private static YmlFileUtil config = WolfStaff.config;


    public static void open(Player viewer, Player target) {
        Inventory inv = Bukkit.createInventory(null, 6 * 9, "CGI+ " + target.getName());

        inv.setItem(1, dynamicItem(target.getPlayer(), DynamicItemEnum.ITEM_HAND, viewer));
        inv.setItem(10, dynamicItem(target, DynamicItemEnum.HELMET, viewer));
        inv.setItem(19, dynamicItem(target, DynamicItemEnum.CHESTPLATE, viewer));
        inv.setItem(28, dynamicItem(target, DynamicItemEnum.LEGGINGS, viewer));

        inv.setItem(13, dynamicItem(target, DynamicItemEnum.HEURES, viewer));
        inv.setItem(14, dynamicItem(target, DynamicItemEnum.SANCTION, viewer));
        inv.setItem(15, dynamicItem(target, DynamicItemEnum.SOON, viewer));

        inv.setItem(37, dynamicItem(target, DynamicItemEnum.BOOTS, viewer));
        inv.setItem(40, dynamicItem(target, DynamicItemEnum.IP, viewer));
        inv.setItem(41, dynamicItem(target, DynamicItemEnum.LOCATION, viewer));
        inv.setItem(42, dynamicItem(target, DynamicItemEnum.PING, viewer));

        viewer.openInventory(inv);
        ModCore.setCurrentGui(viewer, "information");
    }


    private static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack dynamicItem(Player player, DynamicItemEnum dItem, final Player viewer) {
        ItemStack dynamicItem;
        ItemMeta meta;
        switch (dItem) {
            case HELMET:
                dynamicItem = player.getInventory().getHelmet();
                if(dynamicItem == null) {
                    dynamicItem = new ItemStack(Material.AIR);
                } else { dynamicItem = player.getInventory().getHelmet().clone(); }
                break;
            case CHESTPLATE:
                dynamicItem = player.getInventory().getChestplate();
                if(dynamicItem == null) {
                    dynamicItem = new ItemStack(Material.AIR);
                } else { dynamicItem = player.getInventory().getChestplate().clone(); }
                break;
            case LEGGINGS:
                dynamicItem = player.getInventory().getLeggings();
                if(dynamicItem == null) {
                    dynamicItem = new ItemStack(Material.AIR);
                } else { dynamicItem = player.getInventory().getLeggings().clone(); }
                break;
            case BOOTS:
                dynamicItem = player.getInventory().getBoots();
                if(dynamicItem == null) {
                    dynamicItem = new ItemStack(Material.AIR);
                } else { dynamicItem = player.getInventory().getBoots().clone(); }
                break;
            case ITEM_HAND:
                dynamicItem = player.getInventory().getItemInHand();
                if(dynamicItem == null) {
                    dynamicItem = new ItemStack(Material.AIR);
                } else { dynamicItem = player.getInventory().getItemInHand().clone(); }
                break;
            case PING:
                dynamicItem = new ItemStack(Material.STAINED_CLAY, 1, (byte) 4);
                meta = dynamicItem.getItemMeta();
                EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
                meta.setDisplayName("§6Ping: " + nmsPlayer.ping + "ms");
                dynamicItem.setItemMeta(meta);
                break;
            case IP:
                dynamicItem = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
                meta = dynamicItem.getItemMeta();
                meta.setDisplayName("§cIP: ***.***.***.***");
                if(viewer.hasPermission(config.getString("permission-ipsee"))) {
                    String ip = player.getAddress().getAddress().toString().replaceAll("/", "");
                    meta.setDisplayName("§cIP: " + ip);
                }
                dynamicItem.setItemMeta(meta);
                break;
            case LOCATION:
                dynamicItem = new ItemStack(Material.STAINED_CLAY, 1, (byte) 3);
                meta = dynamicItem.getItemMeta();
                meta.setDisplayName("§bx: " + player.getLocation().getBlockX() +
                        " y: " + player.getLocation().getBlockY() +
                        " z: " + player.getLocation().getBlockZ()
                );
                dynamicItem.setItemMeta(meta);
                break;
            case HEURES:
                dynamicItem = new ItemStack(Material.WATCH, 1);
                meta = dynamicItem.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + TimeUtil.timeFormat(player.getPlayerTime()));
                dynamicItem.setItemMeta(meta);
                break;
            case SANCTION:
                dynamicItem = new ItemStack(Material.ANVIL, 1);
                meta = dynamicItem.getItemMeta();
                meta.setDisplayName("§7Sanctioner le joueur");
                dynamicItem.setItemMeta(meta);
                break;
            default:
                dynamicItem = new ItemStack(Material.AIR);
                break;
        }
        if(dynamicItem.getType() == Material.AIR) {
            final ItemStack item = new ItemStack(Material.BARRIER, 1);
            meta = item.getItemMeta();

            meta.setDisplayName("§cRien");
            item.setItemMeta(meta);
            return item;
        }
        final ItemStack item = dynamicItem;
        meta = dynamicItem.getItemMeta();

        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(!e.getInventory().getName().contains("CGI+")) return;
        Player viewed = instance.getServer().getPlayer(e.getInventory().getName().replace("CGI+ ", ""));
        ModCore.removeViewer(viewed, (Player) e.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if(!ModCore.hasCurrentGui((Player) e.getWhoClicked())) { return; }
        if (!ModCore.getCurrentGuiName((Player) e.getWhoClicked()).equalsIgnoreCase("information")) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();
        Player target = instance.getServer().getPlayer(e.getInventory().getName().replace("CGI+ ", ""));
        if (e.getSlot() == 14) {
            p.performCommand("sanction " + target.getName());
        }
    }

    // Cancel dragging in our inventory

    /*
            for (Map.Entry<String, Integer> e : map.entrySet())
            System.out.println("Key: " + e.getKey()
            + " Value: " + e.getValue()); */

    //event dynamic


}
