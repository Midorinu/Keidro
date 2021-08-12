package dev.m1n1don.keidro.listeners;

import com.sk89q.worldedit.math.Vector3;
import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.game.Game;
import dev.m1n1don.keidro.game.GameManager;
import dev.m1n1don.keidro.scoreboard.Teams;
import dev.m1n1don.keidro.scoreboard.fastboard.FastBoard;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener
{

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        if (!GameManager.get().isGame()) return;

        final Player p = e.getEntity();

        if (!Teams.get().isTeamJoined(p, Teams.TeamType.DOROBO)) return;

        final Vector3 vector = Game.getGame().getJail().getRegion().getCenter();
        p.teleport(new Location(p.getWorld(), vector.getX(), vector.getY(), vector.getZ()));
        Teams.get().joinTeam(p, Teams.TeamType.JAIL);

        Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.YELLOW + p.getName() + ChatColor.DARK_AQUA + "が警察に捕まった。");

        for (FastBoard board : KeidroPlugin.getPlugin().getBoards().values())
        {
            board.updateLine(3, ChatColor.WHITE + "  泥棒" + ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + Teams.get().getTeam(Teams.TeamType.DOROBO).getSize());
            board.updateLine(4, ChatColor.GRAY + "  牢獄" + ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + Teams.get().getTeam(Teams.TeamType.JAIL).getSize());
        }

        if (Teams.get().getTeam(Teams.TeamType.DOROBO).getSize() == 0)
        {
            Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.YELLOW + "警察側の勝利！");
            Game.getGame().end();
        }

        respawn(p);
    }

    private void respawn(Player player)
    {
        player.getServer().getScheduler().scheduleSyncDelayedTask(KeidroPlugin.getPlugin(), () -> {
            if (player.isDead())
                ((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
        });
    }
}