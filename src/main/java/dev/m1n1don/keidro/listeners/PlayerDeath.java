package dev.m1n1don.keidro.listeners;

import dev.m1n1don.keidro.KeidroPlugin;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;
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
        final Player p = e.getEntity();

    }

    private void respawn(Player player)
    {
        player.getServer().getScheduler().scheduleSyncDelayedTask(KeidroPlugin.getPlugin(), () -> {
            if (player.isDead())
                ((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
        });
    }
}