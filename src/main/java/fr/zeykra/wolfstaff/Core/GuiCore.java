package fr.zeykra.wolfstaff.Core;

import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.enums.DynamicItemEnum;
import fr.zeykra.wolfstaff.gui.GuiInformation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class GuiCore {

    static WolfStaff instance = WolfStaff.Instance;

    public static void updateGui(Player player, Player viewed) {
        new BukkitRunnable() {

            @Override
            public void run() {
                if(player.getOpenInventory().getTopInventory() == null ) { return; }
                String name = player.getOpenInventory().getTopInventory().getName();
                Inventory inv = player.getOpenInventory().getTopInventory();

                if(!name.contains("CGI+")) { return; }

                inv.setItem(1, GuiInformation.dynamicItem(viewed, DynamicItemEnum.ITEM_HAND, player));
                inv.setItem(10, GuiInformation.dynamicItem(viewed, DynamicItemEnum.HELMET, player));
                inv.setItem(19, GuiInformation.dynamicItem(viewed, DynamicItemEnum.CHESTPLATE, player));
                inv.setItem(28, GuiInformation.dynamicItem(viewed, DynamicItemEnum.LEGGINGS, player));
                inv.setItem(37, GuiInformation.dynamicItem(viewed, DynamicItemEnum.BOOTS, player));
                inv.setItem(40, GuiInformation.dynamicItem(viewed, DynamicItemEnum.IP, player));
                inv.setItem(41, GuiInformation.dynamicItem(viewed, DynamicItemEnum.LOCATION, player));
                inv.setItem(42, GuiInformation.dynamicItem(viewed, DynamicItemEnum.PING, player));

                player.updateInventory();
            }
        }.runTaskLater(instance, 2);



    }
}
