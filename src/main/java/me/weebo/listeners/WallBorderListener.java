package me.weebo.listeners;

import com.google.common.base.Predicate;
import me.weebo.PotionSG;
import me.weebo.barrier.VisualBlock;
import me.weebo.barrier.VisualType;
import me.weebo.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import me.weebo.utilities.LocationUtils;

import java.util.Collection;

public class WallBorderListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (LocationUtils.readGameSpawn() != null) {
            if (PotionSG.getInst().isStarted() && PotionSG.getInst().getRad() != 0 && event.getPlayer().getWorld().getName().equals(LocationUtils.readGameSpawn().getWorld().getName())) {
                Location to = event.getTo();
                int toX = to.getBlockX();
                int toY = to.getBlockY();
                int toZ = to.getBlockZ();
                Location from = event.getFrom();
                if ((from.getBlockX() != toX) || (from.getBlockY() != toY) || (from.getBlockZ() != toZ)) {
                    handlePositionChanged(event.getPlayer(), to.getWorld(), toX, toY, toZ);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        onPlayerMove(event);
    }

    private void handlePositionChanged(Player player, final World toWorld, final int toX, final int toY, final int toZ) {
        if (LocationUtils.readGameSpawn() != null) {
            VisualType visualType = VisualType.WORLD_BORDER;

            PotionSG.getInst().getVisualiseHandler().clearVisualBlocks(player, visualType, new Predicate<VisualBlock>() {
                public boolean apply(VisualBlock visualBlock) {
                    Location other = visualBlock.getLocation();
                    return (other.getWorld().equals(toWorld)) && ((Math.abs(toX - other.getBlockX()) > 5) || (Math.abs(toY - other.getBlockY()) > 4) || (Math.abs(toZ - other.getBlockZ()) > 5));
                }
            });
            int minHeight = toY - 3;
            int maxHeight = toY + 4;

            double x = PotionSG.getInst().getRad();
            double z = PotionSG.getInst().getRad();

            Location loc1 = new Location(toWorld, LocationUtils.readGameSpawn().getX() + x, 0.0D, LocationUtils.readGameSpawn().getZ() - z);
            Location loc2 = new Location(toWorld, LocationUtils.readGameSpawn().getX() - x, 0.0D, LocationUtils.readGameSpawn().getZ() + z);

            Cuboid cb = new Cuboid(loc1, loc2);

            Collection<Vector> edges = cb.edges();
            for (Vector edge : edges) {
                if (Math.abs(edge.getBlockX() - toX) <= 5) {
                    if (Math.abs(edge.getBlockZ() - toZ) <= 5) {
                        Location location = edge.toLocation(toWorld);
                        if (location != null) {
                            Location first = location.clone();
                            first.setY(minHeight);
                            Location second = location.clone();
                            second.setY(maxHeight);

                            PotionSG.getInst().getVisualiseHandler().generate(player, new Cuboid(first, second), visualType, false).size();
                        }
                    }
                }
            }
        }
    }
}