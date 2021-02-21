package me.Mindarius.DeathSwap.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Mindarius.DeathSwap.Main;

public class CommandRTP implements CommandExecutor {
	protected Main plugin;
	public CommandRTP(Main plugin) { this.plugin = plugin; }

	Random r = new Random();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)||plugin.players.contains(sender)) { return false; }
		Player p = (Player) sender;
		World w = p.getWorld();
		Location target = null;
		List<Biome> biomeBlackList = new ArrayList<>();
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
		while(target==null||biomeBlackList.contains(w.getBiome(target.getBlockX(), target.getBlockY(), target.getBlockZ()))) {
			target = w.getHighestBlockAt(w.getSpawnLocation().add(r.nextInt(Main.radius*2)-Main.radius, 0, r.nextInt(Main.radius*2)-Main.radius)).getLocation().add(0, 1, 0);
		}
		p.teleport(target);
		return true;
	}
	
}
