package fr.zeykra.wolfstaff.commands;

import com.sun.org.apache.xpath.internal.operations.Mod;
import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.event.VanishEvent;
import fr.zeykra.wolfstaff.util.ModItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVanish implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("commande executable par des joueur");
        }
        Player player = (Player) sender;
        VanishEvent.vanishToggle(player);
        if(ModCore.isMod(player)) { //systeme qui permet d'update l'item du modérateur meme si il utilise la commande au lieu de l'item
            if(ModCore.isVanished(player)) {
                if(player.getInventory().getItem(7).isSimilar(ModItems.vanishDesactivated())) { // si l'item dans son inv est pas celui qui devrait être
                    player.getInventory().setItem(7, ModItems.vanishActivated()); // on l'update
                }
            } else {
                if(player.getInventory().getItem(7).isSimilar(ModItems.vanishActivated())) {
                    player.getInventory().setItem(7, ModItems.vanishDesactivated());
                }
            }
        }
        return false;
    }
}
