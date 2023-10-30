package me.weebo.utilities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * A class for easier item creation.
 */
public class EasyItem {
	
	private ItemStack item;
	private String name;
	private List<String> lore;
	private boolean unbreakable;
	private short id;
	private int amount;
	private Map<Enchantment, Integer> enchants;
	
	public EasyItem(Material material) {
		this(new ItemStack(material));
	}
	
	public EasyItem(ItemStack item) {
		if (item.getType() != Material.AIR) {
			this.name = item.getItemMeta().getDisplayName();
			this.item = item;
			this.amount = item.getAmount();
			this.id = item.getDurability();
			this.lore = (item.getItemMeta().getLore() != null ? item.getItemMeta().getLore() : new ArrayList<>());
			this.enchants = new HashMap<>();
		}
	}
	
	public EasyItem setName(String name) {
		this.name = name;
		return this;
	}

	public EasyItem addLore(String lore) {
		this.lore.add(C.c(lore));
		return this;
	}

	public EasyItem setLore(String... lore) {
		List<String> list = new ArrayList<>();
		for (String str : lore) list.add(C.c(str));
		this.lore = list;
		return this;
	}
	
	public EasyItem setUnbreakable(boolean unbreakable) {
		this.unbreakable = unbreakable;
		return this;
	}
	
	public EasyItem setId(short id) {
		this.id = id;
		return this;
	}
	
	public EasyItem setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public EasyItem addEnchant(Enchantment enchant, int level) {
		this.enchants.put(enchant, level);
		return this;
	}
	
	public ItemStack build() {
		item.setAmount(amount);
		item.setDurability(id);
		ItemMeta im = item.getItemMeta();
		if (name != null) im.setDisplayName(C.c(name));
		if (lore != null) im.setLore(lore);
		if (unbreakable) im.spigot().setUnbreakable(unbreakable); //have to check if unbreakable is true to prevent items that does not support unbreakable
		if (!enchants.isEmpty()) enchants.forEach((e, i) -> im.addEnchant(e, i, true));
		item.setItemMeta(im);
		return item;
	}

}
