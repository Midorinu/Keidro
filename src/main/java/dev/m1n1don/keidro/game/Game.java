package dev.m1n1don.keidro.game;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.scoreboard.Teams;
import org.bukkit.scheduler.BukkitRunnable;

public class Game
{
    private static Game instance;

    private BukkitRunnable timer;

    public static synchronized Game getGame()
    {
        return instance == null ? instance = new Game() : instance;
    }

    public void start()
    {
        GameManager.get().setGameState(GameManager.GameState.GAME);
    }

    public void end()
    {
        GameManager.get().setGameState(GameManager.GameState.NONE);
    }

    public void reset()
    {
        KeidroPlugin.getPlugin().getBoards().clear();
        Teams.get().resetTeams();
        if (!timer.isCancelled()) timer.cancel();

        Teams.get().setTeams();
        GameManager.get().setGameState(GameManager.GameState.NONE);
    }
}