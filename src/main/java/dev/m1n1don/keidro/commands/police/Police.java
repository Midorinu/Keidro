package dev.m1n1don.keidro.commands.police;

import dev.m1n1don.keidro.KeidroPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Police implements CommandExecutor
{
    private static boolean lottery = false;
    private static List<Player> players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!lottery)
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "現在抽選は行われていません。");
            return true;
        }

        final Player p = (Player) sender;
        if (players.contains(p))
        {
            players.remove(p);
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "警察抽選への応募をキャンセルしました。");
            return true;
        }

        players.add(p);
        sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.GOLD + "警察抽選に応募しました。");
        return true;
    }

    public static List<Player> getPlayers()
    {
        return players;
    }

    public static void setLottery(boolean bool)
    {
        lottery = bool;
    }
}