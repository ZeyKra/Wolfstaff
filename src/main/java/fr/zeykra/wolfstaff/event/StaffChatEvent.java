package fr.zeykra.wolfstaff.event;

import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.util.LangValues;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.UUID;

public class StaffChatEvent implements Listener {

    YmlFileUtil config = WolfStaff.config;
    YmlFileUtil lang = WolfStaff.lang;
    WolfStaff instance = WolfStaff.Instance;

    public void staffChat(String sender, String message) {
        if(ModCore.onlineStaffList.size() <= 0) { return; }
        String res = "";
        for(UUID lUUID : ModCore.onlineStaffList) {
            Player player = instance.getServer().getPlayer(lUUID);

            res = LangValues.customFormat(config.getString("staffchat-format"), "{PLAYER}", sender);
            res = LangValues.customFormat(res, "{MESSAGE}", message);

            player.sendMessage(res);
        }
        if(config.getBoolean("staffchat-message-in-console")) {
            instance.getServer().getConsoleSender().sendMessage(res);
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        if(e.getMessage() != null || e.getPlayer().hasPermission(config.getString("premission-staffchat"))) { return; }
        if(e.getMessage().startsWith("!")) {
            e.setCancelled(true);
            staffChat(e.getPlayer().getDisplayName(), e.getMessage());
        }
    }


}
