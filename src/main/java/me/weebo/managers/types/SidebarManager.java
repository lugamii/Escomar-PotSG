package me.weebo.managers.types;

import me.missionary.board.provider.BoardProvider;
import me.weebo.PotionSG;
import me.weebo.managers.Managers;
import me.weebo.utilities.C;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.weebo.managers.scenarios.Scenario;
import me.weebo.managers.scenarios.ScenarioManager;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * Manages sidebar (scoreboard).
 */
public class SidebarManager implements BoardProvider {

    @Override
    public String getTitle(Player player) {
        return C.TITLE + C.SERVER_NAME + C.SB_SEPARATOR + C.SEASON;
    }

	@Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();
        lines.add("&7&m--------------------");
        if (Managers.getProfileManager().getProfile(player.getUniqueId()).isInLobby()) {
	        lines.add(C.MAIN + "Online: " + C.SB_SECONDARY + Bukkit.getOnlinePlayers().size());
	        if (PotionSG.getInst().isInCooldown()) lines.add(C.MAIN + "Next game in " + C.SB_SECONDARY + formatTime(PotionSG.getInst().getCooldownTimer() + 1));
	        if (Managers.getHostManager().getAdditionalTime() != 0) lines.add(C.MAIN + "Game starts in " + C.SB_SECONDARY + formatTime(Managers.getHostManager().getAdditionalTime()));
	        if (Managers.getProfileManager().getProfile(player.getUniqueId()).isBuild()) {
	        	lines.add("");
	        	lines.add("&e&lIN BUILD MODE");
	        }
			if (PotionSG.getInst().isWaiting() || PotionSG.getInst().isStarted()) {
				lines.add("&7&m--------------------");
				lines.add(C.TITLE + "&lGame " + (PotionSG.getInst().isWaiting() ? "&7(Waiting)" : "&7(Started)"));
				lines.add(C.MAIN + " Host: " + C.SB_SECONDARY + PotionSG.getInst().getHost());
				lines.add(C.MAIN + (PotionSG.getInst().isWaiting() ? " Players: " + C.SB_SECONDARY + PotionSG.getInst().getPlayersWaiting().size(): " Remaining: " + C.SB_SECONDARY + PotionSG.getInst().getPlayersInGame().size()) );
			}
        } else if (Managers.getProfileManager().getProfile(player.getUniqueId()).isWaiting()) {
			lines.add(C.MAIN + "Online: " + C.SB_SECONDARY + Bukkit.getOnlinePlayers().size());
			if (PotionSG.getInst().isInCooldown()) lines.add(C.MAIN + "Next game in " + C.SB_SECONDARY + formatTime(PotionSG.getInst().getCooldownTimer() + 1));
			if (Managers.getHostManager().getAdditionalTime() != 0) lines.add(C.MAIN + "Game starts in " + C.SB_SECONDARY + formatTime(Managers.getHostManager().getAdditionalTime()));
			if (Managers.getProfileManager().getProfile(player.getUniqueId()).isBuild()) {
				lines.add("");
				lines.add("&e&lIN BUILD MODE");
			}
			if (PotionSG.getInst().isWaiting() || PotionSG.getInst().isStarted()) {
				lines.add("&7&m--------------------");
				lines.add("&fGame " + C.SB_SECONDARY + "PotSG-1" + " &7(Waiting)");
				lines.add(C.MAIN + " Host: " + C.SB_SECONDARY + PotionSG.getInst().getHost());
				lines.add(C.MAIN + " Players: " + C.SB_SECONDARY + PotionSG.getInst().getPlayersWaiting().size());
				lines.add(C.MAIN + " Team Size: " + C.SB_SECONDARY + "FFA");
				ScenarioManager smanager = PotionSG.getInstance().getScenarioManager();
					if (smanager.getActiveScenarios().size() > 0) {
						lines.add("");
						lines.add(C.MAIN + "Scenarios: ");
						for (Scenario param: smanager.getActiveScenarios()) {
							lines.add(ChatColor.GRAY + "- " + ChatColor.DARK_AQUA + param.getName());
						}
				}
			}
        } else if (Managers.getProfileManager().getProfile(player.getUniqueId()).isInGame() || Managers.getProfileManager().getProfile(player.getUniqueId()).isSpectating()) {
        	if (PotionSG.getInst().getStartTimer() > 0) {
        		lines.add(C.MAIN + "Game starts in " + C.SB_SECONDARY + ((int) PotionSG.getInst().getStartTimer() + 1) + C.MAIN + "...");
        	} else {
        		lines.add(C.MAIN + "Game Time: " + C.SB_SECONDARY + formatTime(PotionSG.getInst().getGameTimer() + 1));
        		lines.add(C.MAIN + "Players Alive: " + C.SB_SECONDARY + PotionSG.getInst().getPlayersInGame().size());
        		lines.add(C.MAIN + "Spectators: " + C.SB_SECONDARY + PotionSG.getInst().getSpectators().size());
	        	//lines.add(C.MAIN + "Kills: " + C.SB_SECONDARY + "0");
	        	
	        	if (PotionSG.getInst().getPvpTimer() > -1) {
	        		lines.add("");
	        		lines.add(C.MAIN + "PvP Enables in " + C.SB_SECONDARY + formatTime(PotionSG.getInst().getPvpTimer() + 1));
	        	}
	        	else if (PotionSG.getInst().getFeastTimer() > -1) {
	        		lines.add("");
	        		lines.add(C.MAIN + "Feast: " + C.SB_SECONDARY + formatTime(PotionSG.getInst().getFeastTimer() + 1));
	        	}
	        	else if (PotionSG.getInst().getBorderTimer() > -1) {
	        		lines.add("");
	        		lines.add(C.MAIN + "Border: " + C.SB_SECONDARY + PotionSG.getInst().getRad() + (PotionSG.getInst().getRad() > PotionSG.getInst().getTargetRad() ? " (" + PotionSG.getInst().getBorderTimer() + ")" : ""));
	        	}
        	}
        }
        lines.add("");
        lines.add(C.SB_IP_COLOR + C.SERVER_IP);
        lines.add("&7&m--------------------");
		return lines;
    }
    
    @SuppressWarnings("restriction")
	private String getMemory() {
    	Runtime r = Runtime.getRuntime();
    	DecimalFormat df = new DecimalFormat("#.#");
    	String used = df.format((r.totalMemory() - r.freeMemory()) / 1073741824D);
    	String total = df.format(((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() / 1073741824D);
    	return used + "/" + total + " GB";
    }
    
    private String formatTime(int time) {
		final int min = time / 60;
	    final int sec = time % 60;

	    final String strMin = zero(min);
	    final String strSec = zero(sec);
	    
	    return String.format("%s:%s",strMin,strSec);
    }
    
    private String zero(int number) {
	    return (number >=10)? Integer.toString(number):String.format("0%s",Integer.toString(number));
	}
    
}
