package dev.m1n1don.keidro.listeners;

import dev.m1n1don.keidro.game.GameManager;
import dev.m1n1don.keidro.scoreboard.Teams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener
{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (!GameManager.get().isGame()) return;

        final Player p = (Player) e.getWhoClicked();

        if (!Teams.get().isTeamJoined(p, Teams.TeamType.POLICE)) return;

        switch (e.getCurrentItem().getType())
        {
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
                e.setCancelled(true);
                break;
        }
    }
}