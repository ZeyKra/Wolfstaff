package fr.zeykra.wolfstaff.commands;

import fr.zeykra.wolfstaff.event.StaffChatEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStaffchat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StaffChatEvent staffChatEvent = new StaffChatEvent();

        StringBuilder stringBuilder = new StringBuilder();
        for(String x : args) {
            stringBuilder.append(x);
        }

        staffChatEvent.staffChat(sender.getName(), stringBuilder.toString());
        return false;
    }
}
