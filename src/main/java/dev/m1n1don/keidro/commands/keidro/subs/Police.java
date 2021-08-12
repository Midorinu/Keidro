package dev.m1n1don.keidro.commands.keidro.subs;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.command.ISubCommand;
import dev.m1n1don.keidro.scoreboard.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class Police implements ISubCommand
{

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (args.length < 2)
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "使い方: /keidro police <number/player>");
            return;
        }

        if (args[1].matches("[+-]?\\d*(\\.\\d+)?"))
        {
            if (Integer.parseInt(args[1]) <= 0)
            {
                sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "0以下の数字は使用できません。");
                return;
            }

            if (Integer.parseInt(args[1]) > Bukkit.getOnlinePlayers().size())
            {
                sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "募集人数がプレイヤー数より多いため抽選できません。");
                return;
            }

            dev.m1n1don.keidro.commands.police.Police.setLottery(true);
            Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.GREEN + "60秒後に警察の抽選が行われます。\n" + ChatColor.GRAY + "警察の数: " + args[1] + "人");
            Bukkit.getScheduler ().runTaskLater (KeidroPlugin.getPlugin(), () -> randomLottery(Integer.parseInt(args[1])), 1200);
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

    private void randomLottery(int num)
    {
        dev.m1n1don.keidro.commands.police.Police.setLottery(false);
        Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.LIGHT_PURPLE + "抽選をします...");

        if (num > Bukkit.getOnlinePlayers().size())
        {
            Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.RED + "募集人数がプレイヤー数より多いため抽選できませんでした。");
            return;
        }

        for (int i = 0; i < num; i++)
        {
            int random = new Random().nextInt(dev.m1n1don.keidro.commands.police.Police.getPlayers().size());
            final Player target = dev.m1n1don.keidro.commands.police.Police.getPlayers().get(random);
            Teams.get().joinTeam(target, Teams.TeamType.POLICE);
            Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.YELLOW + target.getName() + ChatColor.GOLD + "が警察になりました。");
            dev.m1n1don.keidro.commands.police.Police.getPlayers().remove(target);
        }
        dev.m1n1don.keidro.commands.police.Police.getPlayers().clear();
    }
}

