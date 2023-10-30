package me.weebo.scenarios;

import me.weebo.PotionSG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import me.weebo.managers.scenarios.Scenario;
import me.weebo.utilities.ItemBuilder;

public class PreEnchants implements Scenario, Listener {

    boolean active = false;

    @Override
    public String getName() {
        return "§3PreEnchants";
    }

    @Override
    public Material getItem() {
        return Material.ENCHANTMENT_TABLE;
    }

    @Override
    public String[] getDescription() {
        return new String[] { "§f", "§fWhen a player crafts a §3sword §for §3armor", "§fit comes with §3Unbreaking 3 §f& §3Sharpness 1 §for §3Protection 1."};
    }

    @Override
    public void setActive(boolean active) {
            this.active = active;
            Bukkit.getPluginManager().registerEvents(this, (Plugin) PotionSG.getInstance());
        }
    @Override
    public boolean isActive() {
        return active;
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        if (!this.isActive()) {
            return;
        }
        final Material item = event.getRecipe().getResult().getType();
        if (item.name().contains("SWORD")) {
            event.getInventory().setResult(new ItemBuilder(item).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.DAMAGE_ALL, 1).create());
        }
        if (item.name().contains("HELMET") || item.name().contains("CHESTPLATE") || item.name().contains("LEGGINGS") || item.name().contains("BOOTS")) {
            event.getInventory().setResult(new ItemBuilder(item).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).create());
        }
    }
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (!this.isActive()) {
            return;
        }
        final Material type = event.getClickedBlock().getType();
        if (type == Material.ENCHANTMENT_TABLE || type == Material.ANVIL) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cYou can't use this when the Pre-Enchants scenario is enabled.");
        }
    }
}
