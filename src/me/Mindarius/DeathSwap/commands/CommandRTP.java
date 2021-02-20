package me.Mindarius.DeathSwap.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
		while(target==null||w.getBlockAt(target).getType().equals(Material.WATER)||w.getBlockAt(target).getType().equals(Material.LAVA)) {
			target = w.getHighestBlockAt(w.getSpawnLocation().add(r.nextInt(Main.radius*2)-Main.radius, 0, r.nextInt(Main.radius*2)-Main.radius)).getLocation().add(0, 1, 0);
		}
		p.teleport(target);
		return true;
	}
	
}
