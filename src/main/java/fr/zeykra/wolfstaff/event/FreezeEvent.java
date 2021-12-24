package fr.zeykra.wolfstaff.event;

import fr.zeykra.wolfstaff.Core.ModAction;
import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.gui.GuiFreeze;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

public class FreezeEvent implements Listener {

    WolfStaff instance = WolfStaff.Instance;
    //static YmlFileUtil config = WolfStaff.config;
    //YmlFileUtil lang = WolfStaff.lang;

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if(!ModAction.isFrozen(player)) { return; }
        if(e.getInventory().getType() != InventoryType.DISPENSER ) return;

        new BukkitRunnable() {

            @Override
            public void run() {
                GuiFreeze.open(player);
            }
        }.runTaskLater(instance, 2);


    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(!ModAction.isFrozen(player)) { return; }

        e.setCancelled(true);
    }



}
