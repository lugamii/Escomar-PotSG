package me.weebo.managers.types;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import me.weebo.PotionSG;
import me.weebo.utilities.C;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;

/*
 * Manages ender pearls.
 */
public class PearlManager {
	
	@Getter private Map<String, Long> m;
	
	public PearlManager() {
		m = new HashMap<>();
	}
	
	public void addPearlCooldown(Player p) {
		m.put(p.getName(), System.currentTimeMillis());
		new BukkitRunnable() {
			@Override
			public void run() {
				if (p.isOnline()) removePearlCooldown(p, true);
			}
		}.runTaskLater(PotionSG.getInst(), 16 * 20L);
	}
	
	public void removePearlCooldown(Player p, boolean b) {
		if (isInCooldown(p)) {
			m.remove(p.getName());
			if (b) C.c(p, C.CHAT_SECONDARY + "Your pearl cooldown has expired.");
		}
	}
	
	public boolean isInCooldown(Player p) {
		return m.containsKey(p.getName());
	}
	
	public long getCooldown(Player p) {
		return m.get(p.getName()) + 16000L - System.currentTimeMillis();
	}
	
	public String getCooldownIn1DP(Player p) {
		DecimalFormat value = new DecimalFormat("#.#");
		return value.format(getCooldown(p) / 1000.0);
	}
	
}
