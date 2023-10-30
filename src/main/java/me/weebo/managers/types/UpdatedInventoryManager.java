package me.weebo.managers.types;

import java.util.List;

import me.weebo.PotionSG;
import me.weebo.utilities.C;
import me.weebo.utilities.EasyItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;

public class UpdatedInventoryManager extends BukkitRunnable {

	@Getter private Inventory spectateList;

	public UpdatedInventoryManager() {
		spectateList = Bukkit.createInventory(null, 54, C.c(C.GUI_TITLE_COLOR + "Teleport to..."));
		runTaskTimer(PotionSG.getInst(), 0L, 20L);
	}

	@Override
	public void run() {
		if (PotionSG.getInst().isStarted()) {
			spectateList.clear();
			List<Player> list = PotionSG.getInst().getPlayersInGame();
			for (int i = 0; i < list.size(); i++) spectateList.setItem(i, new EasyItem(Material.SKULL_ITEM).setId((short) 3).setName(C.CHAT_SECONDARY + list.get(i).getName()).build());
		}
	}

//	private int size(int original) {
//		if (original < 10) return 9;
//		else if (original < 19) return 18;
//		else if (original < 28) return 27;
//		else if (original < 37) return 36;
//		else if (original < 46) return 45;
//		else return 54;
//	}

}
