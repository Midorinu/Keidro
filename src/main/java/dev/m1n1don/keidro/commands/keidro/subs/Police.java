package dev.m1n1don.keidro.commands.keidro.subs;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.command.ISubCommand;
import dev.m1n1don.keidro.scoreboard.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Police implements ISubCommand
{

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (args.length > 2)
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "使い方: /keidro police <number/player>");
            return;
        }

        if (args[1].matches("[+-]?\\d*(\\.\\d+)?"))
        {
            // 数字
            return;
        }

        final Player target = Bukkit.getPlayer(args[1]);

        if (target == null)
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "そのプレイヤーは存在しません。");
            return;
        }

        if (!target.isOnline())
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "そのプレイヤーはオフラインです。");
            return;
        }

        if (Teams.get().isTeamJoined(target, Teams.TeamType.POLICE))
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "そのプレイヤーは既に警察です。");
            return;
        }

        Teams.get().joinTeam(target, Teams.TeamType.POLICE);
        Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + "が警察になりました。");
    }
}

