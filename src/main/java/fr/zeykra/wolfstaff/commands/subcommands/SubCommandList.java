package fr.zeykra.wolfstaff.commands.subcommands;

import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.commands.SubCommand;
import fr.zeykra.wolfstaff.util.JsonMessage;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SubCommandList extends SubCommand {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "voir les staff en lignes";
    }

    @Override
    public String getSyntax() {
        return "/wolfstaff list";
    }

    WolfStaff instance = WolfStaff.Instance;
    YmlFileUtil config = WolfStaff.config;
    YmlFileUtil lang = WolfStaff.lang;

    @Override
    public void perform(Player player, String[] args) {
        if(!player.hasPermission(config.getString("permission-list")) || !player.hasPermission(config.getString("permission-star"))) {
            player.sendMessage(lang.getString("message-nopermission"));
            return;
        }

        player.sendMessage("§8()§8§m------------------------§8()");
        for (UUID lUUID : ModCore.onlineStaffList) {
            Player lPlayer = instance.getServer().getPlayer(lUUID);
            String color = "§6";
            String toolTip = "§6Ce joueur n'est pas en /mod";
            if(ModCore.isMod(lPlayer)) {
                toolTip = "§aCe joueur est en /mod";
                color = "§a";
            }
            String msg = "§7- " + lPlayer.getDisplayName() + color + " ▉";
            new JsonMessage().append(msg)
                    .setHoverAsTooltip(toolTip)
                    .save()
                    .send(player);
        }
        player.sendMessage("§8()§8§m------------------------§8()");
    }
}
