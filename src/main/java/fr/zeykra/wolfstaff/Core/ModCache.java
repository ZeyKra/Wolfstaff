package fr.zeykra.wolfstaff.Core;

import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.util.BukkitSerialization;
import fr.zeykra.wolfstaff.util.InventorySaverUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class ModCache implements Listener {

    WolfStaff instance = WolfStaff.Instance;

    public static HashMap<UUID, BukkitRunnable> cachedRunnable = new HashMap<>();

    public static ArrayList<UUID> playerToClear = new ArrayList<>();
    private static HashMap<UUID, Integer> cachedDisconnectedMod = new HashMap<>();

    public void cachePlayerMod(Player player) {
        UUID uuid = player.getUniqueId();
        Plugin plugin;

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                cachedDisconnectedMod.remove(player.getUniqueId());
                ModCore.removeMod(player);
                System.out.println("executed task: " + this.getTaskId());
            }         //          = 1 minute
        }.runTaskLaterAsynchronously(instance, 2 * (60 * 20)); // 2min
        //                             UUI              ID DE LA TASK
        cachedDisconnectedMod.put(player.getUniqueId(), task.getTaskId());
        //System.out.println("Caching " + player.getName() + " TaskID: " + task.getTaskId());
    }

    public static boolean isCachedDisconnectedMod(Player player) {
        return cachedDisconnectedMod.containsKey(player.getUniqueId());
    }

    /* ==================
     *  Partie Event
     * =================
     */

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!InventorySaverUtil.doesPlayerSaveExist(player)) return;
        if(isCachedDisconnectedMod(player)) {
            //System.out.println("canceled task" + getCachePlayerTaskID(player));
            Bukkit.getScheduler().cancelTask(getCachePlayerTaskID(player));
            cachedDisconnectedMod.remove(player.getUniqueId());
            return;
        }
        //System.out.println("reconnexion après expiration");

        // si il n'est plus caché / si sa runnable a expiré

        InventorySaverUtil.restorePlayerInvFromData(player);
        InventorySaverUtil.deletePlayerInvData(player);

    }

    /*
     * Partie onLeave déplacé sur -> ModEvent
     *
     */
    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if(!ModCore.isMod(player)) return;
        cachePlayerMod(player);
    }

    private int getCachePlayerTaskID(Player player) {
        return cachedDisconnectedMod.get(player.getUniqueId());
    }


}
