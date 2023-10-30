package me.weebo.commands.staff;

import me.weebo.PotionSG;
import me.weebo.managers.Managers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.weebo.utilities.C;

public class FreezeCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] arg) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (p.hasPermission(PotionSG.PERM_MOD) || p.hasPermission(PotionSG.PERM_ACCESS)) {
				if (arg.length == 0 || arg.length > 1) C.c(p, "&e\u26A0 &cUsage: /freeze <player>");
				else {
					if (Bukkit.getPlayer(arg[0]) != null) {
						Player t = Bukkit.getPlayer(arg[0]);
						if (Managers.getProfileManager().getProfile(t.getUniqueId()).isFrozen()) {
							allowMovement(t);
							C.c(p, "&aYou unfroze " + t.getName() + ".");
							C.c(t, "&aYou have been unfrozen.");
						} else {
							denyMovement(t);
							C.c(p, "&aYou froze " + t.getName() + ".");
							C.c(t,  "",
									"&f\u2588\u2588\u2588\u2588&c\u2588&f\u2588\u2588\u2588\u2588",
									"&f\u2588\u2588\u2588&c\u2588&6\u2588&c\u2588&f\u2588\u2588\u2588",
									"&f\u2588\u2588&c\u2588&6\u2588&0\u2588&6\u2588&c\u2588&f\u2588\u2588  &c&lYou have been frozen!",
									"&f\u2588\u2588&c\u2588&6\u2588&0\u2588&6\u2588&c\u2588&f\u2588\u2588",
									"&f\u2588&c\u2588&6\u2588\u2588&0\u2588&6\u2588\u2588&c\u2588&f\u2588  &7Please join " + C.MAIN + "discord.nekros.club",
									"&f\u2588&c\u2588&6\u2588\u2588\u2588\u2588\u2588&c\u2588&f\u2588  &7in 3 minutes or you will be banned",
									"&c\u2588&6\u2588\u2588\u2588&0\u2588&6\u2588\u2588\u2588&c\u2588",
									"&c\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588",
									"");
						}
						Managers.getProfileManager().getProfile(t.getUniqueId()).toggleFreeze();
					}
					
				}
			}
		}
		return true;
	}
	
	private void denyMovement(Player player) {
        player.setWalkSpeed(0.0F);
        player.setFlySpeed(0.0F);
        player.setFoodLevel(0);
        player.setSprinting(false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200));
    }

    private void allowMovement(Player player) {
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.0001F);
        player.setFoodLevel(20);
        player.setSprinting(true);
        player.removePotionEffect(PotionEffectType.JUMP);
    }
}
