package me.weebo.border;

import java.util.List;

import me.weebo.PotionSG;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.weebo.utilities.C;
import me.weebo.utilities.LocationUtils;

public class Border extends BukkitRunnable {
	
	public Border() {
		runTaskTimer(PotionSG.getInst(), 0L, 5L);
	}
	
	@Override
	public void run() {
		if (PotionSG.getInst().isStarted()) {
			List<Player> list = PotionSG.getInst().getPlayersInGame();
			list.addAll(PotionSG.getInst().getSpectators());
			for (Player p : list) {
				if (PotionSG.getInst().getArena() != null) {
					if (p.getWorld().equals(LocationUtils.readGameSpawn().getWorld())) {
						Location loc = p.getLocation().clone();
						if (p.getLocation().getX() < PotionSG.getInst().getBorderLocation1().getX()) loc.setX(PotionSG.getInst().getBorderLocation1().getX() + 2);
						if (p.getLocation().getX() > PotionSG.getInst().getBorderLocation2().getX()) loc.setX(PotionSG.getInst().getBorderLocation2().getX() - 2);
						if (p.getLocation().getZ() < PotionSG.getInst().getBorderLocation1().getZ()) loc.setZ(PotionSG.getInst().getBorderLocation1().getZ() + 2);
						if (p.getLocation().getZ() > PotionSG.getInst().getBorderLocation2().getZ()) loc.setZ(PotionSG.getInst().getBorderLocation2().getZ() - 2);
						if (!p.getLocation().equals(loc)) {
							loc.setY(getHighestBlockYAt(loc));
							p.teleport(loc);
							C.c(p, "&e\u26A0 &cYou have reached the edge of the world.");
						}
					}
				}
			}
		} else cancel();
	}
	
	public int getHighestBlockYAt(Location loc) {
		for (int y = 0; y < 256; y++) {
			Material m = loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getType();
			if (m == Material.AIR || m == Material.LONG_GRASS) return y;
		}
		return 0;
	}
}
