package fr.zeykra.wolfstaff.event;

import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ModEvent implements Listener {

    private static YmlFileUtil config = WolfStaff.config;
    private static WolfStaff instance = WolfStaff.Instance;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(e.getInventory().getType() != InventoryType.PLAYER) { return; }
        if(player.getGameMode() == GameMode.CREATIVE) { return; }
        if(player.hasPermission(config.getString("permission-staff")) && ModCore.isVanished(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) { return; }
        if(player.hasPermission(config.getString("permission-staff")) && ModCore.isVanished(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) { return; }
        if(player.hasPermission(config.getString("permission-staff")) && ModCore.isVanished(player)) {
            e.setCancelled(true);
        }
    }



}
