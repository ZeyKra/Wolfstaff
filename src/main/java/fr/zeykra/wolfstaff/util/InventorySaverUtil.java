package fr.zeykra.wolfstaff.util;

import fr.zeykra.wolfstaff.WolfStaff;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

public class InventorySaverUtil {

    private static final WolfStaff instance = WolfStaff.Instance;
    private static File dataFolder = instance.getDataFolder();
    private static String path = dataFolder.getPath() + "/inventory/";

    private static String PREFIX = "InventorySaverUtil Logger >>";

    public static void setup() throws IOException {
        if(dataFolder.exists()) {
            File inventoryFolder = new File(path);
            if(inventoryFolder.exists()) { return; }

            if (inventoryFolder.mkdirs()) {
                Logger("Directory is created!");
            } else {
                Logger("Failed to create directory!");
            }
        }
    }

    public static void savePlayerInventory(Player player) {

        UUID uuid = player.getUniqueId();
        File playerInvFile = new File(path, uuid.toString() + ".yml");

        if(doesPlayerSaveExist(player)) return;

        if(!playerInvFile.exists()) {
            try {
                playerInvFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*if(!) {
            Logger("Failed to create " + player.getName() + " inventory file");
        } */
        YamlConfiguration ymlConfig = YamlConfiguration.loadConfiguration(playerInvFile);

        String[] playerInv = BukkitSerialization.playerInventoryToBase64(player.getInventory());

        ymlConfig.set("inventory", playerInv[0]);
        ymlConfig.set("armor", playerInv[1]);
        try {
            ymlConfig.save(playerInvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getPlayerInvData(Player player) {
        UUID uuid = player.getUniqueId();
        File playerInvFile = new File(path, uuid.toString() + ".yml");
        String[] res = {};

        if(playerInvFile.exists()) {
            YamlConfiguration ymlConfig = YamlConfiguration.loadConfiguration(playerInvFile);
            String invContent = ymlConfig.getString("inventory");
            String armor = ymlConfig.getString("armor");
            res = new String[]{invContent, armor};
        }
        return res;
    }

    public static boolean doesPlayerSaveExist(Player player) {
        UUID uuid = player.getUniqueId();
        File playerInvFile = new File(path, uuid.toString() + ".yml");

        return playerInvFile.exists();
    }

    public static void deletePlayerInvData(Player player) {
        UUID uuid = player.getUniqueId();
        File playerInvFile = new File(path, uuid.toString() + ".yml");

        if(!playerInvFile.exists()) return;
        playerInvFile.delete();
    }

    public static void restorePlayerInvFromData(Player player) {
        String[] inventoryBase64 = InventorySaverUtil.getPlayerInvData(player);
        ItemStack[] content = new ItemStack[]{};
        ItemStack[] armor = new ItemStack[]{};
        try {
            content = BukkitSerialization.itemStackArrayFromBase64(inventoryBase64[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            armor = BukkitSerialization.itemStackArrayFromBase64(inventoryBase64[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.getInventory().setContents(content);
        player.getInventory().setArmorContents(armor);
        player.updateInventory();
    }

    /*
     Logger
     *
     */

    protected static void Logger(String text) {
        System.out.println(PREFIX + text);
    }

}
