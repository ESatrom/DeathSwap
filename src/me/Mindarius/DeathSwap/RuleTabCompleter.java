package me.Mindarius.DeathSwap;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class RuleTabCompleter implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(!command.getName().equalsIgnoreCase("rule")) { return null; }
		List<String> result = new ArrayList<>();
		if(args.length==1) {
			result.add("interval");
			result.add("anyoneStart");
			result.add("randomStart");
			result.add("radius");
			result.add("freeze");
			result.add("doubleFirst");
		} else if(args.length==2&&(args[0].equalsIgnoreCase("doublefirst")||args[0].equalsIgnoreCase("freeze")||args[0].equalsIgnoreCase("anyonestart")||args[0].equalsIgnoreCase("randomStart"))) {
			result.add("false");
			result.add("true");
		}
		return result;
	}

}
