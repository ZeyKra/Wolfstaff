package fr.zeykra.wolfstaff;

import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.commands.CommandCGI;
import fr.zeykra.wolfstaff.commands.CommandManagerWolfstaff;
import fr.zeykra.wolfstaff.commands.CommandMod;
import fr.zeykra.wolfstaff.commands.CommandStaffchat;
import fr.zeykra.wolfstaff.enums.FileEnum;
import fr.zeykra.wolfstaff.event.InventoryEvent;
import fr.zeykra.wolfstaff.event.ItemEvent;
import fr.zeykra.wolfstaff.event.ModEvent;
import fr.zeykra.wolfstaff.event.VanishEvent;
import fr.zeykra.wolfstaff.gui.GuiInformation;
import fr.zeykra.wolfstaff.gui.GuiMinerais;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import fr.zeykra.wolfstaff.util.ModItems;
import org.bukkit.plugin.java.JavaPlugin;

public final class WolfStaff extends JavaPlugin {

    public static WolfStaff Instance;
    public static YmlFileUtil config;
    public static YmlFileUtil lang;

    @Override
    public void onEnable() {
        Instance = this;
        config = new YmlFileUtil(this.getDataFolder().toPath().toString(), "config.yml");
        lang = new YmlFileUtil(this.getDataFolder().toPath().toString(), "lang.yml");

        //Création des fichier config / lang
        FileEnum config = FileEnum.CONFIG;
        config.create(getLogger());
        FileEnum lang = FileEnum.LANG;
        lang.create(getLogger());

        //register de la commande
        //this.getCommand("wolfstaff").setExecutor(new CommandManagerWolfstaff());
        this.getCommand("cgi").setExecutor(new CommandCGI());
        this.getCommand("mod").setExecutor(new CommandMod());
        this.getCommand("staffchat").setExecutor(new CommandStaffchat());
        this.getCommand("wolfstaff").setExecutor(new CommandManagerWolfstaff());

        //register des event
        getServer().getPluginManager().registerEvents(new InventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new ModEvent(), this);
        getServer().getPluginManager().registerEvents(new VanishEvent(), this);
        getServer().getPluginManager().registerEvents(new ModCore(), this);
        getServer().getPluginManager().registerEvents(new ModItems(), this);
        getServer().getPluginManager().registerEvents(new ItemEvent(), this);

        getServer().getPluginManager().registerEvents(new GuiMinerais(), this);
        getServer().getPluginManager().registerEvents(new GuiInformation(), this);

        ModCore.setup();

    }

    public void reloadCfg() {
        config = new YmlFileUtil(this.getDataFolder().toPath().toString(), "config.yml");
        lang = new YmlFileUtil(this.getDataFolder().toPath().toString(), "lang.yml");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
