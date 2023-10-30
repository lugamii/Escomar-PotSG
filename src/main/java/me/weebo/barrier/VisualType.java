package me.weebo.barrier;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum VisualType {

    WORLD_BORDER() {
        private final BlockFiller blockFiller = new BlockFiller() {
            @SuppressWarnings("deprecation")
            @Override
            VisualBlockData generate(Player player, Location location) {
                return new VisualBlockData(Material.STAINED_GLASS, DyeColor.PINK.getData());
            }
        };
        @Override
        BlockFiller blockFiller() {
            return blockFiller;
        }
    };

    abstract BlockFiller blockFiller();
}