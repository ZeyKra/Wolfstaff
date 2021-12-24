package fr.zeykra.wolfstaff.event;

import fr.zeykra.wolfstaff.Core.GuiCore;
import fr.zeykra.wolfstaff.Core.ModCore;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class InventoryEvent implements Listener {

    //Tout les events qui permettent de mettre a jours le cgi

    Material[] armor = {Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
            Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
            Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
            Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS,
            Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,
            Material.EGG, Material.SNOW_BALL
    };

    @EventHandler
    public void onSlotSwitch(PlayerItemHeldEvent e) {
        if(!ModCore.hasViewers(e.getPlayer())) { return; }
        for(Player tViewer : ModCore.getViewers(e.getPlayer())) {
            GuiCore.updateGui(tViewer, e.getPlayer());
        }
    }

    @EventHandler
    public void onInvetoryClick(InventoryClickEvent e) {
        if(e.getInventory() == null) { return; }
        if(e.getInventory().getType() != InventoryType.PLAYER) { return; }
        if(e.getInventory().getName() == null || e.getInventory().getName().isEmpty()) { return; }
        Player viewed = (Player) e.getWhoClicked();
        if(!ModCore.hasViewers(viewed)) { return; }
        for(Player tViewer : ModCore.getViewers(viewed)) {
            GuiCore.updateGui(tViewer, viewed);
        }
    }

    /*
    @EventHandler
    public void onPickup(InventoryPickupItemEvent e) {
        for(HumanEntity ue: e.getInventory().getViewers()) {
            if(!(ue instanceof Player)) { continue; }

            Player viewed = (Player) ue;
            if(!ModCore.hasViewers(viewed)) { continue; }
            for(Player tViewer : ModCore.getViewers(viewed)) {
                GuiCore.updateGui(tViewer, viewed);
            }
        }
    } */

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player viewed = e.getPlayer();
        if(!ModCore.hasViewers(viewed)) { return; }
        for(Player tViewer : ModCore.getViewers(viewed)) {
            GuiCore.updateGui(tViewer, viewed);
        }
    }

    @EventHandler
    public void newOnEquipEvent(PlayerInteractEvent e) {
        if(!ModCore.hasViewers(e.getPlayer())) { return; }
        if(e.getAction() == null || e.getItem() == null) { return; }
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Arrays.asList(armor).contains(e.getItem().getType())) {
                for(Player tViewer : ModCore.getViewers(e.getPlayer())) {
                    GuiCore.updateGui(tViewer, e.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if(!ModCore.hasViewers(e.getPlayer())) { return; }
        for(Player tViewer : ModCore.getViewers(e.getPlayer())) {
            GuiCore.updateGui(tViewer, e.getPlayer());
        }
    }

    //Global inventory event
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if(ModCore.hasCurrentGui(player)) {
            ModCore.removeCurrentGui(player);
        }
    }

}
