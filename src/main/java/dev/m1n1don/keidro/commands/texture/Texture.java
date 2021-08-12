package dev.m1n1don.keidro.commands.texture;

import dev.m1n1don.keidro.KeidroPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Texture implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        final Player p = (Player) sender;
        p.setResourcePack("https://www.midorin.info/textures/KeidroTexture.zip");
        sender.sendMessage(KeidroPlugin.PREFIX + ChatColor.GREEN + "テクスチャを読み込みました。");
        return true;
    }
}