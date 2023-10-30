package me.weebo.managers.types;

import java.util.HashMap;
import java.util.Map;

import me.weebo.PotionSG;
import me.weebo.tasks.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import lombok.Getter;

public class TasksManager {

    @Getter
    public Map<String, BukkitTask> scheduledTasks = new HashMap<>();

    public void schedule(String id, GameTask run, long time, long repeat) {
        scheduledTasks.put(id, Bukkit.getScheduler().runTaskTimer(PotionSG.getInstance(), run, time, repeat));
    }

    public void stop(String id) {
        scheduledTasks.remove(id).cancel();
    }

}
