package me.weebo.managers.types;

import lombok.Getter;
import me.weebo.PotionSG;
import org.bukkit.scheduler.BukkitRunnable;

public class HostManager {

    private final int TARGET_PLAYERS;
    @Getter private static int additionalTime;

    public HostManager() {
        this.TARGET_PLAYERS = 10;
    }

    public void check() {
        if (PotionSG.getInst().getPlayersWaiting().size() == TARGET_PLAYERS) startTask();
    }

    private void startTask() {
        this.additionalTime = 120;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!PotionSG.getInst().isStarted() && PotionSG.getInst().getPlayersWaiting().size() >= TARGET_PLAYERS) {
                    if (additionalTime != 0) additionalTime--;
                    else {
                        PotionSG.getInst().start();
                        cancel();
                    }
                } else {
                    additionalTime = 0;
                    cancel();
                }
            }
        }.runTaskTimer(PotionSG.getInst(), 0L, 20L);
    }

}
