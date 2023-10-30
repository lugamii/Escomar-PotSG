package me.weebo.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import lombok.Setter;
import me.weebo.PotionSG;

public class GameTask extends BukkitRunnable {

    @Getter
    @Setter
    private static int duration;

    public GameTask() {
        duration++;
        duration = 0;
        PotionSG.getInst().getTasksManager().schedule(getFormattedTime(), this, 0L, 20L);
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
        }
    }
        public static String getFormattedTime() {
            int hour = 0 , minute = 0, second = 0;

            minute = duration / 60;
            second = duration % 60;

            if (minute > 59) {
                hour = minute / 60;
                minute = minute % 60;
            }

            String tempHour = String.valueOf(hour);
            String tempMinute = String.valueOf(minute);
            String tempSecond = String.valueOf(second);
            if (hour < 10) {
                tempHour = "0" + hour;
            }
            if (minute < 10) {
                tempMinute = "0" + minute;
            }
            if (second < 10) {
                tempSecond = "0" + second;
            }
            return tempMinute + ":" + tempSecond;
        }

}
