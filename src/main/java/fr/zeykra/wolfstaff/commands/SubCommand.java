package fr.zeykra.wolfstaff.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    //Le nom de la commande ex: /cmd <subcommand> <- ceci
    public abstract String getName();

    // Description de la commande
    public abstract String getDescription();

    // Son utilisation
    public abstract String getSyntax();

    // le code de la commande
    public abstract void perform(Player player, String args[]);

}
