package me.weebo.managers.types;

import lombok.Getter;
import me.weebo.managers.Managers;
import me.weebo.scenarios.Soup;
import me.weebo.utilities.C;
import me.weebo.utilities.EasyItem;
import me.weebo.utilities.FileUtils;
import me.weebo.utilities.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages maps.
 */
public class MapManager {

	@Getter
	private List<String> arenas;
	@Getter
	private List<Chest> chests;
	Soup soup = null;
	private ItemStack[] items, feastItems, soupItems, feastSoupItems;

	public MapManager() {
		arenas = new ArrayList<>();
		chests = new ArrayList<>();
		soupItems = new ItemStack[]{
				new EasyItem(Material.ARROW).setAmount(16).build(),
				new EasyItem(Material.BOW).build(),
				new EasyItem(Material.COOKED_BEEF).setAmount(8).build(),
				new EasyItem(Material.DIAMOND).setAmount(5).build(),
				new EasyItem(Material.DIAMOND_SWORD).build(),
				createEnchantedBook(Enchantment.DAMAGE_ALL, 1),
				createEnchantedBook(Enchantment.ARROW_DAMAGE, 1),
				createEnchantedBook(Enchantment.PROTECTION_ENVIRONMENTAL, 1),
				new EasyItem(Material.ENDER_PEARL).setAmount(3).build(),
				new EasyItem(Material.EXP_BOTTLE).setAmount(10).build(),
				new EasyItem(Material.FLINT_AND_STEEL).build(),
				new EasyItem(Material.GLOWSTONE_DUST).setAmount(5).build(),
				new EasyItem(Material.SULPHUR).setAmount(5).build(),
				new EasyItem(Material.GOLDEN_APPLE).setAmount(2).build(),
				new EasyItem(Material.GOLDEN_CARROT).setAmount(8).build(),
//				new EasyItem(Material.IRON_INGOT).setAmount(10).build(),
				new EasyItem(Material.MUSHROOM_SOUP).setAmount(5).build(),
				new EasyItem(Material.MUSHROOM_SOUP).setAmount(5).build(),
				new EasyItem(Material.MUSHROOM_SOUP).setAmount(5).build(),
				new EasyItem(Material.MUSHROOM_SOUP).build(),
				new EasyItem(Material.WEB).setAmount(3).build()};
		items = new ItemStack[]{
				new EasyItem(Material.ARROW).setAmount(16).build(),
				new EasyItem(Material.BOW).build(),
				new EasyItem(Material.COOKED_BEEF).setAmount(8).build(),
				new EasyItem(Material.DIAMOND).setAmount(5).build(),
				new EasyItem(Material.DIAMOND_SWORD).build(),
				createEnchantedBook(Enchantment.DAMAGE_ALL, 1),
				createEnchantedBook(Enchantment.ARROW_DAMAGE, 1),
				createEnchantedBook(Enchantment.PROTECTION_ENVIRONMENTAL, 1),
				new EasyItem(Material.ENDER_PEARL).setAmount(3).build(),
				new EasyItem(Material.EXP_BOTTLE).setAmount(10).build(),
				new EasyItem(Material.FLINT_AND_STEEL).build(),
				new EasyItem(Material.GLOWSTONE_DUST).setAmount(5).build(),
				new EasyItem(Material.SULPHUR).setAmount(5).build(),
				new EasyItem(Material.GOLDEN_APPLE).setAmount(2).build(),
				new EasyItem(Material.GOLDEN_CARROT).setAmount(8).build(),
//				new EasyItem(Material.IRON_INGOT).setAmount(10).build(),
				new EasyItem(Material.POTION).setId((short) 16421).setAmount(5).build(),
				new EasyItem(Material.POTION).setId((short) 16453).setAmount(5).build(),
				new EasyItem(Material.POTION).setId((short) 8229).setAmount(5).build(),
				new EasyItem(Material.POTION).setId((short) 8194).build(),
				new EasyItem(Material.WEB).setAmount(3).build()};
		feastSoupItems = new ItemStack[]{
				new EasyItem(Material.ARROW).setAmount(32).build(),
				new EasyItem(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).build(),
				new EasyItem(Material.DIAMOND).setAmount(6).build(),
				new EasyItem(Material.DIAMOND_SWORD).addEnchant(Enchantment.FIRE_ASPECT, 1).build(),
				new EasyItem(Material.ENDER_PEARL).setAmount(5).build(),
				new EasyItem(Material.EXP_BOTTLE).setAmount(10).build(),
				new EasyItem(Material.FLINT_AND_STEEL).build(),
				new EasyItem(Material.GOLDEN_APPLE).setAmount(3).build(),
				new EasyItem(Material.MUSHROOM_SOUP).setId((short) 16421).setAmount(5).build(),
				new EasyItem(Material.MUSHROOM_SOUP).setId((short) 16426).build(),
				new EasyItem(Material.MUSHROOM_SOUP).setId((short) 16388).build(),
				new EasyItem(Material.POTION).setId((short) 8226).build(),
				new EasyItem(Material.POTION).setId((short) 8259).build(),
				new EasyItem(Material.TNT).setAmount(3).build(),
				new EasyItem(Material.WEB).setAmount(5).build()};
		feastItems = new ItemStack[]{
				new EasyItem(Material.ARROW).setAmount(32).build(),
				new EasyItem(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).build(),
				new EasyItem(Material.DIAMOND).setAmount(6).build(),
				new EasyItem(Material.DIAMOND_SWORD).addEnchant(Enchantment.FIRE_ASPECT, 1).build(),
				new EasyItem(Material.ENDER_PEARL).setAmount(5).build(),
				new EasyItem(Material.EXP_BOTTLE).setAmount(10).build(),
				new EasyItem(Material.FLINT_AND_STEEL).build(),
				new EasyItem(Material.GOLDEN_APPLE).setAmount(3).build(),
				new EasyItem(Material.POTION).setId((short) 16421).setAmount(5).build(),
				new EasyItem(Material.POTION).setId((short) 16426).build(),
				new EasyItem(Material.POTION).setId((short) 16388).build(),
				new EasyItem(Material.POTION).setId((short) 8226).build(),
				new EasyItem(Material.POTION).setId((short) 8259).build(),
				new EasyItem(Material.TNT).setAmount(3).build(),
				new EasyItem(Material.WEB).setAmount(5).build()};
	}

