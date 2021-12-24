package fr.zeykra.wolfstaff.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;

public class YmlFileUtil {

    /* -------------------------------------
     * @Author : ZeyKra_
     * Class permettant de gerer les fichier yml
     * -------------------------------------
     */

    //made by ZeyKra_

    File file;
    YamlConfiguration ymlConfig;
    ConfigurationSection configSection;

    public YmlFileUtil(String Folder, String File) {
        this.file = new File(Folder, File);
        this.ymlConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void setConfigSection(String key) {
        this.configSection = ymlConfig.getConfigurationSection(key);
    }

    //basique
    // getter
    public String getString(String key) {
        if(file.exists()) {
            if(configSection == null) {
                return ymlConfig.getString(key);
            }
            return configSection.getString(key);
        }
        return null;
    }

    public double getDouble(String key) {
        if(file.exists()) {
            if(configSection == null) {
                return ymlConfig.getDouble(key);
            }
            return configSection.getDouble(key);
        }
        return 0.0;
    }

    public Boolean getBoolean(String key) {
        if(file.exists()) {
            if(configSection == null) {
                return ymlConfig.getBoolean(key);
            }
            return configSection.getBoolean(key);
        }
        return null;
    }

    public int getInt(String key) {
        if(file.exists()) {
            if(configSection == null) {
                return ymlConfig.getInt(key);
            }
            return configSection.getInt(key);
        }
        return 0;
    }

    public float getFloat(String key) {
        return (float) getDouble(key);
    }

    //setter

    public void set(String key, Object value) {
        if(file.exists()) {
            if(configSection == null) {
                ymlConfig.set(key, value);
                try {
                    ymlConfig.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            configSection.set(key, value);
            try {
                ymlConfig.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Plus spécifique

    //setter
    public void setLoc(ConfigurationSection configurationSection, Location loc) {
        configurationSection.set("world", loc.getWorld().getName());
        configurationSection.set("x", loc.getX());
        configurationSection.set("y", loc.getY());
        configurationSection.set("z", loc.getZ());
        try {
            ymlConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLoc(String configSectionkey, Location loc) {
        if(configSection == null) { setLoc(ymlConfig.getConfigurationSection(configSectionkey), loc); return; }
        // si ya deja un config section bah ça cherche la key dedans example pos1/player2
        setLoc(configSection.getConfigurationSection(configSectionkey), loc);
    }

    public void setLoc(Location loc) { setLoc(configSection, loc); }

    //getter
    public Location getLoc() { return getLoc(configSection); }

    public Location getLoc(ConfigurationSection configurationSection) {
        World world = Bukkit.getWorld(configurationSection.getString("world"));
        double x = configurationSection.getDouble("x");
        double y = configurationSection.getDouble("y");
        double z = configurationSection.getDouble("z");
        Location loc = new Location(world, x, y, z);
        return loc;
    }

    public Location getLoc(String configSectionkey) {
        if(configSection == null) { return getLoc(ymlConfig.getConfigurationSection(configSectionkey)); }
        // si ya deja un config section bah ça cherche la key dedans example pos1/player2
        return getLoc(configSection.getConfigurationSection(configSectionkey));
    }

    //Avec YAW
    public Location getLocWithDirection() {
        return getLocWithDirection(configSection);
    }

    public Location getLocWithDirection(ConfigurationSection configurationSection) {
        World world = Bukkit.getWorld(configurationSection.getString("world"));
        double x = configurationSection.getDouble("x");
        double y = configurationSection.getDouble("y");
        double z = configurationSection.getDouble("z");
        float yaw = (float) configurationSection.getDouble("yaw");
        float pitch = (float) configurationSection.getDouble("pitch");
        Location loc = new Location(world, x, y, z, yaw, pitch);
        return loc;
    }

    public Location getLocWithDirection(String configSectionkey) {
        if(configSection == null) { return getLocWithDirection(ymlConfig.getConfigurationSection(configSectionkey)); }
        // si ya deja un config section bah ça cherche la key dedans example pos1/player2
        return getLocWithDirection(configSection.getConfigurationSection(configSectionkey));
    }

}


