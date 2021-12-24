package fr.zeykra.wolfstaff.commands;

import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.gui.GuiInformation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCGI implements CommandExecutor {

    WolfStaff instance = WolfStaff.Instance;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Commande uniquement executable par le joueur");
        }
        Player player = (Player) sender;
        Player viewed = instance.getServer().getPlayer(args[0]);
        GuiInformation.open(player, viewed);
        ModCore.addViewer(viewed, player);
        return false;
    }
}
