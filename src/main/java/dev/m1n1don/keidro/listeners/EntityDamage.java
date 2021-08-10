package dev.m1n1don.keidro.listeners;

import dev.m1n1don.keidro.game.GameManager;
import dev.m1n1don.keidro.scoreboard.Teams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener
{

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        if (!GameManager.get().isGame()) return;
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (!GameManager.get().isJoined((Player) e.getEntity())) return;

        e.setDamage(0);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if (!GameManager.get().isGame()) return;
        if (!(e.getEntity() instanceof Player)) return;

        final Player p = (Player) e.getEntity();
        final Player d = (Player) e.getDamager();

        if (Teams.get().isTeamJoined(p, Teams.TeamType.DOROBO) && Teams.get().isTeamJoined(d, Teams.TeamType.POLICE)) p.setHealth(0);
    }
}