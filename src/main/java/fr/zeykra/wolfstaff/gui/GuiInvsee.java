package fr.zeykra.wolfstaff.gui;

import fr.zeykra.wolfstaff.Core.ModCore;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuiInvsee {

    public static void open(Player viewer, Player target){
        ModCore.setCurrentGui(viewer, "invsee");
        viewer.openInventory((Inventory)target.getInventory());
    }

}
