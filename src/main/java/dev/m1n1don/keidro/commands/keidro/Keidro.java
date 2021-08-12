package dev.m1n1don.keidro.commands.keidro;

import dev.m1n1don.keidro.command.ISubCommand;
import dev.m1n1don.keidro.commands.keidro.subs.*;
import dev.m1n1don.keidro.commands.keidro.timer.Timer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Keidro implements CommandExecutor, TabExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        final Player p = (Player) sender;

        if (args.length < 1)
        {
            SubCommand.HELP.getSubCommand().execute(sender, args);
            return true;
        }

        if (!SubCommand.isSubCommand(args[0].toUpperCase()))
        {
            SubCommand.HELP.getSubCommand().execute(sender, args);
            return true;
        }

        SubCommand.valueOf(args[0].toUpperCase()).getSubCommand().execute(sender, args);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        List<String> tab = new ArrayList<>();
        if (args.length == 1) for (SubCommand cmd : SubCommand.values()) tab.add(cmd.name().toLowerCase());
        return tab;
    }

    public enum SubCommand
    {
        HELP(new Help()),
        START(new Start()),
        END(new End()),
        RESET(new Reset()),
        POLICE(new Police()),
        TIMER(new Timer()),
        JAIL(new Jail());

        private final ISubCommand subCommand;

        SubCommand(ISubCommand subCommand)
        {
            this.subCommand = subCommand;
        }

        public ISubCommand getSubCommand()
        {
            return subCommand;
        }

        public static boolean isSubCommand(String name)
        {
            for (SubCommand subCommand : values()) if (subCommand.toString().equals(name.toUpperCase())) return true;
            return false;
        }
    }
}