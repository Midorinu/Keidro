package dev.m1n1don.keidro.commands.keidro.timer;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.command.ISubCommand;
import dev.m1n1don.keidro.commands.keidro.Keidro;
import dev.m1n1don.keidro.commands.keidro.subs.*;
import dev.m1n1don.keidro.commands.keidro.timer.subs.Add;
import dev.m1n1don.keidro.commands.keidro.timer.subs.Remove;
import dev.m1n1don.keidro.commands.keidro.timer.subs.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Timer implements ISubCommand
{

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        final Player p = (Player) sender;

        if (args.length < 2)
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "使い方: /keidro timer <set/add/remove> <number>");
            return;
        }

        if (!SubCommand.isSubCommand(args[1].toUpperCase()))
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "使い方: /keidro timer <set/add/remove> <number>");
            return;
        }

        SubCommand.valueOf(args[1].toUpperCase()).getSubCommand().execute(sender, args);
    }

    public enum SubCommand
    {
        SET(new Set()),
        ADD(new Add()),
        REMOVE(new Remove());

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