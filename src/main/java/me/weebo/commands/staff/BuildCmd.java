package me.weebo.commands.staff;

import me.weebo.PotionSG;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.weebo.managers.Managers;
import me.weebo.players.Profile;
import me.weebo.utilities.C;
import me.weebo.utilities.PlayerUtils;

public class BuildCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] arg) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.hasPermission(PotionSG.PERM_BUILD)) {
				Profile pr = Managers.getProfileManager().getProfile(p.getUniqueId());
				if (pr.isInLobby()) {
					p.setGameMode(pr.isBuild() ? GameMode.SURVIVAL : GameMode.CREATIVE);
					if (!pr.isBuild()) {
						p.getInventory().clear();
						p.updateInventory();
					} else PlayerUtils.lobbyItems(p);
					C.c(p, "&aBuild mode has been " + (pr.isBuild() ? "disabled" : "enabled") + ".");
					pr.setBuild(!pr.isBuild());
				} else C.c(p, "&cYou must be in lobby to toggle build mode.");
			} else C.c(p, "&cYou do not have permissions to toggle build mode.");
		}
		return true;
	}
}
