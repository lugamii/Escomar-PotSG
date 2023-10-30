package me.weebo.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.Getter;
import lombok.Setter;

public class Button {
	
	@Getter 
	Button button;
	
	@Getter @Setter 
	ItemStack item;
	
	@Getter 
	String name;
	
	@Getter
	List<String> lore;
	
	@Getter @Setter 
	int slot;
	
	@Getter @Setter
	Page nextPage;
	
	public Button(ItemStack item, String name, List<String> lore, int slot, String command) {
		this.item = item;
		this.name = name;
		this.lore = lore;
		this.slot = slot;
		this.button = this;
	}
	
	public Button() {
		this.item = new ItemStack(Material.AIR);
		this.name = "None";
		this.lore = new ArrayList<String>();
		this.slot = 0;
		this.button = this;
	}
	
	public void setName(String name) {
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}
	
	public void setLore(List<String> lore) {
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public void addLore(String lore) {
		final ItemMeta meta = item.getItemMeta();
		this.lore.add(lore);
		meta.setLore(this.lore);
		item.setItemMeta(meta);
	}
}