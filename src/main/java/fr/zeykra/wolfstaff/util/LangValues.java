package fr.zeykra.wolfstaff.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LangValues {

    /*
     * Class pour remplacer certains value par des variables
     * Ex: {PLAYER} -> player.getName();
     *     {LOCATION} -> "x: 57 y:84 z:-126"
     *     {TIME} -> "15.2" non utilisé

     */

    public static String format(String txt, Player player) {
        return txt.replace("{PLAYER}", player.getName());
    }

    public static String format(String txt, Double time) {
        time = (double) Math.round(time);
        return txt.replace("{TIME}", time.toString());
    }

    public static String format(String txt, Location location) {
        String res = "x:" + location.getX() +
                " y:" + location.getY() +
                " z:" + location.getZ();
        return txt.replace("{LOCATION}", res);
    }

    public static String customFormat(String txt, String var, String replace) {
        return txt.replace(var, replace);
    }

}
