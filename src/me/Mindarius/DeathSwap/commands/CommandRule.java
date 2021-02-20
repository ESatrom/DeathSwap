package me.Mindarius.DeathSwap.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Mindarius.DeathSwap.Main;

public class CommandRule implements CommandExecutor {
	protected Main plugin;
	
	public CommandRule(Main plugin) { this.plugin = plugin; }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==0||(args.length==1&&args[0].toLowerCase().equals("help"))) {
			sender.sendMessage("doubleFirst <true/false> - whether or not to double the interval before the first swap. For those who want prep time.\n"
					+ "freeze <true/false> - whether or not to freeze players upon the start command being issued, giving a countdown to game start.\n"
					+ "anyoneStart <true/false> - whether or not to allow anyone to use /start.\n"
					+ "radius <number> - half side-length of the square centered on world spawn in which RTP will place players.\n"
					+ "interval <number>- the duration in seconds between each swap.");
			return true;
		}
		if(args.length!=2) { return false; }
		switch(args[0].toLowerCase()) {
		case "doublefirst":
			try {
				Main.doubleFirstInterval = bool(args[1]);
				sender.sendMessage("doubleFirst set to " + bool(args[1]));
			}
			catch(IllegalArgumentException e) { return false; }
			break;
		case "anyonestart":
			try {
				Main.anyStart = bool(args[1]);
				sender.sendMessage("anyoneStart set to " + bool(args[1]));
			}
			catch(IllegalArgumentException e) { return false; }
			break;
		case "freeze":
			try {
				Main.startFreeze = bool(args[1]);
				sender.sendMessage("freeze set to " + bool(args[1]));
			}
			catch(IllegalArgumentException e) { return false; }
			break;
		case "radius":
			try {
				Main.radius = Integer.parseInt(args[1]); 
				sender.sendMessage("Spawn radius set to " + Integer.parseInt(args[1]));
			}
			catch(NumberFormatException e) { return false; }
			break;
		case "interval":
			try {
				Main.intervalSeconds = Integer.parseInt(args[1]); 
				sender.sendMessage("Interval set to " + Integer.parseInt(args[1]) + " seconds");
			}
			catch(NumberFormatException e) { return false; }
			break;
		default:
			sender.sendMessage("§cUnrecognized setting");
		}
		return true;
	}
	
	private boolean bool(String in) throws IllegalArgumentException {
		System.out.println("\""+in.toLowerCase()+"\"");
		switch(in.toLowerCase()) {
		case "y":
		case "yes":
		case "t":
		case "true":
			return true;
		case "n":
		case "no":
		case "f":
		case "false":
			return false;
		default:
			throw new IllegalArgumentException();				
		}
	}
	
}
