package me.weebo.managers.scenarios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.weebo.PotionSG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryManager {
	
	public InventoryManager() {

	}

    public List<String> getActiveScenarios() {
    	final ArrayList<String> Lore = new ArrayList<String>();
        for (final Scenario scenario: PotionSG.getInstance().getScenarioManager().getActiveScenarios()) {
            Lore.add(scenario.getName());
        }
        if (Lore.size() == 0) {
            Lore.add("§cThere isn't any active scenarios!");
        }
        return Lore;
    }
    
    public List<String> getWithStatus(Scenario scenario) {
    	final ArrayList<String> Lore = new ArrayList<String>();
        for (final String value: scenario.getDescription()) {
            Lore.add(value);
            if (value.contains(".")) {
                Lore.add("");
				Lore.add("§fStatus: " + (scenario.isActive() ? "§aEnabled" : "§cDisabled"));
            }
        }
        return Lore;
    }
    public List<String> getWithNonStatus(Scenario scenario) {
		final ArrayList<String> Lore = new ArrayList<String>();
		for (final String value: scenario.getDescription()) {
			Lore.add(value);
		}
		return Lore;
	}

	public void createScen(Player player, int step) {
		switch (step) {
			case 1: {
				final Inventory inv1 = Bukkit.createInventory(null, 36, "§b§lHost a Game");
				for (int x = 0; x < 9; x++) {
					if (x == 4) {
						continue;
					}
					if (x == 1) {
						continue;
					}
					if (x == 7) {
						continue;
					}
					this.createDisplay(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8), inv1, x, " ", null);
				}

				final ArrayList<String> totalscen = new ArrayList<>();
				totalscen.add("");
				totalscen.add("§7There are only §3" + PotionSG.getInst().getScenarioManager().getScenarios().size() + " §7currently however more can be");
				totalscen.add("§a§ladded §7to the server if there's any good suggestions");
				totalscen.add("§7for §3PotSG §7related scenarios");
				totalscen.add("");
				totalscen.add("§3§lExplanation §fon §3Scenarios:");
				totalscen.add("§3Scenarios §7are special events which modify a");
				totalscen.add("§7certain §3aspect §fof the game");
				totalscen.add("");
				totalscen.add("§7Number of Scenarios Enabled: §a" + PotionSG.getInst().getScenarioManager().getActiveScenarios().size() + "§7/§f" + PotionSG.getInst().getScenarioManager().getScenarios().size());

				final ArrayList<String> teamsize = new ArrayList<String>();
				teamsize.add("§fCurrent TeamSize: §aFFA §7| §cTo2");
				teamsize.add("§f");
				teamsize.add("§3§lNOTE: §fAs of currently you");
				teamsize.add("§c§lCANNOT §fmodify the §3Team Size");
				teamsize.add("§fsince this feature is being §3worked");
				teamsize.add("§3on §fto make sure there's no §3bugs");

				this.createDisplay(new ItemStack(Material.REDSTONE, 1, (short) 14), inv1, 4, "§cDisable All Scenarios", null);
				this.createDisplay(new ItemStack(Material.BOOK, 1, (short) 14), inv1, 1, "§f* §3§lScenarios §f*", totalscen);
				this.createDisplay(new ItemStack(Material.DIAMOND_CHESTPLATE, 0, (short) -1), inv1, 7, "§bTeam Size",teamsize);
				int count = 9;
				for (final Scenario scenario : PotionSG.getInst().getScenarioManager().getScenarios()) {
					this.createDisplay(new ItemStack(scenario.getItem()), inv1, count, scenario.getName(), getWithStatus(scenario));
					++count;
				}
				for (int x = 27; x < 36; x++) {
					if (x == 31) {
						continue;
					}
					this.createDisplay(new ItemStack(Material.EMERALD, 0, (short) -1), inv1, 31, "§a§lHost Game",null);
					this.createDisplay(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8), inv1, x, " ", null);
				}
				player.openInventory(inv1);
				return;
			}
			case 2: {
				final Inventory inv2 = Bukkit.createInventory(null, 27, "§3§lGame Information");
				final ArrayList<String> gameinfo = new ArrayList<String>();
				gameinfo.add("§f");
				gameinfo.add("§fTeam Size: §3FFA");
				gameinfo.add("§fNumber of scenarios enabled: §3" + PotionSG.getInst().getScenarioManager().getActiveScenarios().size() + "§7/§f" + PotionSG.getInst().getScenarioManager().getScenarios().size());
				gameinfo.add("§3Scenarios §faffects a certain aspect of the game");
				gameinfo.add("§fThe §3scenarios information §fwill be listed below via §3items");
				this.createDisplay(new ItemStack(Material.BOOK, 1, (short) 14), inv2, 4, "§f* §3§lGame Information §f*", gameinfo);
				int count = 9;
				for (final Scenario scenario : PotionSG.getInst().getScenarioManager().getActiveScenarios()) {
					this.createDisplay(new ItemStack(scenario.getItem()), inv2, count, scenario.getName(), getWithNonStatus(scenario));
					++count;
				}
				player.openInventory(inv2);
				return;
			}
		}
	}
    
    public void createDisplay(ItemStack redwool, Inventory inv, int Slot, String name, Collection<String> lore) {
    	final ItemStack item = new ItemStack(redwool);
    	final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        final ArrayList<String> Lore = new ArrayList<String>();
        if (lore != null) {
        	Lore.addAll(lore);
            meta.setLore(Lore);
        }
        item.setItemMeta(meta);
        inv.setItem(Slot, item);
    }
	
}
