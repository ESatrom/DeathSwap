package me.Mindarius.DeathSwap;

import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class ListenerElimination implements Listener {
	Server server = Main.server();
	
	@EventHandler public void onDeath(PlayerDeathEvent event) {
		if(eliminate(event.getEntity())) {
			event.setDeathMessage(null); //There will be a custom death message since they were in game.
		}
	}
	@EventHandler public void onDC(PlayerQuitEvent event) { eliminate(event.getPlayer()); }
	
	@EventHandler public void onJoin(PlayerJoinEvent event) { spectate(event.getPlayer()); }
	@EventHandler public void onRespawn(PlayerRespawnEvent event) { spectate(event.getPlayer()); }
	
	/**
	 * @param p - Player to attempt to eliminate
	 * @return whether the player was in game
	 */
	private boolean eliminate(Player p) {
		if(Main.getPlayers().remove(p)) { //Checks that the player was in-game
			Player ender = Main.getSwapList().get(p); //Find who may have killed them
			if(ender==null||!Main.lastSwap()) { //If ender is null, they weren't killed by a swap, lastSwap() checks timestamp of last swap to see if it's close enough.
				server.broadcastMessage("§c§l" + Main.getPlayers().get(0).getDisplayName() + "§c§l has been eliminated!");
			} else {
				server.broadcastMessage("§c§l" + Main.getPlayers().get(0).getDisplayName() + "§c§l has been eliminated by " + ender.getDisplayName() + "§c§l!");
			}
			if(Main.getPlayers().size()==1) { //One player left at this point indicates a victor
				server.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + Main.getPlayers().get(0).getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has won DeathSwap!");
				Main.endTicker();
				for(Player p2 : server.getOnlinePlayers()) { p2.setGameMode(GameMode.SURVIVAL); }
				Main.getPlayers().clear();
				Main.getSwapList().clear();
			} else if(Main.getPlayers().size()==0) { //No players left indicates I messed up
				server.broadcastMessage("§cDeathSwap has ended without a victor.");
				Main.endTicker();
				for(Player p2 : server.getOnlinePlayers()) { p2.setGameMode(GameMode.SURVIVAL); }
				Main.getPlayers().clear();
				Main.getSwapList().clear();
			}
			return true;
		}
		return false; //For some reason they died whilst not in game
	}
	
	/**
	 * @param p - Player to attempt putting in spectator
	 */
	private void spectate(Player p) {
		if(Main.gameOn()) {
			p.setGameMode(GameMode.SPECTATOR);
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20000*20, 0, false, false)); //Lets them see better
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20000*20, 6, false, false)); //Gotta go fast
		} else {
			p.setGameMode(GameMode.SURVIVAL);
		}
	}
}
