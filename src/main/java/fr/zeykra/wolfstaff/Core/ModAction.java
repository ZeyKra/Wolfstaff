package fr.zeykra.wolfstaff.Core;

import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.event.VanishEvent;
import fr.zeykra.wolfstaff.util.JsonMessage;
import fr.zeykra.wolfstaff.util.LangValues;
import fr.zeykra.wolfstaff.util.ModItems;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class ModAction {

    static YmlFileUtil config = WolfStaff.config;
    static YmlFileUtil lang = WolfStaff.lang;
    static WolfStaff instance = WolfStaff.Instance;
    static Set<UUID> FrozenPlayers = new HashSet<>();

    private static Map<UUID, UUID> confirmKillSession = new HashMap<>();

    //Hashmap contenant les uuid generés pour eviter qu'un joueur cclique 100k fois sur le message de confirmation
    public static UUID getConfirmKillSession(UUID uuid) {
        return confirmKillSession.get(uuid);
    }
    public static boolean containConfirmKillSession(UUID uuid) {
        return confirmKillSession.containsKey(uuid);
    }
    public static void removeConfirmKillSession(UUID uuid) {
        confirmKillSession.remove(uuid);
    }

    //Freeze le joueur
    public static void freezePlayer(Player player, Player target) {
        if (FrozenPlayers.contains(target.getUniqueId())) {
            FrozenPlayers.remove(target.getUniqueId());
            target.setFlySpeed(0.2f);
            target.setWalkSpeed(0.2f);
            target.removePotionEffect(PotionEffectType.JUMP);
            player.sendMessage(LangValues.format(lang.getString("message-unfreeze-someone"), target));
            target.sendMessage(lang.getString("message-unfreeze"));

            return;
        }
        FrozenPlayers.add(target.getUniqueId());
        target.setWalkSpeed(0);
        target.setFlySpeed(0);
        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 102, false, false));
        player.sendMessage(LangValues.format(lang.getString("message-freeze-someone"), target));
        target.sendMessage(lang.getString("message-freeze"));
    }


    /*Fonction qui gère le /mod version toggle
     *
     *  Ajout du mod dans la liste des joueur en mod
     *  Vanish du joueur
     *  mise en place de l'inv etc..
     */

    public static void mod(Player player) {
        if (ModCore.isMod(player)) {
            ModCore.removeMod(player);
            player.getInventory().setContents(ModCore.getInventory(player));
            ModCore.removeInventory(player);
            player.sendMessage(lang.getString("message-quitmod"));
            VanishEvent.unVanish(player);
            return;
        }
        ModCore.addInventory(player);
        ModCore.addMod(player);
        player.getInventory().clear();
        player.getInventory().setContents(ModInventory.modInv().getContents());
        player.sendMessage(lang.getString("message-joinmod"));
        VanishEvent.vanish(player);
        player.updateInventory();
    }

    //Fonction qui gere le /mod mais version boolean
    public static void mod(Player player, boolean statut) {
        if (!statut) {
            player.getInventory().clear();
            ModCore.removeMod(player);
            player.getInventory().setContents(ModCore.getInventory(player));
            ModCore.removeInventory(player);
            player.sendMessage(lang.getString("message-quitmod"));
            VanishEvent.unVanish(player);
            player.updateInventory();
            return;
        }
        ModCore.addInventory(player);
        ModCore.addMod(player);
        player.getInventory().clear();
        player.getInventory().setContents(ModInventory.modInv().getContents());
        player.sendMessage(lang.getString("message-joinmod"));
        VanishEvent.vanish(player);
        player.updateInventory();
    }

    public static void confirmKill(Player target, Player killer) {
        if(!killer.hasPermission(config.getString("permission-kill"))) {
            killer.sendMessage(lang.getString("message-nopermission"));
            return;
        }
        // on enregistre l'uuid
        UUID genUUID = UUID.randomUUID();
        confirmKillSession.put(killer.getUniqueId(), genUUID);
        //envoie du message Json
        String msg = LangValues.format(lang.getString("message-confirmkill"), target);
        new JsonMessage().append(msg)
                .setHoverAsTooltip("Cliquer ici pour tuer ce joueur")
                .setClickAsExecuteCmd("/wolfstaff kill " + target.getName() + " " + genUUID.toString())
                .save()
                .send(killer);
    }


}
