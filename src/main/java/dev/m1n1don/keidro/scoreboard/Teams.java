package dev.m1n1don.keidro.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Teams
{
    private static Team dorobo, police, jail, admin;
    private static Teams instance = null;


    private final Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

    public static Teams get()
    {
        return instance == null ? instance = new Teams() : instance;
    }

    public void setTeams()
    {
        setTeam(dorobo, "Dorobo", ChatColor.WHITE);
        setTeam(police, "Police", ChatColor.BLUE);
        setTeam(jail, "Jail", ChatColor.GRAY);
        setTeam(admin, "Admin", ChatColor.GOLD);
    }

    private void setTeam(Team team, String name, ChatColor color)
    {
        team = board.getTeam(name);
        if (team == null)
        {
            team = board.registerNewTeam(name);
            team.setSuffix(ChatColor.RESET.toString());
            team.setColor(color);
            team.setDisplayName(name);
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
            team.setAllowFriendlyFire(false);
        }
    }

    public void resetTeams()
    {
        dorobo.unregister();
        police.unregister();
        jail.unregister();
        admin.unregister();
    }

    public Team getTeam(TeamType team)
    {
        return team.getTeam();
    }

    public Team getTeam(Player player)
    {
        return Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
    }

    public boolean isTeamJoined(Player player)
    {
        for (TeamType team : TeamType.values()) return isTeamJoined(player, team);
        return false;
    }

    public boolean isTeamJoined(Player player, TeamType team)
    {
        return team.getTeam().hasEntry(player.getName());
    }

    public void joinTeam(Player player, TeamType team)
    {
        team.getTeam().addEntry(player.getName());
    }

    public void leaveTeam(Player player, TeamType team)
    {
        team.getTeam().removeEntry(player.getName());
    }

    public void leaveTeam(Player player)
    {
        for (TeamType team : TeamType.values()) if (isTeamJoined(player, team)) leaveTeam(player, team);
    }

    public enum TeamType
    {
        DOROBO(dorobo),
        POLICE(police),
        JAIL(jail),
        ADMIN(admin);

        private final Team team;

        TeamType(Team team)
        {
            this.team = team;
        }

        public Team getTeam()
        {
            return team;
        }
    }
}