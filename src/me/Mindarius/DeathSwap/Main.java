package me.Mindarius.DeathSwap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.Mindarius.DeathSwap.commands.CommandRTP;
import me.Mindarius.DeathSwap.commands.CommandRule;
import me.Mindarius.DeathSwap.commands.CommandStart;

/**
 * Play DeathSwap with any number of friends!
 * 
 * @author MC: Minecraftmage113, Discord: Hoohoo2#4704, Additional Aliases: Mindarius
 * @version 20/Feb/2021
 */
public class Main extends JavaPlugin {
	
	public static boolean doubleFirstInterval = false;
	public static boolean startFreeze = true;
	public static boolean anyStart = false;
	public static int intervalSeconds = 20*60*5;
	public static int radius = 10000;
	
	private Ticker ticker;
	public List<Player> players = new ArrayList<>();
	
	@Override
	public void onEnable() {
		if(!getDataFolder().exists()) { try { getDataFolder().mkdir(); } catch (Exception e) { } }
		Saver.load(getDataFolder().getPath());
		getCommand("Start").setExecutor(new CommandStart(this));
		getCommand("Rule").setExecutor(new CommandRule(this));
		getCommand("Rule").setTabCompleter(new RuleTabCompleter());
		getCommand("Rtp").setExecutor(new CommandRTP(this));
		getServer().getPluginManager().registerEvents(new ListenerDeath(this), this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override public void run() { if(ticker!=null) { ticker.tick(); } }
		}, 0, 20);
	}
	
	@Override public void onDisable() { Saver.save(getDataFolder().getPath());}
	
	public void swap() {
		Random r = new Random();
		List<Location> locs = new ArrayList<>();
		for(Player p : players) {
			locs.add(p.getLocation());
		}
		if(locs.size()<=1) {
			getServer().broadcastMessage("§cNot enough players for DeathSwap (Min 2)");
			return;
		}
		boolean happy = false;
		while(!happy) {
			locs.add(r.nextInt(locs.size()), locs.remove(r.nextInt(locs.size())));
			happy=true;
			for(int i = 0; i < players.size(); i++) { if(players.get(i).getLocation().equals(locs.get(i))) { happy = false; break; } }
		}
		for(int i = 0; i < players.size(); i++) { players.get(i).teleport(locs.get(i)); }
	}

	public void initializeTicker() { ticker = new Ticker(this); }
	public void endTicker() { ticker = null; }
}