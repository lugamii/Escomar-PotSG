package me.weebo.utilities;

import me.weebo.PotionSG;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/*
 * Look at the code then you know what the mystery "C" is.
 * I love static abuse LoL
 */
public class C {
	//Scoreboard Color
	public static final String TITLE = c(PotionSG.getInst().getConfig().getString("SCOREBOARD.TITLE-COLOR"));
	//Scoreboard secondary
	public static final String SB_SECONDARY = c(PotionSG.getInst().getConfig().getString("SCOREBOARD.SECONDARY"));;
	//Scoreboard separator
	public static final String SB_SEPARATOR = c(PotionSG.getInst().getConfig().getString("SCOREBOARD.SEPARATOR"));
	//Scoreboard IP Color
	public static final String SB_IP_COLOR = c(PotionSG.getInst().getConfig().getString("SCOREBOARD.IP-COLOR"));


	//Main Color
	public static final String MAIN = c(PotionSG.getInst().getConfig().getString("COLOR.MAIN"));
	//CHAT-SECONDARY
	public static final String CHAT_SECONDARY = c(PotionSG.getInst().getConfig().getString("COLOR.SECONDARY"));


	/*//Join Current Game
	public static final String JOIN_GAME = c(PotionSG.getInst().getConfig().getString("ITEMS.IN-LOBBY.JOIN-GAME"));
	//Spectate Current Game
	public static final String SPECTATE_GAME = c(PotionSG.getInst().getConfig().getString("ITEMS.IN-LOBBY.SPECTATE-GAME"));
	//host
	public static final String HOST_GAME = c(PotionSG.getInst().getConfig().getString("ITEMS.IN-LOBBY.HOST-GAME"));
	//Leaderboard
	public static final String LEADERBOARDS = c(PotionSG.getInst().getConfig().getString("ITEMS.IN-LOBBY.LEADERBOARDS"));
	//Preferences
	public static final String PREFERENCES = c(PotionSG.getInst().getConfig().getString("ITEMS.IN-LOBBY.PREFERENCES"));


	//Leave Game
	public static final String LEAVE_GAME = c(PotionSG.getInst().getConfig().getString("ITEMS.IN-GAME.LEAVE-GAME"));
	//Stop SPEC
	public static final String STOP_SPECTATING = c(PotionSG.getInst().getConfig().getString("ITEMS.IN-GAME.STOP_SPECTATING"));
	//INSPECT
	public static final String INSPECT_ITEM = c(PotionSG.getInst().getConfig().getString("ITEMS.IN-GAME.INSPECT"));
	//TELEPORT
	//public static final String TELEPORT_ITEM = c(PotionSG.getInst().getConfig().getString("ITEMS.IN-GAME.TELEPORT"));*/


	//GUI Third Color
	public static final String GUI_TITLE_COLOR = c(PotionSG.getInst().getConfig().getString("GUI.TITLE-COLOR"));
	//GUI Main Color
	public static final String GUI_MAIN_COLOR = c(PotionSG.getInst().getConfig().getString("GUI.MAIN-COLOR"));
	//GUI First Color
	public static final String GUI_FIRST_COLOR = c(PotionSG.getInst().getConfig().getString("GUI.FIRST-COLOR"));
	//GUI Secondary
	public static final String GUI_SECONDARY_COLOR = c(PotionSG.getInst().getConfig().getString("GUI.SECONDARY-COLOR"));
	//GUI Third Color
	public static final String GUI_THIRD_COLOR = c(PotionSG.getInst().getConfig().getString("GUI.THIRD-COLOR"));


	//Orbit
	public static final String SERVER_NAME = c(PotionSG.getInst().getConfig().getString("SERVER-NAME"));
	//SEASON
	public static final String SEASON = c(PotionSG.getInst().getConfig().getString("SEASON"));
	//IP
	public static final String SERVER_IP = c(PotionSG.getInst().getConfig().getString("SERVER-IP"));
	
	public static final String PLAYERS = ChatColor.AQUA.toString();
	
	public static String c(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static void c(Player player, String msg) {
		player.sendMessage(c(msg));
	}
	
	public static void c(ConsoleCommandSender console, String msg) {
		console.sendMessage(c(msg));
	}
	
	public static void c(CommandSender sender, String msg) {
		sender.sendMessage(c(msg));
	}
	
	public static void c(Player player, String... msg) {
		for (String s : msg) player.sendMessage(c(s));
	}
	
	public static void c(CommandSender sender, String... msg) {
		for (String s : msg) sender.sendMessage(c(s));
	}
	
}
