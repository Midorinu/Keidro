package dev.m1n1don.keidro.game;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.scoreboard.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameManager
{
    private static GameManager.GameState game = GameManager.GameState.NONE;
    private static GameManager instance = null;

    public static GameManager get()
    {
        return instance == null ? instance = new GameManager() : instance;
    }

    public boolean isGame(GameManager.GameState game)
    {
        return GameManager.game.equals(game);
    }

    public boolean isGame()
    {
        return game.equals(GameManager.GameState.GAME);
    }

    public void setGameState(GameManager.GameState game)
    {
        GameManager.game = game;
    }

    public GameManager.GameState getGameState()
    {
        return game;
    }

    public boolean isJoined(Player player)
    {
        return KeidroPlugin.getPlugin().getBoards().containsKey(player.getUniqueId());
    }

    public static void broadcastMessage(String... messages)
    {
        final StringBuilder builder = new StringBuilder();
        for (String message : messages) builder.append(message).append("\n");
        for (Player player : Bukkit.getOnlinePlayers()) player.sendMessage(builder.delete(builder.length() - 2, builder.length()).toString());
    }

    public static void broadcastMessage(Teams.TeamType team, String... messages)
    {
        final StringBuilder builder = new StringBuilder();
        for (String message : messages) builder.append(message).append("\n");
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (!Teams.get().isTeamJoined(player, team)) return;
            player.sendMessage(builder.delete(builder.length() - 2, builder.length()).toString());
        }
    }

    public static void broadcastTitle(String title, String subtitle)
    {
        for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(title, subtitle);
    }

    public static void broadcastTitle(Teams.TeamType team, String title, String subtitle)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (!Teams.get().isTeamJoined(player, team)) return;
            player.sendTitle(title, subtitle);
        }
    }

    /*
    public static void broadcastActionBar(String content)
    {
        for (UUID uuids : EscGame.getPlugin().getBoards().keySet())
        {
            ActionbarAPI.sendActionbar(Bukkit.getPlayer(uuids), content);
        }
    }

    public static void broadcastActionBar(Teams.TeamType team, String content)
    {
        for (UUID uuids : EscGame.getPlugin().getBoards().keySet())
        {
            final Player player = Bukkit.getPlayer(uuids);
            if (!Teams.get().isTeamJoined(player, team)) return;
            ActionbarAPI.sendActionbar(player, content);
        }
    }
     */

    public static void broadcastSound(Sound sound, int volume, int pitch)
    {
        for (Player player : Bukkit.getOnlinePlayers()) player.playSound(player.getLocation(), sound, volume, pitch);
    }


    public enum GameState
    {
        READY,
        GAME,
        NONE
    }
}