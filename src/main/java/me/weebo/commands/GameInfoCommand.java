package me.weebo.commands;

import me.weebo.PotionSG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] arg) {
        if (s instanceof Player) {
            Player p = (Player) s;
                PotionSG.getInst().getInventoryManager().createScen(p, 2);
        }
        return true;
    }

}
