package me.weebo.commands.staff;

import me.weebo.PotionSG;
import me.weebo.utilities.BukkitReflection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetSlotsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(PotionSG.PERM_ADMIN)) {
            sender.sendMessage("§cInsufficient permissions");
            return true;
        } else if (args.length != 1) {
            sender.sendMessage("§c/setslots <slots>");
            return true;
        } else {
            Integer slots;
            try {
                slots = Integer.parseInt(args[0]);
            } catch (Exception var6) {
                sender.sendMessage("§cNot a valid number.");
                return true;
            }
            BukkitReflection.setMaxPlayers(Bukkit.getServer(), slots);
            sender.sendMessage(String.format("§aSuccessfully set the slots to §2%s§a.", slots));
            return true;
        }
    }
}


