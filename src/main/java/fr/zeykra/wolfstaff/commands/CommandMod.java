package fr.zeykra.wolfstaff.commands;

import fr.zeykra.wolfstaff.Core.ModAction;
import fr.zeykra.wolfstaff.event.VanishEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMod implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("commande executable par des joueur");
        }
        Player player = (Player) sender;
        ModAction.mod(player);

        return false;
    }
}
