package me.weebo.menu;

import java.util.ArrayList;
import java.util.List;

import me.weebo.PotionSG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;

public class Page {
	
	@Getter
	Menu menu;
	
	@Getter @Setter 
	String title;
	
	@Getter
	int rows;
	
	@Getter
	List<Button> buttons;
	
	@Getter @Setter
	Button nextPageButton, previousPageButton;
	
	@Getter @Setter
	Page nextPage, previousPage;
	
	@Getter @Setter
	boolean goToNextPageOnEveryClick;

	@Getter
	Inventory inventory;
	
	public Page(Menu menu, String title, int rows) {
		this.menu = menu;
		this.title = title;
		this.rows = rows*9;
		this.buttons = new ArrayList<Button>();
		this.goToNextPageOnEveryClick = false;
	}
	
	public void addButton(Button button) {
		this.getButtons().add(button);
	}
	
	public void addPreviousPageButton(String name, int slot) {
		final Button button = new Button();
		button.setItem(new ItemStack(Material.ARROW));
		button.setName(name);
		button.setSlot(slot);
		this.addButton(button);
		this.setPreviousPageButton(button);
	}
	
	public void addNextPageButton(String name, int slot) {
		final Button button = new Button();
		button.setItem(new ItemStack(Material.ARROW));
		button.setName(name);
		button.setSlot(slot);
		this.addButton(button);
		this.setNextPageButton(button);
	}
	
	public void build(Player player) {
		inventory = Bukkit.createInventory(null, rows, title);
		for(final Button button: buttons) {
			inventory.setItem(button.getSlot(), button.getItem());
		}
		player.openInventory(inventory);
		PotionSG.getInstance().getPlayerMenu().put(player.getUniqueId(), this);
	}
	
	public Button getButton(int slot) {
		for(Button button : buttons) {
			if(button.getSlot() == slot) {
				return button;
			}
		}
		return null;
	}

}
