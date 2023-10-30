package me.weebo.scenarios;

import me.weebo.PotionSG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import me.weebo.managers.scenarios.Scenario;


public class NoFire implements Scenario, Listener {

    boolean active = false;

    @Override
    public String getName() {
        return "§3NoFire";
    }

    @Override
    public Material getItem() {
        return Material.FLINT_AND_STEEL;
    }

    @Override
    public String[] getDescription() {
        return new String[] { "§f", "§fPlayers §c§ldon't take damage from any", "§ftype of §6Fire damage."};
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
    public void onDamage(EntityDamageEvent event) {
        if (!this.isActive()) {
            return;
        }
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.FIRE_TICK || event.getCause() == DamageCause.LAVA) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (!this.isActive()) {
            return;
        }
        final Material type = event.getClickedBlock().getType();
        if (type == Material.FLINT_AND_STEEL) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cYou can't use this when the No-Fire scenario is enabled.");
            return;
        }
    }

}
