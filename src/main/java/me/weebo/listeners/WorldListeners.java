package me.weebo.listeners;

import me.weebo.PotionSG;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.weather.WeatherChangeEvent;

/*
 * Listen to events related to worlds.
 */
public class WorldListeners implements Listener {

	@EventHandler
	public void spawn(CreatureSpawnEvent e) {
		if (PotionSG.getInst().isStarted() && e.getEntityType() == EntityType.SILVERFISH && e.getSpawnReason() == SpawnReason.SILVERFISH_BLOCK) return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void weather(WeatherChangeEvent e) {
		e.setCancelled(e.toWeatherState());
	}
	
	@SuppressWarnings("deprecation")
	public static void LeavesDecayEvent(org.bukkit.event.block.LeavesDecayEvent e) {
		e.getBlock().setData((byte) (e.getBlock().getData() % 4));
		e.setCancelled(true);
	}

}
