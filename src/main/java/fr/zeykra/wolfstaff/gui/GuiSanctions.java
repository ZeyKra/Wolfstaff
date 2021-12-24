package fr.zeykra.wolfstaff.gui;

import fr.zeykra.wolfstaff.Core.ModCore;
import fr.zeykra.wolfstaff.WolfStaff;
import fr.zeykra.wolfstaff.objects.YmlGuiObject;
import fr.zeykra.wolfstaff.util.ColorUtil;
import fr.zeykra.wolfstaff.util.YmlGuiReader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class GuiSanctions implements Listener {


    static YmlGuiReader ymlGuiReader = WolfStaff.sanctiongui;

    static String[] pagesName = {"autre", "cheat", "gameplay", "chat", "minage"};
    private static Map guiList = new HashMap<String, HashMap<Integer, YmlGuiObject>>();

    public static void open(String menu, Player player, Player target) {
        Inventory inv = Bukkit.createInventory(null, 6 * 9, menu);
        HashMap<Integer, YmlGuiObject> tempHashmap = (HashMap<Integer, YmlGuiObject>) guiList.get(menu);
        for (Map.Entry<Integer, YmlGuiObject> set : tempHashmap.entrySet()) {
            inv.setItem(set.getKey(), generateItem(set.getValue()));
        }


        inv.setItem(4, generateHead(target));
        //slot default
        inv.setItem(11, createGuiItem(Material.PAPER, "Autre", "§cCatégorie: §fAutres"));
        inv.setItem(12, createGuiItem(Material.ANVIL, "Cheat", "§cCatégorie: §fCheat"));
        inv.setItem(13, createGuiItem(Material.TNT, "Gamplay", "§cCatégorie: §fGameplay"));
        inv.setItem(14, createGuiItem(Material.PAINTING, "Chat", "§cCatégorie: §fChat"));
        inv.setItem(15, createGuiItem(Material.DIAMOND_PICKAXE, "Minage", "§cCatégorie: §fMinage"));

        for(int i = 20; i < 25 ; i++) {
            inv.setItem(i, createGuiItem(Material.STAINED_GLASS_PANE, "", 0, ""));
        }
        inv.setItem(22, createGuiItem(Material.STAINED_GLASS_PANE, "", 14, ""));



        player.openInventory(inv);
        ModCore.setCurrentGui(player, "sanction-" + menu);
    }

    public void updateInv(String menu, Player player) {
        menu = menu.replace("sanction-", "");
        ModCore.setCurrentGui(player, "sanction-" + menu);
        int[] protectedSlot = {4, 11, 12, 13, 14, 15, 20, 21, 22, 23, 24};
        Inventory inv = player.getOpenInventory().getTopInventory();;
        for(int i = 0; i < 53; i++) {
            int finalI = i;
            if(Arrays.stream(protectedSlot).anyMatch(x -> x == finalI)) { continue; }
            inv.setItem(i, new ItemStack(Material.AIR));
        }
        for(int i = 20; i < 25 ; i++) {
            inv.setItem(i, createGuiItem(Material.STAINED_GLASS_PANE, "", 0, ""));
        }

        HashMap<Integer, YmlGuiObject> tempHashmap = (HashMap<Integer, YmlGuiObject>) guiList.get(menu);
        for (Map.Entry<Integer, YmlGuiObject> set : tempHashmap.entrySet()) {
            inv.setItem(set.getKey(), generateItem(set.getValue()));
        }

        player.updateInventory();
    }

    private static ItemStack generateItem(YmlGuiObject ymlGuiObject) {
        ItemStack item = ymlGuiObject.getItem();
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.format(ymlGuiObject.getName()));
        String[] lore = ColorUtil.format(ymlGuiObject.getLore()).split("\\|\\|");
        meta.setLore(Arrays.asList(lore));
        item.setAmount(1);
        item.setItemMeta(meta);

        return item;
    }


    public static void generateGuiList() {
        for(String page : pagesName) {
            Map tHasmap = new HashMap<Integer, YmlGuiObject>(ymlGuiReader.readWhole(page));
            //System.out.println("Temp Hashmap: " + tHasmap);
            guiList.put(page, tHasmap);
        }

        // sout
        //System.out.println(guiList);

    }

    public String getCommand(String page, Integer slot) {

        HashMap<Integer, YmlGuiObject> tempHashmap = (HashMap<Integer, YmlGuiObject>) guiList.get(page);
        YmlGuiObject tempYmlGuiObject = tempHashmap.get(slot);
        return tempYmlGuiObject.getCommand();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory() == null) { return; }
        if(e.getInventory().getType() == InventoryType.PLAYER) { return; }
        if(e.getInventory().getName() == null || e.getInventory().getName().isEmpty()) { return; }
        Player player = (Player) e.getWhoClicked();
        if(!ModCore.hasCurrentGui(player)) { return; }
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) { return; }

        if(!ModCore.getCurrentGuiName(player).contains("sanction-")) { return; }

        e.setCancelled(true);

        switch(e.getSlot()) {
            case 11:
                if(ModCore.getCurrentGuiName(player).equalsIgnoreCase("sanction-autre")) return;
                updateInv("autre", player);
                player.getOpenInventory().getTopInventory().setItem(20, createGuiItem(Material.STAINED_GLASS_PANE, "", 14, ""));
                return;
            case 12:
                if(ModCore.getCurrentGuiName(player).equalsIgnoreCase("sanction-cheat")) return;
                updateInv("cheat", player);
                player.getOpenInventory().getTopInventory().setItem(21, createGuiItem(Material.STAINED_GLASS_PANE, "", 14, ""));
                return;
            case 13:
                if(ModCore.getCurrentGuiName(player).equalsIgnoreCase("sanction-gameplay")) return;
                updateInv("gameplay", player);
                player.getOpenInventory().getTopInventory().setItem(22, createGuiItem(Material.STAINED_GLASS_PANE, "", 14, ""));
                return;
            case 14:
                if(ModCore.getCurrentGuiName(player).equalsIgnoreCase("sanction-chat")) return;
                updateInv("chat", player);
                player.getOpenInventory().getTopInventory().setItem(23, createGuiItem(Material.STAINED_GLASS_PANE, "", 14, ""));
                return;
            case 15:
                if(ModCore.getCurrentGuiName(player).equalsIgnoreCase("sanction-minage")) return;
                updateInv("minage", player);
                player.getOpenInventory().getTopInventory().setItem(24, createGuiItem(Material.STAINED_GLASS_PANE, "", 14, ""));
                return;

        }
        //prevention d'erreur dans la console pour les slot static
        if(e.getSlot() == 4 || e.getSlot() == 20 || e.getSlot() == 21 || e.getSlot() == 22 || e.getSlot() == 23 || e.getSlot() == 24 ) return;

        // recuperation du bon hashmap grace au nom du gui du Joueur ( getCurrentGuiName() ) avec le replace qui "sanction-" > ""
        HashMap<Integer, YmlGuiObject> tempHashmap = (HashMap<Integer, YmlGuiObject>) guiList.get(ModCore.getCurrentGuiName(player).replace("sanction-", ""));
        YmlGuiObject tempYmlGuiObject = tempHashmap.get(e.getSlot());
        String command = tempYmlGuiObject.getCommand().replace("{PLAYER}", ModCore.getSanctionedPlayer(player).getName());
        if(command == null || command.isEmpty() || command == "") { return; }
        player.performCommand(command);

        player.closeInventory();
    }

    static protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    static protected ItemStack createGuiItem(final Material material, final String name, int itemByte, final String... lore) {
        final ItemStack item = new ItemStack(material, 1, (byte) itemByte);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    static protected ItemStack generateHead(Player player) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName(player.getDisplayName());
        skull.setItemMeta(skullMeta);
        return skull;
    }


}
