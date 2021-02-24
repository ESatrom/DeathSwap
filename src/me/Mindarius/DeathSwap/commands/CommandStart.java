package me.Mindarius.DeathSwap.commands;

import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Mindarius.DeathSwap.Main;
import net.md_5.bungee.api.ChatColor;

public class CommandStart extends DSCommand {
	public CommandStart() { super("Start", null); }

	Server server = Main.get().getServer();
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender.isOp()||Main.anyStart)) {
			sender.sendMessage("§cInsufficient permissions to perform command.");
			return true;
		}
		if(Main.gameOn()) { return false; }
		server.dispatchCommand(server.getConsoleSender(), "advancement revoke @a everything"); //Clears advances to make progress more evident
		server.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DeathSwap interval: " + Math.floor(Main.intervalSeconds/0.6)/100.0 + " minutes!");
		Main.players.clear(); //This should already be clear
		
		if(server.getOnlinePlayers().size()<=1) {
			server.broadcastMessage("§cNot enough players for DeathSwap (Min 2)");
			return true;
		}
		
		for(Player p : server.getOnlinePlayers()) { if(Main.randomStart) { server.dispatchCommand(p, "rtp"); } } //Needs to be separate so it doesn't think a game is running
		for(Player p : server.getOnlinePlayers()) {
			Main.players.add(p); //registers player in game
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setSaturation(2);
			for(PotionEffect e : p.getActivePotionEffects()) { p.removePotionEffect(e.getType()); } //Clears all potion effects
			p.getInventory().clear();
			p.setGameMode(GameMode.SURVIVAL);
			if(Main.startFreeze) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*10, 4)); //Gives damage immunity
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*10, 10));
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*10, -10)); //Makes it so they can't jump to circumvent slowness
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20*10, 100));
			}
		}
		if(!Main.startFreeze) { server.broadcastMessage("§2§lDeathSwap has begun!"); }
		Main.initializeTicker();
		if(!Main.randomStart) { Main.swap(); }
		return true;
	}
	
}
