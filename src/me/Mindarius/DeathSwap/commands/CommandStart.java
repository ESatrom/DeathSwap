package me.Mindarius.DeathSwap.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Mindarius.DeathSwap.Main;
import net.md_5.bungee.api.ChatColor;

public class CommandStart implements CommandExecutor {
	protected Main plugin;
	
	public CommandStart(Main plugin) { this.plugin = plugin; }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender.isOp()||Main.anyStart)) {
			sender.sendMessage("§cInsufficient permissions to perform command.");
			return true;
		}
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "advancement revoke @a everything");
		plugin.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DeathSwap interval: " + Math.floor(Main.intervalSeconds/0.6)/100.0 + " minutes!");
		plugin.players.clear();
		for(Player p : plugin.getServer().getOnlinePlayers()) {
			if(Main.randomStart) { plugin.getServer().dispatchCommand(p, "rtp"); }
			plugin.players.add(p);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setSaturation(2);
			for(PotionEffect e : p.getActivePotionEffects()) { p.removePotionEffect(e.getType()); }
			p.getInventory().clear();
			p.setGameMode(GameMode.SURVIVAL);
			if(Main.startFreeze) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*10, 4));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*10, 10));
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*10, -10));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20*10, 100));
			} else {
				plugin.getServer().broadcastMessage("§2§lDeathSwap has begun!");
			}
		}
		plugin.initializeTicker();
		plugin.swap();
		return true;
	}
	
}
