package dev.m1n1don.keidro.commands.keidro.subs;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.command.ISubCommand;
import dev.m1n1don.keidro.game.Game;
import dev.m1n1don.keidro.manager.RegionManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Jail implements ISubCommand
{

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        final Player p = (Player) sender;
        final Region region = RegionManager.getRegion(p);

        if (region == null)
        {
            sender.sendMessage(ChatColor.RED + "Please make a region selection first.");
            return;
        }

        Game.getGame().setJail(new dev.m1n1don.keidro.jail.Jail(p, region));

        sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.GOLD + "牢獄を設定しました。");
    }
}