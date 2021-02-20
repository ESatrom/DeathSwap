package me.Mindarius.DeathSwap;

import net.md_5.bungee.api.ChatColor;

/**
 * Ticks twice per second.
 */
public class Ticker {
	Main plugin;
	public int runTime = 0;
	boolean init = true;
	public Ticker(Main plugin) { 
		this.plugin=plugin;
	}
	
	public void tick() {
		if(Main.startFreeze&&init) {
			if(runTime==10) {
				plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "GO!");
				runTime = 0;
				init = false;
			} else {
				plugin.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "" + (10-(runTime/20)) + "...");
			}
		} else {
			if(runTime==Main.intervalSeconds) {
				plugin.swap();
				runTime = 0;
			} else if(runTime>Main.intervalSeconds-10) {
				plugin.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Swap in " + (Main.intervalSeconds-runTime) + "...");
			}
		}
		runTime++;
	}
}
