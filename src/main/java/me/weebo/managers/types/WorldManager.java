package me.weebo.managers.types;

import me.weebo.PotionSG;
import me.weebo.utilities.C;
import me.weebo.utilities.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldManager {
	
	private boolean success;
	private String world;
	private Player[] playerInWorldn;
	private Location[] playerInWorldLocn;
	private GameMode[] playerInWorldGMn;
	
	public void saveRebuild(Location loc, boolean save) {
		world = loc.getWorld().getName();
		Bukkit.getScheduler().runTask(PotionSG.getInst(), new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				C.c(Bukkit.getConsoleSender(), ChatColor.GOLD + (save ? "Saving" : "Rebuilding") +  " the world '" + loc.getWorld() + "...");
				Player[] playerInWorld = new Player[Bukkit.getOnlinePlayers().size()];
				Location[] playerInWorldLoc = new Location[Bukkit.getOnlinePlayers().size()];
				GameMode[] playerInWorldLGM = new GameMode[Bukkit.getOnlinePlayers().size()];
				int i = 0;
				if (loc.getWorld().equals(Bukkit.getServer().getWorlds().get(0))) C.c(Bukkit.getConsoleSender(), ChatColor.RED + "Cannot backup the world as it is the default world!");
				else {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv load " + world);
					for (Player pInWorld : Bukkit.getServer().getOnlinePlayers()) {
						if (pInWorld.getWorld().equals(loc.getWorld())) {
							playerInWorld[i] = pInWorld;
							playerInWorldLoc[i] = pInWorld.getLocation();
							playerInWorldLGM[i] = pInWorld.getGameMode();
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + pInWorld.getName() + " e:" + Bukkit.getServer().getWorlds().get(0).getName() + ":" + Bukkit.getServer().getWorlds().get(0).getSpawnLocation().getX() + "," + Bukkit.getServer().getWorlds().get(0).getSpawnLocation().getY() + "," + Bukkit.getServer().getWorlds().get(0).getSpawnLocation().getZ());
						}
						i++;
					}
				}
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv unload " + world);
				if (save) success = WorldUtils.copy(world, world + "_default#backup");
				else success = WorldUtils.copy(world + "_default#backup",  world);
            	playerInWorldLocn = playerInWorldLoc;
            	playerInWorldn = playerInWorld;
            	playerInWorldGMn = playerInWorldLGM;
            	Bukkit.getScheduler().runTask(PotionSG.getInst(), new Runnable() {
            		Player[] playerInWorld = playerInWorldn;
            		Location[] playerInWorldLoc = playerInWorldLocn;
            		GameMode[] playerInWorldGM = playerInWorldGMn;
            		public void run() {
            			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv load " + world);
            			boolean loaded = false;
            			while (!loaded) {
            				loaded = Boolean.valueOf(Bukkit.getServer().getWorlds().contains(Bukkit.getWorld(world)));
            				if (loaded) {
            					playerInWorldLocn = this.playerInWorldLoc;
            					playerInWorldn = this.playerInWorld;
            					playerInWorldGMn = this.playerInWorldGM;
            					Bukkit.getScheduler().runTaskLater(PotionSG.getInst(), new Runnable() {
            						Player[] playerInWorld = playerInWorldn;
            						Location[] playerInWorldLoc = playerInWorldLocn;
            						public void run() {
            							for (int i = 0; i < this.playerInWorld.length; i++) if (this.playerInWorld[i] != null) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + this.playerInWorld[i].getName() + " e:" + this.playerInWorldLoc[i].getWorld().getName() + ":" + this.playerInWorldLoc[i].getX() + "," + this.playerInWorldLoc[i].getY() + "," + this.playerInWorldLoc[i].getZ());
            						}
            					}, 10L);
            					Bukkit.getScheduler().runTaskLater(PotionSG.getInst(), new Runnable() {
            						Player[] playerInWorld = playerInWorldn;
            						GameMode[] playerInWorldGM = playerInWorldGMn;
            						public void run() {
            							for (int i = 0; i < this.playerInWorld.length; i++) if (this.playerInWorld[i] != null) this.playerInWorld[i].setGameMode(this.playerInWorldGM[i]);
            						}
            					}, 20L);
            				}
            			}
            		}
            	});
            	if (success) C.c(Bukkit.getConsoleSender(), "&aSuccessfully " + (save ? "saved" : "rebuilded") + " the world '" + world + "'.");
            	else C.c(Bukkit.getConsoleSender(), "&cFailed to " + (save ? "save" : "rebuild") + " the world '" + world + "'.");
			}
		});
	}
	
}
