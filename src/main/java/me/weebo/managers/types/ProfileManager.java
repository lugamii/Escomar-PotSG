package me.weebo.managers.types;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.weebo.players.Profile;
import org.bukkit.entity.Player;

import lombok.Getter;

/*
 * Manages profiles.
 */
public class ProfileManager {
	
	@Getter private List<Profile> profiles;
	
	public ProfileManager() {
		profiles = new ArrayList<>();
	}
	
	public Profile getProfile(UUID id) {
		for (Profile p : profiles) if (p.getId().equals(id)) return p;
		return null;
	}
	
	public void createProfile(Player p) {
		profiles.add(new Profile(p.getUniqueId()));
	}
	
	public void removeProfile(Player p) {
		profiles.remove(getProfile(p.getUniqueId()));
	}
	
}
