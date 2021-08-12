package dev.m1n1don.keidro.commands.keidro.subs;

import dev.m1n1don.keidro.command.ISubCommand;
import org.bukkit.command.CommandSender;

public class Help implements ISubCommand
{

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        sendMessage(
                sender,
                " ",
                " /keidro help",
                " /keidro start",
                " /keidro end",
                " /keidro reset",
                " /keidro police <number/player>",
                " /keidro timer <set/add> <number>"
        );
    }

    private void sendMessage(CommandSender sender, String... messages)
    {
        final StringBuilder builder = new StringBuilder();
        for (String message : messages) builder.append(message).append("\n");
        sender.sendMessage(builder.substring(0, builder.toString().length() - 2));
    }
}