package me.Mindarius.DeathSwap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.Mindarius.DeathSwap.commands.CommandRTP;
import me.Mindarius.DeathSwap.commands.CommandRule;
import me.Mindarius.DeathSwap.commands.CommandSpec;
import me.Mindarius.DeathSwap.commands.CommandStart;
import me.Mindarius.DeathSwap.commands.DSCommand;

/**
 * Play DeathSwap with any number of friends!
 * 
 * @author MC: Minecraftmage113, Discord: Hoohoo2#4704, Additional Aliases: Mindarius
 * @version 20/Feb/2021
 */
public class Main extends JavaPlugin {
	
	public static boolean 	doubleFirstInterval = false,
							startFreeze = true,
							anyStart = false,
							randomStart = true; //Boolean game settings
	public static int 	intervalSeconds = 300,
						killInterval = 10,
						radius = 4000; //Integer game settings

	public static Random r = new Random(); //A random, for random numbers
	private static Ticker ticker; //The ticker, handles passive plugin operations (countdowns & stuff)
	private static Main main; //The plugin, as there should only ever be one in an instance of a server this is made static
	
	public static List<Player> players = new ArrayList<>(); //List of players still in the game
	public static Map<Player, Player> swapList = new HashMap<Player, Player>(); //Key: player that was swapped, Value: player they were last swapped to.
	
	public Main() { main = this; }
	
	@Override
	public void onEnable() {
		if(!getDataFolder().exists()) { try { getDataFolder().mkdir(); } catch (Exception e) {} } //If there is no data folder, it makes one. This is to save settings in. The try catch-all do-nothing is a terrible practice.
		Saver.load(getDataFolder().getPath()); //Attempt to load preconfigured game settings
		registerCommands(new CommandStart(),
				new CommandRule(),
				new CommandRTP(),
				new CommandSpec()); //register all the commands
		server().getPluginManager().registerEvents(new ListenerElimination(), this); //Register the elimination listener
		server().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override public void run() { if(ticker!=null) { ticker.tick(); } }
		}, 0, 20); //Set it up so that the ticker will tick every second if it exists.
	}
	
	@Override public void onDisable() { Saver.save(getDataFolder().getPath()); } //Save configurations when closed.
	
	/** Swaps all active players */
	public static void swap() {
		List<Location> locs = new ArrayList<>(); //(to be) Locations of all players in game
		List<Player> ps = new ArrayList<>(); //(to be) players in corresponding order to locations list
		for(Player p : players) { //repeat for all in-game players
			locs.add(p.getLocation());
			ps.add(p);
		}
		if(locs.size()<=1) { //If 1 or fewer players
			server().broadcastMessage("§cNot enough players for Swap (Min 2)");
			return;
		}
		boolean uniqueLocations = false;
		while(!uniqueLocations) {
			int from = r.nextInt(locs.size()), to = r.nextInt(locs.size()); //Select random to/from positions
			locs.add(to, locs.remove(from)); //switches position of a location according to the predetermined random values
			ps.add(to, ps.remove(from)); //matches player list positions to location list positions
			uniqueLocations=true;
			for(int i = 0; i < players.size(); i++) { if(players.get(i).getLocation().equals(locs.get(i))) { uniqueLocations = false; break; } } //If any player is targeted to their own location, try again.
		}
		for(int i = 0; i < players.size(); i++) {
			players.get(i).teleport(locs.get(i)); //teleport player
			swapList.put(players.get(i), ps.get(i)); //log teleport target
		}
	}
	
	/** Start the game */
	public static void initializeTicker() { ticker = new Ticker(); }
	/** End the game */
	public static void endTicker() { 
		ticker = null;
		swapList.clear();
	}

	/** @return the object of the plugin */
	public static Main get() { return main; }
	/** @return the server */
	public static Server server() { return main.getServer(); }
	
	/** @param commands - registered by method*/
	private void registerCommands(DSCommand... commands) { for(DSCommand c : commands) { this.getCommand(c.getName()).setExecutor(c); } }
	
	/**Whether the last swap was recent enough to be considered cause of death*/
	public static boolean lastSwap() { return ticker.swapTime<killInterval; }
	/** Whether there is an active game*/
	public static boolean gameOn() { return players.size()>0; }
}