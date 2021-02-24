package me.Mindarius.DeathSwap;

import org.bukkit.Server;

import net.md_5.bungee.api.ChatColor;

/**
 * Class to handle regularly ticked operations.
 */
public class Ticker {
	public int swapTime = 0; //Time since last Swap
	boolean init = true; //If true just does a countdown
	Server server = Main.server();
	
	public void tick() {
		if(Main.startFreeze&&init) { //If I should do a count-down
			if(swapTime==10) { //If it's time to go
				server.broadcastMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "GO!");
				swapTime = 0;
				init = false;
			} else {
				server.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "" + (10-swapTime) + "..."); //Initial Countdown
			}
		} else { //If in-game
			if(swapTime==Main.intervalSeconds) { //If time for swap
				Main.swap();
				swapTime = 0;
			} else if(swapTime>=Main.intervalSeconds-10) { //If within 10 seconds of swap
				server.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Swap in " + (Main.intervalSeconds-swapTime) + "...");
			}
		}
		swapTime++;
	}
}
