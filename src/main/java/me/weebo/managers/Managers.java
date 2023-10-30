package me.weebo.managers;

import lombok.Getter;
import me.weebo.managers.types.*;

/*
 * Handles all "manager" classes.
 */
public class Managers {
	
	@Getter private static ProfileManager profileManager = new ProfileManager();
	@Getter private static PearlManager pearlManager = new PearlManager();
	@Getter private static MapManager mapManager = new MapManager();
	@Getter private static WorldManager worldManager = new WorldManager();
	@Getter private static UpdatedInventoryManager updatedInventoryManager = new UpdatedInventoryManager();
	@Getter private static LeaderboardManager leaderboardManager = new LeaderboardManager();
	@Getter private static HostManager hostManager = new HostManager();
	@Getter private static TasksManager tasksManager = new TasksManager();

}
