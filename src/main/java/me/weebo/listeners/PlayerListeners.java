package me.weebo.listeners;

import me.weebo.PotionSG;
import me.weebo.managers.Managers;
import me.weebo.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.help.HelpTopic;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

/*
 * Listens to events related to players.
 */
public class PlayerListeners implements Listener {

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if ((e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (!(Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0) && !Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isBuild())
                e.setCancelled(true);
            if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0) {
                    e.setCancelled(true);
                    Managers.getMapManager().dropFeastItems(e.getClickedBlock().getLocation());
                    e.getClickedBlock().setType(Material.AIR);
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.GLASS, 10, 1);
                }
            }
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.CHEST) {
                if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    if (!Managers.getMapManager().chestIsOpened(chest)) Managers.getMapManager().fillChest(chest);
                    e.getClickedBlock().setType(Material.AIR);
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ZOMBIE_WOODBREAK, 10, 1);
                }
            }
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.CHEST) {
                if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0) {
                    Managers.getMapManager().fillChest((Chest) e.getClickedBlock().getState());
                    e.getClickedBlock().setType(Material.AIR);
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ZOMBIE_WOODBREAK, 10, 1);
                }
            }
        }
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().getType() != null && e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getDisplayName() != null) {
                if (equals(e, "Teleport")) {
                    e.setCancelled(true);
                    List<Player> list = PotionSG.getInst().getPlayersInGame();
                    e.getPlayer().teleport(list.get(new Random().nextInt(list.size())).getLocation());
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player p = e.getPlayer();
            if (e.getItem() == null) return;
            if (e.getItem().getType() == null) return;
            if (e.getItem().getType() == Material.ENDER_PEARL) {
                if (Managers.getPearlManager().isInCooldown(p)) {
                    e.setCancelled(true);
                    C.c(p, "&cYou are on pearl cooldown for " + Managers.getPearlManager().getCooldownIn1DP(p) + " seconds.");
                } else Managers.getPearlManager().addPearlCooldown(p);
            }
            if (e.getItem().getItemMeta() == null) return;
            if (e.getItem().getItemMeta().getDisplayName() == null) return;
            e.setCancelled(true);
            if (equals(e, "Join Current Game")) {
                if (PotionSG.getInst().isIdle()) C.c(p, "&e\u26A0 &cThere is no game available.");
                else if (PotionSG.getInst().isWaiting()) PotionSG.getInst().join(p);
                else C.c(p, "&cThe game has already started, please use right-click the spectate item in your hotbar.");
            } else if (equals(e, "Host a Game")) {
                /*if (p.hasPermission(PotionSG.PERM_HOST) || p.hasPermission(PotionSG.PERM_ACCESS) || p.hasPermission(PotionSG.PERM_MOD)) {
                    if (PotionSG.getInst().isIdle()) {
                        if (PotionSG.getInst().getCooldownTimer() > 0)
                            C.c(p, "&e\u26A0 &cPlease wait for another " + PotionSG.getInst().getCooldownTimer() + " seconds before hosting another game.");
                        else PotionSG.getInst().hostGame(p.getName());
                    } else if (PotionSG.getInst().isWaiting()) C.c(p, "&e\u26A0 &cThe game has already started!");
                    else C.c(p, "&e\u26A0 &cThe game has already started!");
                } else
                    new JsonMessage().append(C.c("&e\u26A0 " + C.CHAT_SECONDARY + "Hosting a game requires at least &6Inaros" + C.CHAT_SECONDARY + " or above. You can purchase ")).save().append(C.MAIN + "here").setClickAsURL("http://discord.nekros.club").setHoverAsTooltip(C.c("&eClick to go to our discord")).save().append(C.CHAT_SECONDARY + ".").save().send(p);*/
                p.performCommand("host");
            } else if (equals(e, "Spectate")) {
                if (PotionSG.getInst().isIdle()) C.c(p, "&e\u26A0 &cThere is no game available.");
                else if (PotionSG.getInst().isWaiting()) C.c(p, "&e\u26A0 &cThe game has not yet started.");
                else if (PotionSG.getInst().getPlayersInGame().size() < 1) C.c(p, "&e\u26A0 &cThe game has ended.");
                else PotionSG.getInst().spectate(p, true);
            } else if (equals(e, "Disconnect")) p.kickPlayer(null);
            else if (equals(e, "Leave Game")) {
                PotionSG.getInst().msgAll(C.MAIN + p.getName() + C.CHAT_SECONDARY + " left the game.");
                PotionSG.getInst().leaveGame(p);
            } else if (equals(e, "Stop Spectating")) PotionSG.getInst().stopSpectating(p, true);
            else if (equals(e, "Teleport")) p.openInventory(Managers.getUpdatedInventoryManager().getSpectateList());
            else if (equals(e, "Leaderboard")) Managers.getLeaderboardManager().openLeaderboard(p);
            else if (equals(e, "Preferences")) p.performCommand("settings");
        }
    }

    private boolean equals(PlayerInteractEvent e, String name) {
        return ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equals(name);
    }

    @SuppressWarnings("unused")
    private boolean startsWith(PlayerInteractEvent e, String name) {
        return ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).startsWith(name);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0)
            return;
        if (!Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isBuild()) e.setCancelled(true);
    }

    @EventHandler
    public void open(InventoryOpenEvent e) {
        if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0) {
            if (e.getInventory().getType() != InventoryType.CHEST) return;
            Chest chest = (Chest) e.getInventory().getHolder();
            if (Managers.getMapManager().chestIsOpened(chest)) return;
            Managers.getMapManager().fillChest(chest);
        }
    }

