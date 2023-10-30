package me.weebo.managers.scenarios;

import org.bukkit.Material;

public interface Scenario {
	
	default String getName() {
		return null;
	}
	
	default Material getItem() {
		return null;
	}
	
	default String[] getDescription() {
		return null;
	}
	
	default void setActive(boolean active) {
		return;
	}
	
	default boolean isActive() {
		return false;
	}

}
