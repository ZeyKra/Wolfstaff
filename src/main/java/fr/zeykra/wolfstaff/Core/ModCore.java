package fr.zeykra.wolfstaff.Core;

import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ModCore implements Listener {

    private static YmlFileUtil config = WolfStaff.config;
    private static WolfStaff instance = WolfStaff.Instance;


    //vanish
    public static Set<UUID> vanishList = new HashSet<>();
    public static void addVanish(Player player) {vanishList.add(player.getUniqueId());}
    public static void removeVanish(Player player) {vanishList.remove(player.getUniqueId());}


    //staff en ligne
    public static Set<UUID> onlineStaffList = new HashSet<>();

    //list des joueur en /mod
    public static Set<UUID> modList = new HashSet<>();
    public static void addMod(Player player) {modList.add(player.getUniqueId());}
    public static void removeMod(Player player) {modList.remove(player.getUniqueId());}

    //player qui sont entrain d'être regarder de façon a pouvoir gerer le gui dynamic ta mère
    public static Map<UUID, Set<UUID>> inSeeList = new HashMap<>();

    //List contenant les inventaires des modérateurs
    /*public static HashMap<UUID, ItemStack[]> inventoryList = new HashMap<>();
    public static void addInventory(Player player) {inventoryList.put(player.getUniqueId(), player.getInventory().getContents());}
    public static void removeInventory(Player player) {inventoryList.remove(player.getUniqueId());} */

    //Hashmap <UUID du Mod, UUID du Joueur > contenant les joueurs entrain de se faire sanctioner
    public static HashMap<UUID, UUID> sanctionedPlayer = new HashMap<>();
    public static void setSanctionedPlayer(Player mod, Player target) { sanctionedPlayer.put(mod.getUniqueId(), target.getUniqueId());}
    public static void removeSanctionedPlayer(Player mod) { sanctionedPlayer.remove(mod.getUniqueId());}

    //Hashmap <UUID du Mod, Nom du gui> permettant de savoir quelle gui le mod utilise
    public static HashMap<UUID, String> currentGui = new HashMap<>();
    public static void setCurrentGui(Player mod, String guiName) { currentGui.put(mod.getUniqueId(), guiName);}
    public static void removeCurrentGui(Player mod) { currentGui.remove(mod.getUniqueId());}

    public static void addViewer(Player viewed, Player viewer) {
        //si le joueur a déjà une liste
        if(inSeeList.containsKey(viewed.getUniqueId())) {
            Set<UUID> tSet = inSeeList.get(viewed.getUniqueId());
            tSet.add(viewer.getUniqueId());
            inSeeList.put(viewed.getUniqueId(), tSet);
            return;
        }
        Set<UUID> tSet = new HashSet<>();
        tSet.add(viewer.getUniqueId());
        inSeeList.put(viewed.getUniqueId(), tSet);

    }

    public static void removeViewer(Player viewed, Player viewer) {
        //si le joueur a déjà une liste
        if(!inSeeList.containsKey(viewed.getUniqueId())) { return; }

        Set<UUID> tSet = inSeeList.get(viewed.getUniqueId());
        tSet.remove(viewer.getUniqueId());
        if(tSet.size() <= 0) {
            inSeeList.remove(viewed.getUniqueId());
        }
    }

    public static Set<Player> getViewers(Player viewed) {
        Set<Player> tSet = new HashSet<>();

        if(inSeeList.containsKey(viewed.getUniqueId())) {
            for(UUID tUUID : inSeeList.get(viewed.getUniqueId())) {
                tSet.add(instance.getServer().getPlayer(tUUID));
            }
        }

        return tSet;
    }

    public static Boolean hasViewers(Player viewed) {
        return inSeeList.containsKey(viewed.getUniqueId());
    }

    //mise en place de onlinesStaffList<>
    public static void setup() {
        for(Player lPlayer : instance.getServer().getOnlinePlayers()) {
            if(lPlayer.hasPermission(config.getString("permission-staff"))) {
                onlineStaffList.add(lPlayer.getUniqueId());
            }
        }
    }

    public static boolean isVanished(Player player) {
        return vanishList.contains(player.getUniqueId());
    }

    public static boolean isMod(Player player) {
        return modList.contains(player.getUniqueId());
    }

    //Current Gui & SanctionedPlayer
    public static boolean hasCurrentGui(Player player) { return currentGui.containsKey(player.getUniqueId()); }
    public static String getCurrentGuiName(Player player) { return currentGui.get(player.getUniqueId()); }


    public static boolean hasSanctionedPlayer(Player player) { return sanctionedPlayer.containsKey(player.getUniqueId()); }
    public static Player getSanctionedPlayer(Player player) { return instance.getServer().getPlayer(sanctionedPlayer.get(player.getUniqueId())); }

    /*
     * Manage de la liste des staff en ligne
     * Join , deco
     *  + Quelques event en tant que mod
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(player.hasPermission(config.getString("permission-staff"))) {
            onlineStaffList.add(player.getUniqueId());
        }
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if(player.hasPermission(config.getString("permission-staff")) && onlineStaffList.contains(player.getUniqueId())) {
            onlineStaffList.remove(player.getUniqueId());
        }
        if(hasSanctionedPlayer(player)) {
            removeSanctionedPlayer(player);
        }
        if(hasCurrentGui(player)) {
            removeCurrentGui(player);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(e.getEntity() == null) return;
        if(!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if(ModCore.isMod(player)) {
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();

        if(ModCore.isMod(victim)) {
            e.setCancelled(true);
        }
    }

}
