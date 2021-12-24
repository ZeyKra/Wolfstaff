package fr.zeykra.wolfstaff.util;

import fr.zeykra.wolfstaff.objects.YmlGuiObject;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class YmlGuiReader {

    File file;
    YamlConfiguration ymlConfig;
    ConfigurationSection configSection;

    public YmlGuiReader(String Folder, String File) {
        this.file = new File(Folder, File);
        this.ymlConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void setConfigSection(String key) {
        this.configSection = ymlConfig.getConfigurationSection(key);
    }

    public HashMap<Integer, YmlGuiObject> readWhole(String page) {
        HashMap<Integer, YmlGuiObject> objectsHashmap = new HashMap<>();
        if(!ymlConfig.isConfigurationSection(page)) { return objectsHashmap; }
        ConfigurationSection pageYmlConfig = ymlConfig.getConfigurationSection(page);
        for(int i = 0;  i < 53; i++) {
            if(pageYmlConfig.isSet("" + i)) {
                objectsHashmap.put(i, read(page,"" + i));
            }
        }
        return objectsHashmap;
    }

    public YmlGuiObject read(String page, String slot) {
        ConfigurationSection objectSection = ymlConfig.getConfigurationSection(page).getConfigurationSection(slot);
        YmlGuiObject ymlGuiObject = new YmlGuiObject(
                Integer.parseInt(slot),
                objectSection.getString("name"),
                objectSection.getString("item"),
                objectSection.getString("lore"),
                objectSection.getString("command")
        );
        if(objectSection.isSet("byte")) {
            ymlGuiObject.setItemByte(objectSection.getInt("byte"));
        }
        return ymlGuiObject;
    }




}
