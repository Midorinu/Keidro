package dev.m1n1don.keidro.game;

import dev.m1n1don.keidro.KeidroPlugin;
import dev.m1n1don.keidro.jail.Jail;
import dev.m1n1don.keidro.scoreboard.Teams;
import dev.m1n1don.keidro.scoreboard.fastboard.FastBoard;
import dev.m1n1don.keidro.util.Item;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Game
{
    private static Game instance;

    public int countdown = 600;
    private BukkitRunnable timer;

    private Jail jail;

    public static synchronized Game getGame()
    {
        return instance == null ? instance = new Game() : instance;
    }

    public void start()
    {
        if (jail == null)
        {
            Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.RED + "牢獄が設定されていないため開始できません");
            return;
        }

        jail.getMaximum().getWorld().setDifficulty(Difficulty.PEACEFUL);
        jail.getMaximum().getWorld().setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);

        for (Player player : Bukkit.getOnlinePlayers())
        {
            KeidroPlugin.getPlugin().getBoards().put(player.getUniqueId(), new FastBoard(player));
            if (!Teams.get().isTeamJoined(player, Teams.TeamType.ADMIN))
            {
                player.setGameMode(GameMode.ADVENTURE);
                if (!Teams.get().isTeamJoined(player, Teams.TeamType.POLICE)) Teams.get().joinTeam(player, Teams.TeamType.DOROBO);
            }
        }

        for (FastBoard board : KeidroPlugin.getPlugin().getBoards().values())
        {
            board.updateTitle(ChatColor.DARK_GREEN + "ケイドロ");
            board.updateLines(
                    "",
                    ChatColor.GOLD + "人数",
                    ChatColor.AQUA + "  警察" + ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + Teams.get().getTeam(Teams.TeamType.POLICE).getSize(),
                    ChatColor.WHITE + "  泥棒" + ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + Teams.get().getTeam(Teams.TeamType.DOROBO).getSize(),
                    ChatColor.GRAY + "  牢獄" + ChatColor.DARK_GRAY + " >> " + ChatColor.YELLOW + Teams.get().getTeam(Teams.TeamType.JAIL).getSize(),
                    "",
                    ChatColor.GOLD + "ゲーム時間",
                    ChatColor.YELLOW + "  10分0秒",
                    ""
            );
        }

        GameManager.get().setGameState(GameManager.GameState.READY);

        new BukkitRunnable()
        {
            int before = 3;

            @Override
            public void run()
            {
                if (before == 0)
                {
                    GameManager.get().setGameState(GameManager.GameState.GAME);
                    for (Player player : Bukkit.getOnlinePlayers())
                    {
                        player.sendTitle(ChatColor.GOLD + "ゲーム開始", ChatColor.GRAY + "ケイドロ");
                        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                        if (Teams.get().isTeamJoined(player, Teams.TeamType.POLICE))
                        {
                            player.getInventory().setHelmet(new Item(Material.DIAMOND_HELMET).setDisplayName(ChatColor.YELLOW + "サングラス").build());
                            player.getInventory().setChestplate(new Item(Material.DIAMOND_CHESTPLATE).setDisplayName(ChatColor.YELLOW + "チェストプレート").build());
                            player.getInventory().setLeggings(new Item(Material.DIAMOND_LEGGINGS).setDisplayName(ChatColor.YELLOW + "レギンス").build());
                            player.getInventory().setBoots(new Item(Material.DIAMOND_BOOTS).setDisplayName(ChatColor.YELLOW + "ブーツ").build());
                        }
                    }
                    this.cancel();

                    timer = new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            if (countdown == 0)
                            {
                                Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.GREEN + "泥棒側の勝利！");
                                end();
                            }
                            else setTime((int) Math.floor(countdown / 60), countdown - (int) Math.floor(countdown / 60) * 60);

                            for (Player player : Bukkit.getOnlinePlayers())
                            {
                                if (Teams.get().isTeamJoined(player, Teams.TeamType.JAIL) && !checkArea(jail.getMaximum(), jail.getMinimum(), player.getLocation()))
                                {
                                    Bukkit.broadcastMessage(KeidroPlugin.PREFIX + ChatColor.GREEN + player.getName() + "が脱獄した！");
                                    Teams.get().joinTeam(player, Teams.TeamType.DOROBO);
                                }
                            }

                            countdown--;
                        }
                    };
                    timer.runTaskTimer(KeidroPlugin.getPlugin(), 0L, 20L);
                }
                else
                {
                    for (Player player : Bukkit.getOnlinePlayers())
                    {
                        player.sendTitle(ChatColor.YELLOW + String.valueOf(before), ChatColor.GRAY + "ケイドロ");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                }
                before--;
            }
        }.runTaskTimer(KeidroPlugin.getPlugin(), 0L, 20L);
    }

    public void end()
    {
        GameManager.get().setGameState(GameManager.GameState.NONE);

        timer.cancel();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.sendTitle(ChatColor.GOLD + "ゲーム終了", ChatColor.GRAY + "ケイドロ");
            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
            if (!Teams.get().isTeamJoined(player, Teams.TeamType.ADMIN)) player.getInventory().clear();
        }
    }

    public void reset()
    {
        KeidroPlugin.getPlugin().getBoards().clear();
        Teams.get().resetTeams();
        if (!timer.isCancelled()) timer.cancel();
        countdown = 600;

        for (FastBoard board : KeidroPlugin.getPlugin().getBoards().values()) board.delete();
        KeidroPlugin.getPlugin().getBoards().clear();

        setJail(null);

        Teams.get().setTeams();
        GameManager.get().setGameState(GameManager.GameState.NONE);
    }

    public BukkitRunnable getTimer()
    {
        return timer;
    }

    private void setTime(int minutes, int second)
    {
        if (minutes < 20)
        {
            if (second < 10)
            {
                for (FastBoard board : KeidroPlugin.getPlugin().getBoards().values())
                {
                    board.updateLine(7, ChatColor.YELLOW + "   " + String.valueOf(minutes).replace(".0", "") + "分 " + String.valueOf(second).replace(".0", "") + "秒");
                }
            }
            else
            {
                for (FastBoard board : KeidroPlugin.getPlugin().getBoards().values())
                {
                    board.updateLine(7, ChatColor.YELLOW + "   " + String.valueOf(minutes).replace(".0", "") + "分" + String.valueOf(second).replace(".0", "") + "秒");
                }
            }
        }
    }

    private boolean checkArea(final Location loc1, final Location loc2, final Location targetLoc)
    {
        if ((targetLoc.getBlockX() >= loc1.getBlockX() && targetLoc.getBlockX() <= loc2.getBlockX()) || (targetLoc.getBlockX() <= loc1.getBlockX() && targetLoc.getBlockX() >= loc2.getBlockX()))
            if ((targetLoc.getBlockZ() >= loc1.getBlockZ() && targetLoc.getBlockZ() <= loc2.getBlockZ()) || (targetLoc.getBlockZ() <= loc1.getBlockZ() && targetLoc.getBlockZ() >= loc2.getBlockZ())) return true;
        return false;
    }

    public void setJail(Jail loc)
    {
        jail = loc;
    }

    public Jail getJail()
    {
        return jail;
    }
}