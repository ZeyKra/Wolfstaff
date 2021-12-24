package fr.zeykra.wolfstaff.commands;

import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.gui.GuiSanctions;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSanction implements CommandExecutor {

    YmlFileUtil config = WolfStaff.config;
    YmlFileUtil lang = WolfStaff.lang;
    WolfStaff instance = WolfStaff.Instance;
    @Override

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) { sender.sendMessage("Console command");}
        if(args.length < 1) { sender.sendMessage(lang.getString("message-precise-player"));}
        Player player = (Player) sender;
        if(!instance.getServer().getPlayer(args[0]).isOnline()) {
            sender.sendMessage(lang.getString("message-precise-player"));
        }
        Player target = instance.getServer().getPlayer(args[0]);
        ModCore.setSanctionedPlayer(player, target);
        GuiSanctions.open("gameplay", player, target);
        return false;
    }
}
