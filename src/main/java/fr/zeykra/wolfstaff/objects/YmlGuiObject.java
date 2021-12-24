package fr.zeykra.wolfstaff.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class YmlGuiObject {
    int slot;
    String name;
    ItemStack item;
    byte itemByte;
    String lore;
    String command;

    YmlGuiObject(int slot, String name, String itemType, int itemByte, String lore, String command) {
        this.slot = slot;
        this.name = name;
        this.item = new ItemStack(Material.matchMaterial(itemType), 1, (byte) itemByte);
        this.itemByte = (byte) itemByte;
        this.lore = lore;
        this.command = command;
    }

    public YmlGuiObject(int slot, String name, String itemType, String lore, String command) {
        this.slot = slot;
        this.name = name;
        this.item = new ItemStack(Material.matchMaterial(itemType), 1);
        this.lore = lore;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public byte getItemByte() {
        return itemByte;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getCommand() {
        return command;
    }

    public String getLore() {
        return lore;
    }

    //set le byte ex color d'un item
    public void setItemByte(int itemByte) {
        this.item = new ItemStack(item.getType(), 1, (byte) itemByte);
    }

}
