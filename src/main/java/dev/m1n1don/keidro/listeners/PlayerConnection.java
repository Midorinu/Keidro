package dev.m1n1don.keidro.listeners;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.game.GameManager;
import dev.m1n1don.keidro.scoreboard.Teams;
import dev.m1n1don.keidro.scoreboard.fastboard.FastBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnection implements Listener
{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        final Player p = e.getPlayer();

        if (!GameManager.get().isGame()) return;

        Teams.get().joinTeam(p, Teams.TeamType.JAIL);

        // teleport
        KeidroPlugin.getPlugin().getBoards().put(p.getUniqueId(), new FastBoard(p));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        final Player p = e.getPlayer();

        if (!GameManager.get().isGame()) return;
        if (!KeidroPlugin.getPlugin().getBoards().containsKey(p.getUniqueId())) return;

        Teams.get().leaveTeam(p);

        final FastBoard board = KeidroPlugin.getPlugin().getBoards().remove(p.getUniqueId());
        if (board != null) board.delete();
    }
}