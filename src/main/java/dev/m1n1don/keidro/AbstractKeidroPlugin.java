package dev.m1n1don.keidro;

import dev.m1n1don.keidro.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AbstractKeidroPlugin extends JavaPlugin
{
    protected void registerListeners(Listener... listeners)
    {
        for (Listener listener : listeners) getServer().getPluginManager().registerEvents(listener, this);
    }

    protected void registerCommands(Command... commands)
    {
        for (Command command : commands)
        {
            getCommand(command.getName()).setExecutor(command.getExecutor());
            if (command.getTabCompleter() == null) return;
            getCommand(command.getName()).setTabCompleter(command.getTabCompleter());
        }
    }
}