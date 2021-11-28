package fr.zeykra.wolfstaff.enums;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ModItemEnum {
    FREEZE(Material.ICE, "§bFreeze", "wolfstaff.freeze"),
    KILL(Material.REDSTONE_TORCH_ON, "§cKill", "wolfstaff.kill"),
    INVSEE(Material.HOPPER, "§eInvsee", "wolfstaff.invsee");

    private Material material;
    private String name;
    private String permission;

    ModItemEnum(Material mat, String n, String perm) {
        this.material = mat;
        this.name = n;
        this.permission = perm;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public String getPermission() {
        return permission;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;
    }
}
