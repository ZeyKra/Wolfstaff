package fr.zeykra.wolfstaff.commands.subcommands;

import fr.zeykra.wolfstaff.Core.ModAction;
import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.commands.SubCommand;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import java.lang.annotation.Target;
import java.util.UUID;

public class SubCommandKill extends SubCommand {
    @Override
    public String getName() {
        return "kill";
    }

    @Override
    public String getDescription() {
        return "permet de tueur un joeur";
    }

    @Override
    public String getSyntax() {
        return "/wolfstaff kill <joueur>";
    }

    WolfStaff instance = WolfStaff.Instance;
    YmlFileUtil config = WolfStaff.config;
    YmlFileUtil lang = WolfStaff.lang;

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission(config.getString("permission-kill"))) {
            player.sendMessage(lang.getString("message-nopermission"));
            return;
        }


        //System pour pas qu'un joueur appuy 6600x sur le message de kill avec l'item KIll
        if (args.length >= 3) {
            UUID thatUUID = UUID.fromString(args[2]);
            //Si la session n'est pas egal a la session defini envoie du message
            System.out.println("/" + ModAction.getConfirmKillSession(player.getUniqueId()) + "/");
            System.out.println("/" + thatUUID + "/");
            if ( !ModAction.containConfirmKillSession(player.getUniqueId())||
                    !ModAction.getConfirmKillSession(player.getUniqueId()).toString().equals(thatUUID.toString())) {
                player.sendMessage(lang.getString("message-message-expired"));
                return;
            }
            //Sinon on fait expiré sa sessions
            ModAction.removeConfirmKillSession(player.getUniqueId());

        }

        //Mort du joueur etc

        Player target = instance.getServer().getPlayer(args[1]);
        target.setHealth(0.0D);
        if (config.getBoolean("send-killed-message")) {
            target.sendMessage(lang.getString("message-kill"));
        }


    }
}
