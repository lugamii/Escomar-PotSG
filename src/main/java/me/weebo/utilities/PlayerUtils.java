package me.weebo.utilities;

import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/*
 * Does things about players.
 */
public class PlayerUtils {
	
	public static void reset(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[4]);
		p.getInventory().setHeldItemSlot(0);
		p.updateInventory();
		p.setHealth(20.0);
		p.setFoodLevel(20);
		p.setExp(0);
		p.setLevel(0);
		p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
		if (p.getGameMode() != GameMode.SURVIVAL) p.setGameMode(GameMode.SURVIVAL);
	}
	
	public static void lobbyItems(Player p) {
		reset(p);
		p.getInventory().setItem(0, new EasyItem(Material.INK_SACK).setName("&a&lJoin Current Game").setId(DyeColor.PURPLE.getData()).setLore("&7Right-click to join a game").build());
		p.getInventory().setItem(1, new EasyItem(Material.MAGMA_CREAM).setName("&e&lSpectate").setLore("&7Right-click to spectate a game").build());
		p.getInventory().setItem(4, new EasyItem(Material.NETHER_STAR).setName("&9&lHost a Game").setLore("&7Right-click to create a game").build());
		p.getInventory().setItem(7, new EasyItem(Material.SKULL_ITEM).setName("&2&lLeaderboard").setId((short) 3).setLore("&7Right-click to open leaderboard").build());
		p.getInventory().setItem(8, new EasyItem(Material.REDSTONE_COMPARATOR).setName("&3&lPreferences").setLore("&7Right-click to change settings").build());
		/*p.getInventory().setItem(0, new EasyItem(Material.INK_SACK).setName(C.JOIN_GAME).setId(DyeColor.PURPLE.getData()).setLore("&7Right-click to join a game").build());
		p.getInventory().setItem(1, new EasyItem(Material.MAGMA_CREAM).setName(C.SPECTATE_GAME).setLore("&7Right-click to spectate a game").build());
		p.getInventory().setItem(4, new EasyItem(Material.NETHER_STAR).setName(C.HOST_GAME).setLore("&7Right-click to create a game").build());
		p.getInventory().setItem(7, new EasyItem(Material.SKULL_ITEM).setName(C.LEADERBOARDS).setId((short) 3).setLore("&7Right-click to open leaderboard").build());
		p.getInventory().setItem(8, new EasyItem(Material.REDSTONE_COMPARATOR).setName(C.PREFERENCES).setLore("&7Right-click to change settings").build());*/
		//p.getInventory().setItem(8, new EasyItem(Material.INK_SACK).setName("&c&lDisconnect").setId(DyeColor.ORANGE.getData()).setLore("&7Right-click to leave the server.").build());
		p.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public static void waitingItems(Player p) {
		reset(p);
		p.getInventory().setItem(0, new EasyItem(Material.INK_SACK).setName("&c&lLeave Game").setId(DyeColor.ORANGE.getData()).setLore("&7Right-click to leave the game.").build());
		p.getInventory().setItem(7, new EasyItem(Material.SKULL_ITEM).setName("&2&lLeaderboard").setId((short) 3).setLore("&7Right-click to open leaderboard").build());
		p.getInventory().setItem(8, new EasyItem(Material.REDSTONE_COMPARATOR).setName("&3&lPreferences").setLore("&7Right-click to change settings").build());
		/*p.getInventory().setItem(0, new EasyItem(Material.INK_SACK).setName(C.LEAVE_GAME).setId(DyeColor.ORANGE.getData()).setLore("&7Right-click to leave the game.").build());
		p.getInventory().setItem(7, new EasyItem(Material.SKULL_ITEM).setName(C.LEADERBOARDS).setId((short) 3).setLore("&7Right-click to open leaderboard").build());
		p.getInventory().setItem(8, new EasyItem(Material.REDSTONE_COMPARATOR).setName(C.PREFERENCES).setLore("&7Right-click to change settings").build());*/
		p.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public static void spectateItems(Player p) {
		reset(p);
		p.getInventory().setItem(0, new EasyItem(Material.COMPASS).setName("&e&lTeleport").setLore("&7Left-click to teleport to a random player.", "&7Right-click to teleport specific player.").build());
		p.getInventory().setItem(1, new EasyItem(Material.BOOK).setName("&e&lView Inventory").setLore("&7Right-click at a player to view their inventory.").build());
		p.getInventory().setItem(8, new EasyItem(Material.INK_SACK).setName("&c&lStop Spectating").setId(DyeColor.ORANGE.getData()).setLore("&7Right-click to stop spectating.").build());
		/*p.getInventory().setItem(0, new EasyItem(Material.COMPASS).setName("&e&lTeleport").setLore("&7Left-click to teleport to a random player.", "&7Right-click to teleport specific player.").build());
		p.getInventory().setItem(1, new EasyItem(Material.BOOK).setName(C.INSPECT_ITEM).setLore("&7Right-click at a player to view their inventory.").build());
		p.getInventory().setItem(8, new EasyItem(Material.INK_SACK).setName(C.STOP_SPECTATING).setId(DyeColor.ORANGE.getData()).setLore("&7Right-click to stop spectating.").build());*/
		p.updateInventory();
	}
}
