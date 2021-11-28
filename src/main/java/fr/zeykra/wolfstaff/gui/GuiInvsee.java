package fr.zeykra.wolfstaff.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuiInvsee {

    public static void open(Player viewer, Player target){
        viewer.openInventory((Inventory)target.getInventory());
    }

}