	private ItemStack createEnchantedBook(Enchantment ench, int level) {
		ItemStack i = new ItemStack(Material.ENCHANTED_BOOK);
		EnchantmentStorageMeta e = (EnchantmentStorageMeta) i.getItemMeta();
		e.addStoredEnchant(ench, level, false);
		i.setItemMeta(e);
		return i;
	}

	public boolean chestIsOpened(Chest chest) {
		return chests.contains(chest);
	}

	public void addArena(String name, Player p) {
		if (!arenas.contains(name)) {
			arenas.add(name);
			LocationUtils.saveGameSpawn(p.getLocation(), name);
			C.c(p, "&aYou have successfully created an arena named \"" + name + "\" and your current location will be the spawn of it.");
			C.c(p, "&eWe have to make a backup of this world, expect lag!");
			Managers.getWorldManager().saveRebuild(p.getLocation(), true);
		} else C.c(p, "&e\u26A0 &cAn arena with that name already exists!");
	}

	public void removeArena(String name, Player p) {
		if (arenas.contains(name)) {
			arenas.remove(name);
			LocationUtils.saveGameSpawn(null, name);
			C.c(p, "&aYou have successfully removed that arena.");
		} else C.c(p, "&e\u26A0 &cThat arena does not exist!");
	}

	public void loadArenas() {
		YamlConfiguration config = FileUtils.getArenasYML();
		if (config.getConfigurationSection("arenas") != null) {
			for (String name : config.getConfigurationSection("arenas").getKeys(false)) arenas.add(name);
		}
	}

	public void dropFeastItems(Location loc) {
		Random rand = new Random();
		for (int i = 0; i < new Random().nextInt(2) + 6; i++) {
			ItemStack item = feastItems[rand.nextInt(feastItems.length)].clone();
			item.setAmount(rand.nextInt(item.getAmount()) + 1);
			loc.getWorld().dropItemNaturally(loc, item);
		}
	}

	public void fillChest(Chest chest) {
		Random rand = new Random();
		chest.getInventory().clear();
		for (int i = 0; i < rand.nextInt(2) + 5; i++) {
			ItemStack item = items[rand.nextInt(items.length)].clone();
			item.setAmount(rand.nextInt(item.getAmount()) + 1);
			if ((item.getType() == Material.BOW || item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.ENCHANTED_BOOK) &&
					(chest.getInventory().contains(Material.BOW) || chest.getInventory().contains(Material.FLINT_AND_STEEL) || chest.getInventory().contains(Material.DIAMOND_SWORD) || chest.getInventory().contains(Material.ENCHANTED_BOOK))) {
				i++;
				continue;
			}
			chest.getInventory().addItem(item);
		}
		if (!chest.getInventory().contains(items[3].getType()) && rand.nextInt(10) < 8) {
			ItemStack it = items[3].clone();
			it.setAmount(rand.nextInt(it.getAmount()) + 1);
			chest.getInventory().addItem(it);
		}
		if (!chest.getInventory().contains(items[15].getType())) {
			ItemStack it = items[15].clone();
			it.setAmount(rand.nextInt(it.getAmount()) + 1);
			chest.getInventory().addItem(it);
			if (rand.nextInt(10) < 3) {
				ItemStack i = items[16].clone();
				i.setAmount(rand.nextInt(i.getAmount()) + 1);
				chest.getInventory().addItem(i);
			}
		}
		chests.add(chest);
	}

	public void resetMap(Location loc) {
		chests.clear();
		Managers.getWorldManager().saveRebuild(loc, false);
	}

	public void replaceItems(Chest chest, Material m, int amount) {
		if (soup.isActive()) {
			if (Material.POTION.getData().equals(PotionType.INSTANT_HEAL)) {

			}

		}
	}
}


