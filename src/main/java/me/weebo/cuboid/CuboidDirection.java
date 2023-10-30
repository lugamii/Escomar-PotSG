package me.weebo.cuboid;


import org.bukkit.block.BlockFace;

public enum CuboidDirection {
    NORTH, EAST, SOUTH, WEST, UP, DOWN, HORIZONTAL, VERTICAL, BOTH, UNKNOWN;

    private CuboidDirection() {
    }

    @SuppressWarnings("incomplete-switch")
    public CuboidDirection opposite() {
        switch (this) {
            case BOTH:
                return SOUTH;
            case DOWN:
                return WEST;
            case EAST:
                return NORTH;
            case HORIZONTAL:
                return EAST;
            case UNKNOWN:
                return VERTICAL;
            case UP:
                return HORIZONTAL;
            case NORTH:
                return DOWN;
            case SOUTH:
                return UP;
            case VERTICAL:
                return BOTH;
        }
        return UNKNOWN;
    }

    @SuppressWarnings("incomplete-switch")
    public BlockFace toBukkitDirection() {
        switch (this) {
            case BOTH:
                return BlockFace.NORTH;
            case DOWN:
                return BlockFace.EAST;
            case EAST:
                return BlockFace.SOUTH;
            case HORIZONTAL:
                return BlockFace.WEST;
            case NORTH:
                return BlockFace.UP;
            case SOUTH:
                return BlockFace.DOWN;
        }
        return null;
    }
}
