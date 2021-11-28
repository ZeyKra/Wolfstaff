package fr.zeykra.wolfstaff.Core;

import fr.zeykra.wolfstaff.util.ModItems;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class ModInventory {

    public static Inventory modInv() {
        Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);

        inv.setItem(0, ModItems.freeze());
        inv.setItem(1, ModItems.minerais());
        inv.setItem(2, ModItems.invsee());
        inv.setItem(3, ModItems.informations());
        inv.setItem(4, ModItems.kill());

        inv.setItem(7, ModItems.vanishActivated());
        inv.setItem(8, ModItems.quit());

        return inv;
    }

}
