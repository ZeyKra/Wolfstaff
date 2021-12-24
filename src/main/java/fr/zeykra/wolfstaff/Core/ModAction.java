package fr.zeykra.wolfstaff.Core;

import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.event.VanishEvent;
import fr.zeykra.wolfstaff.gui.GuiFreeze;
import fr.zeykra.wolfstaff.util.InventorySaverUtil;
import fr.zeykra.wolfstaff.util.JsonMessage;
import fr.zeykra.wolfstaff.util.LangValues;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
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
        if (isFrozen(target)) { // si il est freeze tu l'unfreeze
            FrozenPlayers.remove(target.getUniqueId());
            target.setFlySpeed(0.2f);
            target.setWalkSpeed(0.2f);
            target.removePotionEffect(PotionEffectType.JUMP);
            player.sendMessage(LangValues.format(lang.getString("message-unfreeze-someone"), target));
            target.sendMessage(lang.getString("message-unfreeze"));
            target.closeInventory();
            return;
        }
        FrozenPlayers.add(target.getUniqueId());
        target.setWalkSpeed(0);
        target.setFlySpeed(0);
        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 102, false, false));
        player.sendMessage(LangValues.format(lang.getString("message-freeze-someone"), target));
        target.sendMessage(lang.getString("message-freeze"));
        GuiFreeze.open(target);
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

            InventorySaverUtil.restorePlayerInvFromData(player);
            InventorySaverUtil.deletePlayerInvData(player);

            player.sendMessage(lang.getString("message-quitmod"));
            VanishEvent.unVanish(player);
            return;
        }
        InventorySaverUtil.savePlayerInventory(player);
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

            InventorySaverUtil.restorePlayerInvFromData(player);
            InventorySaverUtil.deletePlayerInvData(player);

            player.sendMessage(lang.getString("message-quitmod"));
            VanishEvent.unVanish(player);
            return;
        }
        InventorySaverUtil.savePlayerInventory(player);
        ModCore.addMod(player);
        player.getInventory().clear();
        player.getInventory().setContents(ModInventory.modInv().getContents());
        player.sendMessage(lang.getString("message-joinmod"));
        VanishEvent.vanish(player);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
    }

    public static void randomTp(Player player) {
        ArrayList<Player> playerList = new ArrayList<>();

        for(Player lPlayer : Bukkit.getOnlinePlayers()) {
            if(lPlayer.getUniqueId() == player.getUniqueId()) continue;
            playerList.add(lPlayer);
        }
        if(playerList.size() <= 0) {
            player.sendMessage(lang.getString("message-notenought-player"));
            return;
        }

        int randomInt = new Random().nextInt(playerList.size());
        Player target = (Player) playerList.get(randomInt);

        player.teleport(target);
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
        String msg = LangValues.format(lang.getString("message-confirm-kill"), target);
        new JsonMessage().append(msg)
                .setHoverAsTooltip("Cliquer ici pour tuer ce joueur")
                .setClickAsExecuteCmd("/wolfstaff kill " + target.getName() + " " + genUUID.toString())
                .save()
                .send(killer);
    }

    //explicite
    public static boolean isFrozen(Player player) {
        return FrozenPlayers.contains(player.getUniqueId());
    }

    //Fonction qui permet de remettre le statut de mod après un realod par exemple
    public static void reSetupModState() {
        String path = instance.getDataFolder().getPath() + "/inventory/";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles == null || listOfFiles.length <= 0) return;
        for (File file : listOfFiles) {
            UUID uuid = UUID.fromString(file.getName().replace(".yml", ""));
            //System.out.println(file.getName());
            if(instance.getServer().getPlayer(uuid) == null) return;
            if(!instance.getServer().getPlayer(uuid).isOnline()) return;
            Player player = instance.getServer().getPlayer(uuid);

            ModCore.addMod(player);
            //System.out.println("re mod :" +  player.getName());
        }
    }


}
