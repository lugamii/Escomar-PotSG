package me.weebo.players;

import lombok.Getter;
import lombok.Setter;
import me.weebo.PotionSG;

import java.util.UUID;

/*
 * Wrapper class for storing information of players.
 */
public class Profile {
	
	@Getter private UUID id;
	@Getter private PlayerState state;
	@Getter @Setter private boolean build;
	@Getter private long pearl;
	@Getter private boolean frozen;
	
	public Profile(UUID id) {
		this.id = id;
		this.state = PlayerState.IN_LOBBY;
	}

	public boolean isInLobby() {
		return this.state == PlayerState.IN_LOBBY;
	}
	
	public boolean isWaiting() {
		return this.state == PlayerState.IN_GAME && PotionSG.getInst().isWaiting();
	}
	
	public boolean isInGame() {
		return this.state == PlayerState.IN_GAME && PotionSG.getInst().isStarted();
	}

	public boolean isSpectating() {
		return this.state == PlayerState.SPECTATING;
	}
	
	public void setInLobby() {
		this.state = PlayerState.IN_LOBBY;
	}
	
	public void setInGame() {
		this.state = PlayerState.IN_GAME;
	}
	
	public void setSpectating() {
		this.state = PlayerState.SPECTATING;
	}
	
	public void toggleFreeze() {
		frozen = !frozen;
	}

}
