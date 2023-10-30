package me.weebo.scenarios;

import me.weebo.PotionSG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;
import me.weebo.managers.scenarios.Scenario;

public class Soup implements Scenario, Listener {

    boolean active = false;

    @Override
    public String getName() {
        return "§3Soup";
    }

    @Override
    public Material getItem() {
        return Material.MUSHROOM_SOUP;
    }

    @Override
    public String[] getDescription() {
        return new String[] { "§f", "§fWhen a player eats a", "§3Mushroom Soup §fthey gain §chealth."};
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
    public void onEat(PlayerItemConsumeEvent event) {
        if(PotionSG.getInst().isStarted()) {
            if (event.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
                int v = (int) (event.getPlayer().getHealth() + 7);
                event.getPlayer().setHealth((v >= event.getPlayer().getMaxHealth() ? event.getPlayer().getMaxHealth() : v));
                event.getPlayer().getItemInHand().setType(Material.BOWL);
                event.getPlayer().getInventory().remove(Material.BOWL);
            }
        }
    }

}
