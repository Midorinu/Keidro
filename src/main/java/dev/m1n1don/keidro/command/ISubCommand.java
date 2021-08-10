package dev.m1n1don.keidro.command;

import org.bukkit.command.CommandSender;

public interface ISubCommand
{
    void execute(CommandSender sender, String[] args);
}