//    @EventHandler
//    public void onBrewing(BrewEvent e) {
//        BrewingStand brewingstand = (BrewingStand) e.getBlock().getState();
//        brewingstand.setBrewingTime(1);
//    }

    @EventHandler
    public void inv(InventoryClickEvent e) {
        if (Managers.getProfileManager().getProfile(e.getWhoClicked().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0)
            return;
        if (Managers.getProfileManager().getProfile(e.getWhoClicked().getUniqueId()).isSpectating()) {
            if (e.getCurrentItem() != null || e.getCurrentItem().getType() != Material.AIR) {
                if (ChatColor.stripColor(e.getInventory().getTitle()).equals("Teleport to...")) {
                    if (Bukkit.getPlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) != null)
                        e.getWhoClicked().teleport(Bukkit.getPlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())).getLocation());
                } else e.setCancelled(true);
            }
        }
        if (!Managers.getProfileManager().getProfile(e.getWhoClicked().getUniqueId()).isBuild()) e.setCancelled(true);
    }

    @EventHandler
    public void interact(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            Player t = (Player) e.getRightClicked();
            if (e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == Material.AIR) return;
            if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals("View Inventory")) {
                if (Managers.getProfileManager().getProfile(t.getUniqueId()).isInGame() && Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isSpectating()) {
                    Inventory inv = Bukkit.createInventory(null, 45, ChatColor.YELLOW + "Inventory of " + t.getName());
                    int i = 27;
                    for (int j = 0; j <= 8; j++) {
                        inv.setItem(i, t.getInventory().getContents()[j]);
                        i++;
                    }
                    int k = 18;
                    for (int j = 27; j < t.getInventory().getContents().length; j++) {
                        inv.setItem(k, t.getInventory().getContents()[j]);
                        k++;
                    }
                    int l = 9;
                    for (int j = 18; j <= 26; j++) {
                        inv.setItem(l, t.getInventory().getContents()[j]);
                        l++;
                    }
                    int m = 0;
                    for (int j = 9; j <= 17; j++) {
                        inv.setItem(m, t.getInventory().getContents()[j]);
                        m++;
                    }
                    int n = 39;
                    for (ItemStack it : t.getInventory().getArmorContents()) {
                        inv.setItem(n, it);
                        n--;
                    }
                    e.getPlayer().openInventory(inv);
                }
            }
        }
    }

    @EventHandler
    public void hunger(FoodLevelChangeEvent e) {
        if (!Managers.getProfileManager().getProfile(e.getEntity().getUniqueId()).isInGame() || (e.getFoodLevel() < ((Player) e.getEntity()).getFoodLevel() && new Random().nextInt(100) > 4))
            e.setCancelled(true);
    }

    @EventHandler
    public void damage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (Managers.getProfileManager().getProfile(e.getEntity().getUniqueId()).isInGame() && PotionSG.getInst().isPvp() && !Managers.getProfileManager().getProfile(e.getEntity().getUniqueId()).isFrozen())
                return;
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            if (Managers.getProfileManager().getProfile(e.getEntity().getUniqueId()).isInGame() && Managers.getProfileManager().getProfile(e.getDamager().getUniqueId()).isInGame() && PotionSG.getInst().isPvp())
                return;
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void pickup(PlayerPickupItemEvent e) {
        if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isSpectating()) e.setCancelled(true);
    }

    @EventHandler
    public void death(PlayerDeathEvent e) {
        if (Managers.getProfileManager().getProfile(e.getEntity().getUniqueId()).isInGame()) {
            if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player && !e.getEntity().getKiller().equals(e.getEntity())) Managers.getLeaderboardManager().addKill(e.getEntity().getKiller().getUniqueId());
            e.getEntity().setHealth(20.0);
            Managers.getPearlManager().removePearlCooldown(e.getEntity(), false);
            e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());
            PotionSG.getInst().msg("&c" + e.getDeathMessage() + ".");
            e.setDeathMessage(null);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (PotionSG.getInst().getPlayersInGame().size() != 2)
                        PotionSG.getInst().spectate(e.getEntity(), false);
                    else {
                        PotionSG.getInst().leaveGame(e.getEntity());
                        PotionSG.getInst().check();
                    }
                }
            }.runTaskLater(PotionSG.getInst(), 2L);

        }
    }

    @EventHandler
    public void closeInv(InventoryCloseEvent e) {
        if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0) {
            if (e.getInventory().getType() != InventoryType.CHEST) return;
            if (!isEmpty(e.getInventory().getContents())) return;
            Chest chest = (Chest) e.getInventory().getHolder();
//			Managers.getMapManager().addBlockRemoved(chest.getBlock());
            chest.getBlock().setType(Material.AIR);
            ((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.ZOMBIE_WOODBREAK, 10, 1);
        }
    }

    private boolean isEmpty(ItemStack[] i) {
        for (ItemStack item : i) if (item != null) return false;
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void join(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        if (LocationUtils.readLobbySpawn() != null) e.getPlayer().teleport(LocationUtils.readLobbySpawn());
        Managers.getProfileManager().createProfile(e.getPlayer());
        PlayerUtils.lobbyItems(e.getPlayer());
        for (int i = 0; i < 100; i++) e.getPlayer().sendMessage("");
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.PORTAL_TRAVEL, 0.2F, 1);
        /*C.c(e.getPlayer(),
                "&7&m--------------------------------------------------",
                "");
        sendCenteredMessage(e.getPlayer(), C.MAIN + C.SERVER_NAME + C.SEASON);
        String[] msg = new String[]{
                "Whoooooooooooooooosh!",
                "What should I type here?",
                "Pretty sure you are here just for PotSG!",
                "Yooo, Hanky with no brim!!",
                "69w"
//                "HANKY CHEATED ON KAGUYA AND STOLE MY CHIKAAAA"
        };
        sendCenteredMessage(e.getPlayer(), "&6\u2730 " + C.CHAT_SECONDARY + msg[new Random().nextInt(msg.length)]);
        C.c(e.getPlayer(), "", "&7&m--------------------------------------------------");*/
    }

    private final static int CENTER_PX = 154;

    public static void sendCenteredMessage(Player player, String message) {
        if (message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);
        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;
        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
                continue;
            } else if (previousCode == true) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                } else isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }
        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        C.c(player, sb.toString() + message);
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame()) {
            PotionSG.getInst().msg("&c" + e.getPlayer().getName() + " disconnected and has been disqualified.");
            Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).setInLobby();
            PotionSG.getInst().check();
        } else if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isWaiting()) PotionSG.getInst().msg(C.MAIN + e.getPlayer().getName() + C.CHAT_SECONDARY + " left the game.");
        else if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isSpectating()) PotionSG.getInst().stopSpectating(e.getPlayer(), true);
        Managers.getPearlManager().removePearlCooldown(e.getPlayer(), false);
        Managers.getProfileManager().removeProfile(e.getPlayer());
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if (!(Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0) && !Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isBuild())
            e.setCancelled(true);
        //else Managers.getMapManager().addBlockRemoved(e.getBlock());
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (!(Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInGame() && PotionSG.getInst().getStartTimer() < 0) && !Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isBuild()) e.setCancelled(true);
        else if (e.getBlockPlaced().getType() == Material.TNT) {
            e.getBlockPlaced().setType(Material.AIR);
            e.getBlockPlaced().getWorld().spawn(e.getBlockPlaced().getLocation(), TNTPrimed.class);
        }
        //else Managers.getMapManager().addBlockPlaced(e.getBlock());
    }

    @EventHandler
    public void epearl(PlayerTeleportEvent e) {
        if (e.getCause() == TeleportCause.ENDER_PEARL && (e.getTo().getX() < PotionSG.getInst().getBorderLocation1().getX() || e.getTo().getX() > PotionSG.getInst().getBorderLocation2().getX() || e.getTo().getZ() < PotionSG.getInst().getBorderLocation1().getZ() || e.getTo().getZ() > PotionSG.getInst().getBorderLocation2().getZ())) {
            e.setCancelled(true);
            e.getPlayer().getInventory().addItem(new EasyItem(Material.ENDER_PEARL).build());
            C.c(e.getPlayer(), "&e\u26A0 &cYou cannot pearl outside the border.");
        }
    }

    @EventHandler
    public void cmd(PlayerCommandPreprocessEvent e) {
        String str =
//        String[] str = new String[]{
//                "If you see this message, it means its time for you to type \"/help\".",
//                "Sorry, but it seems like that command does not exist!",
//                "We don't even know what that command is.",
//                "I know it's better to set this to \"Unknown command.\", but you guys are bored with that, I guess?",
                "Unknown command. :(";
//                "Do you know what you are doing? Maybe type \"/help\" for help!"
//        };
        if (!e.isCancelled()) {
            String cmd = e.getMessage().split(" ")[0];
            HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(cmd);
            if (topic == null || cmd.equalsIgnoreCase("me") || cmd.equalsIgnoreCase("say")) {
                C.c(e.getPlayer(), "&e\u26A0 &c" + str/*[new Random().nextInt(str.length)]*/);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void move(PlayerMoveEvent e) {
        if (Managers.getProfileManager().getProfile(e.getPlayer().getUniqueId()).isInLobby() && e.getTo().getBlockY() < 0) e.getPlayer().teleport(LocationUtils.readLobbySpawn());
    }

}
