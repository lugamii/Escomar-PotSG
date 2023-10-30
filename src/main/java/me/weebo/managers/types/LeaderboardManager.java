package me.weebo.managers.types;

import lombok.Getter;
import me.weebo.utilities.C;
import me.weebo.utilities.EasyItem;
import me.weebo.utilities.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class LeaderboardManager {

    @Getter private Map<UUID, Integer> killsLB, winsLB;

    public LeaderboardManager() {
        killsLB = new HashMap<>();
        winsLB = new HashMap<>();
        updateWins();
        updateKills();
    }

    public void openLeaderboard(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, C.GUI_TITLE_COLOR + "Leaderboard");
        inv.setItem(13, new EasyItem(Material.SKULL_ITEM).setName(C.GUI_MAIN_COLOR + "Your Statistics").setLore(C.GUI_FIRST_COLOR + "Kills: " + C.GUI_SECONDARY_COLOR + getKills(p.getUniqueId()), C.GUI_FIRST_COLOR + "Wins: " + C.GUI_SECONDARY_COLOR + getWins(p.getUniqueId())).setId((short) 3).build());
        EasyItem kills = new EasyItem(Material.DIAMOND_SWORD).setName(C.GUI_MAIN_COLOR + "Top 10 Killers");
        EasyItem wins = new EasyItem(Material.NETHER_STAR).setName(C.GUI_MAIN_COLOR + "Top 10 Winners");
        int i = 1;
        for (Map.Entry<UUID, Integer> e : killsLB.entrySet()) {
            if (i > 10) break;
            kills.addLore(C.GUI_FIRST_COLOR + i + ". " + C.GUI_SECONDARY_COLOR + Bukkit.getOfflinePlayer(e.getKey()).getName() + C.GUI_THIRD_COLOR + " (" + e.getValue() + (e.getValue() == 1 ? " kill)" : " kills)"));
            i++;
        }
        i = 1;
        for (Map.Entry<UUID, Integer> e : winsLB.entrySet()) {
            if (i > 10) break;
            wins.addLore(C.GUI_FIRST_COLOR + i + ". " + C.GUI_SECONDARY_COLOR + Bukkit.getOfflinePlayer(e.getKey()).getName() + C.GUI_THIRD_COLOR + " (" + e.getValue() + (e.getValue() == 1 ? " win)" : " wins)"));
            i++;
        }
        inv.setItem(29, kills.build());
        inv.setItem(33, wins.build());
        p.openInventory(inv);
    }

    private void load() {
        FileConfiguration fileconfig = FileUtils.getPlayerDataYML();
        for (String s : fileconfig.getConfigurationSection("").getKeys(false)) killsLB.put(UUID.fromString(s), getKills(UUID.fromString(s)));
        for (String s : fileconfig.getConfigurationSection("").getKeys(false)) winsLB.put(UUID.fromString(s), getWins(UUID.fromString(s)));
    }

    private void updateWins() {
        load();
        winsLB = winsLB.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    private void updateKills() {
        load();
        killsLB = killsLB.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public int getKills(UUID id) {
        FileConfiguration fileconfig = FileUtils.getPlayerDataYML();
        return fileconfig.getInt(id.toString() + ".kills", 0);
    }

    public int getWins(UUID id) {
        FileConfiguration fileconfig = FileUtils.getPlayerDataYML();
        return fileconfig.getInt(id.toString() + ".wins", 0);
    }

    private void saveKills(UUID id, int kills) {
        FileConfiguration fileconfig = FileUtils.getPlayerDataYML();
        fileconfig.set(id.toString() + ".kills", kills);
        FileUtils.saveProfilesFile();
    }
    private void saveWins(UUID id, int wins) {
        FileConfiguration fileconfig = FileUtils.getPlayerDataYML();
        fileconfig.set(id.toString() + ".wins", wins);
        FileUtils.saveProfilesFile();
    }

    public void addKill(UUID id) {
        saveKills(id, getKills(id) + 1);
        updateKills();
    }

    public void addWin(UUID id) {
        saveWins(id, getWins(id) + 1);
        updateWins();
    }
}
