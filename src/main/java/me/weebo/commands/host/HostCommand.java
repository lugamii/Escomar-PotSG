package me.weebo.commands.host;

import me.weebo.PotionSG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HostCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] arg) {
        if (s instanceof Player) {
            Player p = (Player) s;
            if (p.hasPermission(PotionSG.PERM_MOD) || p.hasPermission(PotionSG.PERM_ACCESS)) {
                PotionSG.getInst().getInventoryManager().createScen(p, 1);
            } else {
                p.sendMessage("No Permissions!");
            }
        }
        return true;
    }

}
