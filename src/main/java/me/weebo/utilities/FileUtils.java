package me.weebo.utilities;

import lombok.Getter;
import me.weebo.PotionSG;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/*
 * Manages YAML files.
 */
public class FileUtils {

	public static final String ARENAS = "arenas.yml";
	public static final String PLAYERDATA = "playerdata.yml";
	@Getter
	private static File arenasFile = new File(PotionSG.getInst().getDataFolder(), ARENAS);
	@Getter
	private static File playerDataFile = new File(PotionSG.getInst().getDataFolder(), PLAYERDATA);
	@Getter
	private static YamlConfiguration arenasYML = YamlConfiguration.loadConfiguration(arenasFile);
	@Getter
	private static YamlConfiguration playerDataYML = YamlConfiguration.loadConfiguration(playerDataFile);

	static {
		if (!getArenasFile().exists())
			try {
				getArenasFile().createNewFile();
			} catch (IOException e) {
				C.c(Bukkit.getConsoleSender(), "&cPotionSG encountered en error when trying to create arenas.yml!");
			}
		if (!getPlayerDataFile().exists())
			try {
				getPlayerDataFile().createNewFile();
			} catch (IOException e) {
				C.c(Bukkit.getConsoleSender(), "&cPotionSG encountered en error when trying to create playerdata.yml!");
			}
	}

	public static void saveArenasFile() {
		try {
			getArenasYML().save(getArenasFile());
		} catch (IOException e) {
			C.c(Bukkit.getConsoleSender(), "&cPotionSG encountered an error when trying to save arenas.yml!");
		}
	}

	public static void saveProfilesFile() {
		try {
			getPlayerDataYML().save(getPlayerDataFile());
		} catch (IOException e) {
			C.c(Bukkit.getConsoleSender(), "&cPotionSG encountered an error when trying to save profiles.yml!");
		}
	}

}
