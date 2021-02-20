package me.Mindarius.DeathSwap;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class ListenerDeath implements Listener {
	Main plugin;
	public ListenerDeath(Main plugin) { this.plugin = plugin; }
	@EventHandler public void onDeath(PlayerDeathEvent event) {
		if(plugin.players.remove(event.getEntity())) {
			event.getEntity().setGameMode(GameMode.SPECTATOR);
			event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20000*20, 0, false, false));
			event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20000*20, 6, false, false));
			if(plugin.players.size()==1) {
				plugin.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + plugin.players.get(0).getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has won DeathSwap!");
				plugin.endTicker();
				for(Player p : plugin.getServer().getOnlinePlayers()) { p.setGameMode(GameMode.SURVIVAL); }
				plugin.players.clear();
			}
		}
	}
}
