package me.Mindarius.DeathSwap.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Mindarius.DeathSwap.Main;
import me.Mindarius.DeathSwap.completers.SpecCompleter;

public class CommandSpec extends DSCommand {
	public CommandSpec() { super("Spec", new SpecCompleter()); }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) { return false; } //No console
		Player p = (Player) sender;
		if(p.getGameMode()!=GameMode.SPECTATOR) { return false; } //No non-specs
		if(args.length!=1) { return false; } //Need to specify player
		Player targ = Main.server().getPlayer(args[0]);
		if(targ==null||!targ.isOnline()) { return false; } //Needs to be a valid online player
		p.teleport(targ.getLocation());
		p.sendMessage("Punch a player to passively follow them (including camera). Shift to exit passive follow");
		return true;
	}
	
}
