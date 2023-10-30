package me.weebo.commands.staff;

import me.weebo.PotionSG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.weebo.utilities.LocationUtils;

public class SetLobbySpawnCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] arg) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.hasPermission(PotionSG.PERM_ACCESS)) LocationUtils.saveLobbySpawn(p);
		}
		return true;
	}

}
