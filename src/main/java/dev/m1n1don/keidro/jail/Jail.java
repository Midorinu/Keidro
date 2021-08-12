package dev.m1n1don.keidro.jail;

import com.sk89q.worldedit.regions.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Jail
{
    private final Location maximum, minimum;
    private final Region region;

    public Jail(Player player, Region region)
    {
        this.maximum = new Location(player.getWorld(), region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ());
        this.minimum = new Location(player.getWorld(), region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ());
        this.region = region;
    }

    public Location getMaximum()
    {
        return maximum;
    }

    public Location getMinimum()
    {
        return minimum;
    }

    public Region getRegion()
    {
        return region;
    }
}