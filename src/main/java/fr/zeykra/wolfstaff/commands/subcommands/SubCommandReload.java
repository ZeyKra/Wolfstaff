package fr.zeykra.wolfstaff.commands.subcommands;

import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.commands.SubCommand;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.entity.Player;

public class SubCommandReload extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "reload la configuration et le lang";
    }

    @Override
    public String getSyntax() {
        return "/wolfstaff reload";
    }

    WolfStaff instance = WolfStaff.Instance;
    YmlFileUtil config = WolfStaff.config;
    YmlFileUtil lang = WolfStaff.lang;

    @Override
    public void perform(Player player, String[] args) {
        if(!player.hasPermission(config.getString("permission-reload"))) {
            player.sendMessage(lang.getString("message-nopermission"));
            return;
        }
        instance.reloadCfg();
        player.sendMessage(lang.getString("message-reloaded"));

    }
}
