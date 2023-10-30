package me.weebo.listeners;

import me.weebo.PotionSG;
import me.weebo.managers.scenarios.InventoryManager;
import me.weebo.managers.scenarios.Scenario;
import me.weebo.utilities.C;
import me.weebo.utilities.JsonMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class InventoryListener implements Listener {
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    	final Player player = (Player)event.getWhoClicked();
    	final ItemStack clicked = event.getCurrentItem();
    	final Inventory inventory = event.getInventory();
        
        if (clicked == null) {
        	return;
        }
        if (clicked.getType() == Material.AIR) {
        	return;
        }
        InventoryManager manager = PotionSG.getInstance().getInventoryManager();
        if (inventory.getTitle().equals("§b§lHost a Game")) {
        	event.setCancelled(true);
        	if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase( "§cDisable All Scenarios")) {
        		for (Scenario scenario : PotionSG.getInstance().getScenarioManager().getScenarios()) {
        			scenario.setActive(false);
        		}
        		player.closeInventory();
        		manager.createScen(player, 1);
        		player.sendMessage( "§7All §fscenarios have been §cdisabled§f.");
                return;
        	}
			if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase("§a§lHost Game")) {
				if (player.hasPermission(PotionSG.PERM_HOST) || player.hasPermission(PotionSG.PERM_ACCESS) || player.hasPermission(PotionSG.PERM_MOD)) {
					if (PotionSG.getInst().isIdle()) {
						if (PotionSG.getInst().getCooldownTimer() > 0)
							C.c(player, "&cPlease wait for another " + PotionSG.getInst().getCooldownTimer() + " seconds before hosting another game.");
						else PotionSG.getInst().hostGame(player.getName());
					} else if (PotionSG.getInst().isWaiting()) C.c(player, "&cThe game has already started!");
					else C.c(player, "&cThe game has already started!");
				} else
					new JsonMessage().append(C.c("" + "&cHosting a game requires at least a rank")).save().send(player);
				player.closeInventory();
				return;
			}
        	final Scenario scenario = PotionSG.getInstance().getScenarioManager().getScenario(clicked.getItemMeta().getDisplayName());
        	final ItemMeta meta = clicked.getItemMeta();
			if (scenario != null) {
				scenario.setActive(!scenario.isActive());
				player.sendMessage("§fScenario §7" + scenario.getName() + " " + (scenario.isActive() ? "§fhas been §aenabled§f." : "§fhas been §cdisabled§f."));
				meta.setLore(manager.getWithStatus(scenario));
				clicked.setItemMeta(meta);
				player.updateInventory();
				return;
            }
            return;	
        }

	}

}
