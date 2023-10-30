package me.weebo.scenarios;

import me.weebo.PotionSG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import me.weebo.managers.scenarios.Scenario;


public class Enchanter implements Scenario, Listener {

    boolean active = false;

    @Override
    public String getName() {
        return "§3Enchanter";
    }

    @Override
    public Material getItem() {
        return Material.BOOK;
    }

    @Override
    public String[] getDescription() {
        return new String[] { "§f", "§fPlayers get §3infinite §famount of §3XP"};
    }

    @Override
    public void setActive(boolean active) {
            this.active = active;
            if(!this.active) {
                Bukkit.getPluginManager().registerEvents(this, (Plugin) PotionSG.getInstance());
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "xp 100000L" + Bukkit.getOnlinePlayers());
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "xp 100000L @a");
            }
        }

    @Override
    public boolean isActive() {
        return active;
    }


}
