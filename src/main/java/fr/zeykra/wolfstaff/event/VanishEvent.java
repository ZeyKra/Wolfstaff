package fr.zeykra.wolfstaff.event;

import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class VanishEvent implements Listener {

    private static YmlFileUtil config = WolfStaff.config;
    private static YmlFileUtil lang = WolfStaff.lang;
    private static WolfStaff instance = WolfStaff.Instance;


    public static void vanishToggle(Player player) {
        if(player.hasPermission("wolfstaff.vanish")) {
            if(!ModCore.isVanished(player)) {
                player.sendMessage(lang.getString("message-vanish"));
                ModCore.addVanish(player);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 102, false, false));
                hideToAll(player);
            } else if(ModCore.isVanished(player)) {
                player.sendMessage(lang.getString("message-unvanish"));
                ModCore.removeVanish(player);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 102, false, false));
                revealToAll(player);
            }
        }
    }

    public static void vanish(Player player) {
        if(player.hasPermission("wolfstaff.vanish")) {
            if(!ModCore.isVanished(player)) {
                ModCore.addVanish(player);
                player.sendMessage(lang.getString("message-vanish"));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 102, false, false));
                hideToAll(player);
            }
        }
    }

    public static void unVanish(Player player) {
        if(player.hasPermission("wolfstaff.vanish")) {
            if(ModCore.isVanished(player)) {
                ModCore.removeVanish(player);
                player.sendMessage(lang.getString("message-unvanish"));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 102, false, false));
                revealToAll(player);
            }
        }
    }



    private static void hideToAll(Player playerToHide) {
        for(Player lPlayer : instance.getServer().getOnlinePlayers()) {
            lPlayer.hidePlayer(playerToHide);
        }
    }

    private static void revealToAll(Player playerToShow) {
        for(Player lPlayer : instance.getServer().getOnlinePlayers()) {
            lPlayer.showPlayer(playerToShow);
        }
    }

    //Manage des joeur vanish
    @EventHandler //hide des mod déjà vanish lors de la connexion d'un joueur
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        for(UUID lUUID : ModCore.vanishList) {
            Player vanished = instance.getServer().getPlayer(lUUID);
            player.hidePlayer(vanished);
        }
    }

    @EventHandler // hide d'un modo déjà vanish lorsque il se reco alors qu'il était en vniahs
    public void onVanishedJoin(PlayerJoinEvent e) {
        Player vanished = e.getPlayer();
        if(ModCore.isVanished(vanished)) {
            hideToAll(vanished);
            vanished.sendMessage(lang.getString("message-join-vanished"));
        }
    }



}
