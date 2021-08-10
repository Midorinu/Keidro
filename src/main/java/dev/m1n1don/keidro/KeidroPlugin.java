package dev.m1n1don.keidro;

import dev.m1n1don.keidro.command.Command;
import dev.m1n1don.keidro.commands.keidro.Keidro;
import dev.m1n1don.keidro.commands.police.Police;
import dev.m1n1don.keidro.commands.spectator.Spectator;
import dev.m1n1don.keidro.commands.texture.Texture;
import dev.m1n1don.keidro.listeners.EntityDamage;
import dev.m1n1don.keidro.listeners.PlayerConnection;
import dev.m1n1don.keidro.listeners.PlayerDeath;
import dev.m1n1don.keidro.scoreboard.fastboard.FastBoard;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KeidroPlugin extends AbstractKeidroPlugin
{
    public static String PREFIX = ChatColor.BLUE + "[" + ChatColor.AQUA + "ケイドロ" + ChatColor.BLUE + "] ";
    private static KeidroPlugin plugin;

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable()
    {
        plugin = this;

        registerListeners(
                new EntityDamage(),
                new PlayerConnection(),
                new PlayerDeath()
        );

        registerCommands(
                new Command("keidro", new Keidro()),
                new Command("police", new Police()),
                new Command("spectator", new Spectator()),
                new Command("/texture", new Texture())
        );
    }

    @Override
    public void onDisable()
    {
    }

    public static KeidroPlugin getPlugin()
    {
        return plugin;
    }

    public Map<UUID, FastBoard> getBoards()
    {
        return boards;
    }
}