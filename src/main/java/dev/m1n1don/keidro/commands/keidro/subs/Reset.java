package dev.m1n1don.keidro.commands.keidro.subs;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.command.ISubCommand;
import dev.m1n1don.keidro.game.Game;
import dev.m1n1don.keidro.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Reset implements ISubCommand
{

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (GameManager.get().isGame())
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "ゲーム中のためリセットできません。");
            return;
        }

        Game.getGame().reset();
        Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.GOLD + "ゲームをリセットしました。");
    }
}