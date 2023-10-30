package me.weebo.menu;

import me.weebo.PotionSG;
import org.bukkit.entity.Player;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	
	@Getter
	Menu menu;
	
	@Getter
	List<Page> pages;
	
	public Menu() {
		this.pages = new ArrayList<Page>();
		this.menu = this;
	}
	
	public Page getPage(int page) {
		return pages.get(page);
	}
	
	public void addPage(Page page) {
		this.pages.add(page);
	}
	
	public void show(Player player) {
		this.pages.get(0).build(player);
		PotionSG.getInstance().getPlayerMenu().put(player.getUniqueId(), this.pages.get(0));
	}

}