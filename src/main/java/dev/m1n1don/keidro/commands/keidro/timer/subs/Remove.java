package dev.m1n1don.keidro.commands.keidro.timer.subs;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.command.ISubCommand;
import dev.m1n1don.keidro.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Remove implements ISubCommand
{

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        Game.getGame().countdown -= Integer.parseInt(args[2]);
        Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.GOLD + "ゲーム時間を " + ChatColor.YELLOW + args[2] + "秒" + ChatColor.GOLD + " 減らしました。");
    }
}