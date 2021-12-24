package fr.zeykra.wolfstaff.commands;

import fr.zeykra.wolfstaff.commands.subcommands.SubCommandKill;
import fr.zeykra.wolfstaff.commands.subcommands.SubCommandList;
import fr.zeykra.wolfstaff.commands.subcommands.SubCommandReload;
import fr.zeykra.wolfstaff.commands.subcommands.SubCommandTest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManagerWolfstaff implements CommandExecutor {

    //Class permettant de gerer les sous-commands/ arguments de celle si

    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManagerWolfstaff() {
        subCommands.add(new SubCommandKill());
        subCommands.add(new SubCommandReload());
        subCommands.add(new SubCommandList());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(p, args);
                    }
                }
            }else if(args.length == 0){
                p.sendMessage("§8§m--------------------------------");
                for (int i = 0; i < getSubcommands().size(); i++){
                    p.sendMessage(getSubcommands().get(i).getSyntax() + " - " + getSubcommands().get(i).getDescription());
                }
                p.sendMessage("§8§m--------------------------------");
            }

        }


        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subCommands;
    }


    public ArrayList<SubCommand> getSubCommands() { return subCommands; }
}
