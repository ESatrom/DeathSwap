package me.Mindarius.DeathSwap;

/**
 * Class to handle regularly ticked operations.
 */
public class Ticker {
	private int swapTime = 0; //Time since last Swap
	private boolean init = true; //If true just does a countdown
	
	public void tick() {
		if(Main.isGRFreeze()&&init) { //If I should do a count-down
			if(getSwapTime()==10) { //If it's time to go
				Main.server().broadcastMessage("§a§lGO!");
				swapTime = 0;
				init = false;
			} else {
				Main.server().broadcastMessage("§a§l" + (10-getSwapTime()) + "..."); //Initial Countdown
			}
		} else { //If in-game
			if(getSwapTime()==Main.getGRInterval()) { //If time for swap
				Main.swap();
				swapTime = 0;
			} else if(getSwapTime()>=Main.getGRInterval()-10) { //If within 10 seconds of swap
				Main.server().broadcastMessage("§c§lSwap in " + (Main.getGRInterval()-getSwapTime()) + "...");
			}
		}
		swapTime = getSwapTime() + 1;
	}

	public int getSwapTime() { return swapTime; }
}
