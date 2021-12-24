package fr.zeykra.wolfstaff;

import fr.zeykra.wolfstaff.Core.ModAction;
import fr.zeykra.wolfstaff.Core.ModCache;
import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.commands.*;
import fr.zeykra.wolfstaff.enums.FileEnum;
import fr.zeykra.wolfstaff.event.*;
import fr.zeykra.wolfstaff.gui.GuiInformation;
import fr.zeykra.wolfstaff.gui.GuiMinerais;
import fr.zeykra.wolfstaff.gui.GuiSanctions;
import fr.zeykra.wolfstaff.util.InventorySaverUtil;
import fr.zeykra.wolfstaff.util.YmlFileUtil;
import fr.zeykra.wolfstaff.util.ModItems;
import fr.zeykra.wolfstaff.util.YmlGuiReader;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class WolfStaff extends JavaPlugin {

    public static WolfStaff Instance;
    public static YmlFileUtil config;
    public static YmlFileUtil lang;
    public static YmlGuiReader sanctiongui;

    @Override
    public void onEnable() {
        Instance = this;
        config = new YmlFileUtil(this.getDataFolder().toPath().toString(), "config.yml");
        lang = new YmlFileUtil(this.getDataFolder().toPath().toString(), "lang.yml");
        sanctiongui = new YmlGuiReader(this.getDataFolder().toPath().toString(), "sanctionsgui.yml");

        //Création des fichier config / lang
        FileEnum config = FileEnum.CONFIG;
        config.create(getLogger());
        FileEnum lang = FileEnum.LANG;
        lang.create(getLogger());
        FileEnum sanctiongui = FileEnum.SANCTIONGUI;
        sanctiongui.create(getLogger());

        //register de la commande
        //this.getCommand("wolfstaff").setExecutor(new CommandManagerWolfstaff());
        this.getCommand("cgi").setExecutor(new CommandCGI());
        this.getCommand("mod").setExecutor(new CommandMod());
        this.getCommand("staffchat").setExecutor(new CommandStaffchat());
        this.getCommand("wolfstaff").setExecutor(new CommandManagerWolfstaff());
        this.getCommand("sanction").setExecutor(new CommandSanction());
        this.getCommand("vanish").setExecutor(new CommandVanish());

        //register des event
        getServer().getPluginManager().registerEvents(new InventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new ModEvent(), this);
        getServer().getPluginManager().registerEvents(new VanishEvent(), this);
        getServer().getPluginManager().registerEvents(new ModCore(), this);
        getServer().getPluginManager().registerEvents(new ModItems(), this);
        getServer().getPluginManager().registerEvents(new ItemEvent(), this);
        getServer().getPluginManager().registerEvents(new FreezeEvent(), this);

        getServer().getPluginManager().registerEvents(new GuiMinerais(), this);
        getServer().getPluginManager().registerEvents(new GuiInformation(), this);
        getServer().getPluginManager().registerEvents(new GuiSanctions(), this);

        getServer().getPluginManager().registerEvents(new ModCache(), this);

        ModCore.setup();
        try {
            InventorySaverUtil.setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GuiSanctions.generateGuiList();
        //Fonction qui remet le statut pour les mods qui était en /mod après un reload
        ModAction.reSetupModState();

    }

    public void reloadPluginConfg() {
        config = new YmlFileUtil(this.getDataFolder().toPath().toString(), "config.yml");
        lang = new YmlFileUtil(this.getDataFolder().toPath().toString(), "lang.yml");
        sanctiongui = new YmlGuiReader(this.getDataFolder().toPath().toString(), "sanctionsgui.yml");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
