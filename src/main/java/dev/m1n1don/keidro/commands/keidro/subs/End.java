package dev.m1n1don.keidro.commands.keidro.subs;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.command.ISubCommand;
import dev.m1n1don.keidro.game.Game;
import dev.m1n1don.keidro.game.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class End implements ISubCommand
{

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (!GameManager.get().isGame())
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "ゲーム中ではないため終了できません。");
            return;
        }

        Game.getGame().end();
    }
}