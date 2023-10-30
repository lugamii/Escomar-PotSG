package me.weebo.commands.staff;

import me.weebo.PotionSG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.weebo.managers.Managers;
import me.weebo.utilities.C;

public class ArenaCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] arg) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.hasPermission(PotionSG.PERM_ACCESS)) {
				if (arg.length == 1) {
					if (arg[0].equalsIgnoreCase("list")) list(p);
					else help(p);
				} else if (arg.length == 2) {
					if (arg[0].equalsIgnoreCase("create")) Managers.getMapManager().addArena(arg[1], p);
					else if (arg[0].equalsIgnoreCase("delete")) Managers.getMapManager().removeArena(arg[1], p);
					else help(p);
				} else help(p);
			} else C.c(p, "&cYou do not have permissions to manage arenas.");
		}
		return true;
	}
	
	private void list(Player p) {
		C.c(p, "&7&m--------------------------------------------------", C.MAIN + "&lArenas:");
		for (String a : Managers.getMapManager().getArenas()) C.c(p, C.CHAT_SECONDARY + "* " + C.MAIN + a);
		C.c(p, "&7&m--------------------------------------------------");
	}
	
	private void help(Player p) {
		C.c(p, 
				"&7&m--------------------------------------------------", 
				C.MAIN + "&lSub-commands of \"" + C.CHAT_SECONDARY + "/arenas" + C.MAIN + "&l\":",
				C.MAIN + "/arena create <name> &7- Create an arena",
				C.MAIN + "/arena delete <name> &7- Delete an arena",
				C.MAIN + "/arena list &7- List all arenas",
				"&7&m--------------------------------------------------");
	}
}
