package me.Mindarius.DeathSwap.completers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.Mindarius.DeathSwap.Main;

public class SpecCompleter implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> result = new ArrayList<>();
		if(args.length==1) { //We only need stuff for the first argument.
			for(Player p : Main.getPlayers()) { result.add(p.getDisplayName()); } //Gives all in-game players
		}
		return result;
	}

}
