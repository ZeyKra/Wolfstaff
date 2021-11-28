package fr.zeykra.wolfstaff.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    //Class permettant de convert les "&" en "§"

    public static String format(String txt) {
        return ChatColor.translateAlternateColorCodes('&', txt);
    }

}
