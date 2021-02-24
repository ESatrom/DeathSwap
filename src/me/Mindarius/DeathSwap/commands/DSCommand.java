package me.Mindarius.DeathSwap.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import me.Mindarius.DeathSwap.Main;

/**
 * Class exists to help in automation of commands, specifically registration.
 */
public abstract class DSCommand implements CommandExecutor {
	private String name;
	protected DSCommand(String name, TabCompleter c) {
		this.name = name;
		if(c!=null) { Main.get().getCommand(getName()).setTabCompleter(c); }
	}
	public String getName() { return name; }
}
