package dev.m1n1don.keidro.commands.spectator;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.game.GameManager;
import dev.m1n1don.keidro.scoreboard.Teams;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spectator implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!GameManager.get().isGame())
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "ゲーム中ではないため観戦モードを使用できません。");
            return true;
        }

        final Player p = (Player) sender;

        if (!Teams.get().isTeamJoined(p, Teams.TeamType.JAIL))
        {
            sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.RED + "牢獄以外のプレイヤーは観戦モードを使用できません。");
            return true;
        }

        p.setGameMode(GameMode.SPECTATOR);
        sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.AQUA + "観戦モードに変更しました。");
        return true;
    }
}