package me.weebo.menu;

import me.weebo.PotionSG;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player) {
            final Player player = (Player) event.getWhoClicked();
            final Page page = PotionSG.getInstance().getPlayerMenu().get(player.getUniqueId());
            if(page == null) {
                return;
            }
            event.setCancelled(true);
            if(page.isGoToNextPageOnEveryClick()) {
                player.closeInventory();
                page.getNextPage().build(player);
                return;
            }

            final int slot = event.getRawSlot();
            final Button button = page.getButton(slot);
            if(button != null && button.getNextPage() != null) {
                player.closeInventory();
                button.getNextPage().build(player);
                return;
            }

            if(page.getNextPageButton() != null && page.getNextPageButton().equals(button)) {
                player.closeInventory();
                page.getNextPage().build(player);
                return;
            }

            if(page.getPreviousPageButton() != null && page.getPreviousPageButton().equals(button)) {
                player.closeInventory();
                page.getPreviousPage().build(player);
                return;
            }
        }
    }

    @EventHandler
    public void InventoryClose(InventoryCloseEvent event) {
        if(event.getPlayer() instanceof Player) {
            PotionSG.getInstance().getPlayerMenu().remove(event.getPlayer().getUniqueId());
        }
    }

}
