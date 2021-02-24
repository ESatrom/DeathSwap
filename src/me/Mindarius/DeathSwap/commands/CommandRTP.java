package me.Mindarius.DeathSwap.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Mindarius.DeathSwap.Main;

public class CommandRTP extends DSCommand {
	public CommandRTP() { super("Rtp", null); }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)||Main.gameOn()) { return false; } //Can't be run by console or while in game
		Player p = (Player) sender;
		World w = p.getWorld();
		Location target = null;
		List<Biome> biomeBlackList = new ArrayList<>(); //Biomes which players shouldn't be put into by an RTP
		biomeBlackList.add(Biome.OCEAN);
		biomeBlackList.add(Biome.COLD_OCEAN);
		biomeBlackList.add(Biome.DEEP_COLD_OCEAN);
		biomeBlackList.add(Biome.DEEP_FROZEN_OCEAN);
		biomeBlackList.add(Biome.DEEP_LUKEWARM_OCEAN);
		biomeBlackList.add(Biome.DEEP_OCEAN);
		biomeBlackList.add(Biome.DEEP_WARM_OCEAN);
		biomeBlackList.add(Biome.FROZEN_OCEAN);
		biomeBlackList.add(Biome.LUKEWARM_OCEAN);
		biomeBlackList.add(Biome.WARM_OCEAN);
		biomeBlackList.add(Biome.RIVER);
		biomeBlackList.add(Biome.FROZEN_RIVER);
		biomeBlackList.add(Biome.DESERT_LAKES);
		int tries = 0;
		while(target==null||biomeBlackList.contains(w.getBiome(target.getBlockX(), target.getBlockY(), target.getBlockZ()))) { //Repeat until you get a good location
			target = w.getHighestBlockAt(w.getSpawnLocation().add(Main.r.nextInt(Main.radius*2)-Main.radius, 0, Main.r.nextInt(Main.radius*2)-Main.radius)).getLocation().add(0, 1, 0);
			tries++;
			if(tries>=Main.radius) {
				sender.sendMessage("Couldn't find a suitable starting location in a reasonable amount of tries, this may have been caused due to too small an RTP radius. If you know your radius is wide enough, just try again.");
				break; //If it takes too long to RTP someone, just stop instead of possibly crashing the server.
			}
		}
		p.teleport(target);
		return true;
	}
	
}
