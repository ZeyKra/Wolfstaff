package fr.zeykra.wolfstaff.event;

import com.sun.org.apache.xpath.internal.operations.Mod;
import fr.zeykra.wolfstaff.Core.ModAction;
import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.gui.GuiInformation;
import fr.zeykra.wolfstaff.gui.GuiInvsee;
import fr.zeykra.wolfstaff.gui.GuiMinerais;
import fr.zeykra.wolfstaff.util.ModItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemEvent implements Listener {

    //Tout les event de clique droit avec des items de modérations

    //Item qui ont besion de cliquer sur un joueur
    @EventHandler
    public void onRightClickPlayer(PlayerInteractEntityEvent e) {
        if(!(e.getRightClicked() instanceof Player)) { return; }
        Player player = e.getPlayer();
        if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) { return;}
        if(!ModCore.isMod(player)) { return; }

        ItemStack item = player.getItemInHand();
        Player target = (Player) e.getRightClicked();
        if(item.isSimilar(ModItems.freeze())) {
            ModAction.freezePlayer(player, target);
        }
        if(item.isSimilar(ModItems.kill())) {
            ModAction.confirmKill(target, player);
        }
        if(item.isSimilar(ModItems.informations())) {
            GuiInformation.open(player, target);
            ModCore.addViewer(target, player);
        }
        if(item.isSimilar(ModItems.invsee())) {
            GuiInvsee.open(player, target);
        }
        if(item.isSimilar(ModItems.minerais())) {
            GuiMinerais.open(player, target);
        }
        e.setCancelled(true);
    }

    //Items qui n'ont pas besion d'être cliquer sur un joueur
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(e.getItem() == null || e.getItem().getType() == Material.AIR) { return; }
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            Player player = e.getPlayer();
            if(item.isSimilar(ModItems.vanishActivated())) {
                VanishEvent.unVanish(e.getPlayer());
                player.getInventory().setItem(7, ModItems.vanishDesactivated());
            } else if(item.isSimilar(ModItems.vanishDesactivated())) {
                VanishEvent.vanish(e.getPlayer());
                player.getInventory().setItem(7, ModItems.vanishActivated());
            } else if(item.isSimilar(ModItems.quit())) {
                ModAction.mod(e.getPlayer(), false);
                VanishEvent.unVanish(e.getPlayer());
            }
        }
        return;
    }

}
