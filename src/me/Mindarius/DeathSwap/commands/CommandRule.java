package me.Mindarius.DeathSwap.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.Mindarius.DeathSwap.Main;
import me.Mindarius.DeathSwap.completers.RuleCompleter;

public class CommandRule extends DSCommand {
	public CommandRule() { super("Rule", new RuleCompleter()); }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==0||(args.length==1&&args[0].toLowerCase().equals("help"))) { //Displays help
			sender.sendMessage("doubleFirst <true/false> - whether or not to double the interval before the first swap. For those who want prep time.\n"
					+ "freeze <true/false> - whether or not to freeze players upon the start command being issued, giving a countdown to game start.\n"
					+ "anyoneStart <true/false> - whether or not to allow anyone to use /start.\n"
					+ "randomStart <true/false> - whether or not to rtp all players on start (opposed to a swap).\n"
					+ "radius <number> - half side-length of the square centered on world spawn in which RTP will place players. A larger radius can cause more lag due to need to load teleport target location.\n"
					+ "interval <number>- the duration in seconds between each swap."
					+ "killInterval <number>- the interval in seconds after each swap to consider a death an intentional kill.");
			return true;
		}
		if(args.length!=2) { return false; } //We only take a setting and a value here
		switch(args[0].toLowerCase()) { //Switches on the supplied rule, same process for each setting
		case "doublefirst": //Check what it be
			try {
				Main.doubleFirstInterval = bool(args[1]); //Set the thing to provided value
				sender.sendMessage("doubleFirst set to " + bool(args[1])); //Echo to sender
			}
			catch(IllegalArgumentException e) { return false; } //If they gave me a bad value I will complain to them
			break; //Close this off so it doesn't try doing another setting with this value.
		case "randomstart":
			try {
				Main.randomStart = bool(args[1]);
				sender.sendMessage("randomStart set to " + bool(args[1]));
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
		case "killinterval":
			try {
				Main.killInterval = Integer.parseInt(args[1]); 
				sender.sendMessage("killInterval set to " + Integer.parseInt(args[1]) + " seconds");
			}
			catch(NumberFormatException e) { return false; }
			break;
		default:
			sender.sendMessage("§cUnrecognized setting"); //If they typoed or smth
		}
		return true;
	}
	
	/**
	 * @param in - String to parse as boolean
	 * @return boolean value
	 * @throws IllegalArgumentException - not a nice boolean
	 */
	private boolean bool(String in) throws IllegalArgumentException {
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
