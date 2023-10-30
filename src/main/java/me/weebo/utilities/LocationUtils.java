package me.weebo.utilities;

import me.weebo.PotionSG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/*
 * A class for easier communications between YML files and location objects.
 */
public class LocationUtils {
	
	public static void saveLobbySpawn(Player player) {
		FileConfiguration config = PotionSG.getInst().getConfig();
		config.set("lobby-spawn", translate(player.getLocation()));
		C.c(player, "&aYou have set the lobby spawn point for PotionSG.");
		PotionSG.getInst().saveConfig();
	}
	
	public static Location readLobbySpawn() {
		FileConfiguration config = PotionSG.getInst().getConfig();
		if (config.getString("lobby-spawn") != null) return translate(config.getString("lobby-spawn"));
		C.c(Bukkit.getConsoleSender(), "&cPotionSG cannot read the lobby spawn point from config.yml, please set it up with \"/setlobbyspawn\".");
		return null;
	}
	
	public static void saveGameSpawn(Location loc, String arena) {
		FileConfiguration config = FileUtils.getArenasYML();
		config.set("arenas." + arena, translate(loc));
		FileUtils.saveArenasFile();
	}
	
	public static Location readGameSpawn() {
		FileConfiguration config = FileUtils.getArenasYML();
		if (!PotionSG.getInst().getArena().equals("")) return translate(config.getString("arenas." + PotionSG.getInst().getArena()));
		return null;
	}
	
	public static Location translate(String input) {
		String[] str = input.split("\\^");
		return new Location(Bukkit.getWorld(str[0]), 
				Double.parseDouble(str[1]), 
				Double.parseDouble(str[2]), 
				Double.parseDouble(str[3]), 
				Float.parseFloat(str[4]), 
				Float.parseFloat(str[5]));
	}
	
	public static String translate(Location loc) {
		if (loc != null) return loc.getWorld().getName() + "^" + loc.getX() + "^" + loc.getY() + "^" + loc.getZ() + "^" + loc.getYaw() + "^" + loc.getPitch();
		return null;
	}

}
