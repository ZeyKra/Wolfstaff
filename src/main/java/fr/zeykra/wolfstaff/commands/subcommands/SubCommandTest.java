package fr.zeykra.wolfstaff.commands.subcommands;

import fr.zeykra.wolfstaff.commands.SubCommand;
import fr.zeykra.wolfstaff.util.BukkitSerialization;
import fr.zeykra.wolfstaff.util.InventorySaverUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class SubCommandTest extends SubCommand {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "commande de test";
    }

    @Override
    public String getSyntax() {
        return "/wolfstaff test";
    }

    @Override
    public void perform(Player player, String[] args) {
        //player.sendMessage("tentationde save d'inv");

        switch (args[1]) {
            case "save":
                InventorySaverUtil.savePlayerInventory(player);
                player.sendMessage("saving");
                break;
            case "delete":
                InventorySaverUtil.deletePlayerInvData(player);
                player.sendMessage("deleting");
                break;
            case "get":
                String[] invBase64 = InventorySaverUtil.getPlayerInvData(player);
                player.sendMessage(invBase64[0]);
                player.sendMessage(invBase64[1]);
                break;
            case "restore":
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
                player.sendMessage("inventory restoré");
                break;
        }


    }
}
