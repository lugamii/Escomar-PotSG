package me.weebo;

import lombok.Getter;
import lombok.Setter;
import me.missionary.board.BoardManager;
import me.missionary.board.settings.BoardSettings;
import me.missionary.board.settings.ScoreDirection;
import me.weebo.commands.staff.*;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import me.weebo.barrier.ProtocolLibHook;
import me.weebo.barrier.VisualiseHandler;
import me.weebo.border.Border;
import me.weebo.commands.GameInfoCommand;
import me.weebo.commands.host.CancelCmd;
import me.weebo.commands.host.HostCommand;
import me.weebo.commands.host.StartCmd;
import me.weebo.listeners.InventoryListener;
import me.weebo.listeners.PlayerListeners;
import me.weebo.listeners.WallBorderListener;
import me.weebo.listeners.WorldListeners;
import me.weebo.managers.Managers;
import me.weebo.managers.scenarios.InventoryManager;
import me.weebo.managers.scenarios.ScenarioManager;
import me.weebo.managers.types.SidebarManager;
import me.weebo.managers.types.TasksManager;
import me.weebo.managers.types.UpdatedInventoryManager;
import me.weebo.menu.MenuListener;
import me.weebo.menu.Page;
import me.weebo.utilities.C;
import me.weebo.utilities.LocationUtils;
import me.weebo.utilities.PlayerUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
 * The main class of the plugin.
 */
public class PotionSG extends JavaPlugin {

    public static final String PERM_ACCESS = "potionsg.access";
    public static final String PERM_MOD = "potionsg.mod";
    public static final String PERM_HOST = "potionsg.host";
    public static final String PERM_BUILD = "potionsg.build";
    public static final String PERM_ADMIN = "potionsg.admin";
    public int port = Bukkit.getPort();
    static PotionSG potsgscen;

    @Getter
    private static PotionSG inst;

    @Getter
    private GameState state;

    @Getter
    @Setter
    private String host;

    @Getter
    private int startTimer, pvpTimer, feastTimer, rad, borderTimer, feastRad, cooldownTimer, targetRad, gameTimer;

    @Getter
    private boolean pvp;

    @Getter
    private VisualiseHandler visualiseHandler;
    private BoardManager manager;

    @Getter
    String arena;

    @Getter
    TasksManager tasksManager;

    @Getter
    ScenarioManager scenarioManager;

    @Getter
    InventoryManager inventoryManager;

    @Getter
    Map<UUID, Page> playerMenu;

    public static PotionSG getInstance() {
        return potsgscen;
    }

    @Override
    public void onEnable() {
        //ProtocolLib
        if(this.getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "ProtocolLib depend not found!");
            Bukkit.shutdown();
            //Registred ProtocolLib
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "ProtocolLib found!");
        }
        //Multiverse-Core
        if(this.getServer().getPluginManager().getPlugin("Multiverse-Core") == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Multiverse-Core depend not found!");
            Bukkit.shutdown();
            //Registered Multiverse
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Multiverse-Core found!");
        }

        //GameTask gametask;
        //if(PotionSG.getInstance().isStarted()) {
            //gametask = new GameTask();
            //gametask.runTaskTimer(this, 0, 1 * 20);
        //}
        potsgscen = this;
        inst = this;
        state = GameState.IDLE;
        arena = "";
        scenarioManager = new ScenarioManager();
        regEvents();
        regCmds();
        registerManagers();
        getConfig().options().copyDefaults(true);
        saveConfig();
        manager = new BoardManager(this, BoardSettings.builder().boardProvider(new SidebarManager()).scoreDirection(ScoreDirection.UP).build());
        visualiseHandler = new VisualiseHandler();
        playerMenu = new ConcurrentHashMap<UUID, Page>();
        new UpdatedInventoryManager();
        Iterator<Recipe> it = Bukkit.getServer().recipeIterator();
        while (it.hasNext()) {
            Recipe rec = it.next();
            if (rec.getResult().getType() == Material.CHEST || rec.getResult().getType() == Material.ENDER_CHEST || rec.getResult().getType() == Material.TRAPPED_CHEST)
                it.remove();
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            Managers.getProfileManager().createProfile(p);
            PlayerUtils.lobbyItems(p);
        }
        ProtocolLibHook.hook();
        Managers.getMapManager().loadArenas();
        for (World w : Bukkit.getWorlds()) w.setDifficulty(Difficulty.NORMAL);
        Bukkit.broadcastMessage(C.c("&a&l\u24D8 &aPotionSG has been enabled."));
        //hostGame(Bukkit.getConsoleSender().getName());
    }

    @Override
    public void onDisable() {
        manager.onDisable();
		for (Player p : Bukkit.getOnlinePlayers()) p.kickPlayer("PotionSG is reloading / restarting");
    }

    public void registerConfigs() {
        saveDefaultConfig();

    }
    private void regEvents() {
        List<Listener> l = new ArrayList<>();
        l.add(new PlayerListeners());
        l.add(new WorldListeners());
        l.add(new WallBorderListener());
        l.add(new MenuListener());
        l.add(new InventoryListener());
        l.forEach(li -> Bukkit.getPluginManager().registerEvents(li, this));
    }

    private void regCmds() {
        Map<String, CommandExecutor> m = new HashMap<>();
        m.put("build", new BuildCmd());
        m.put("setlobbyspawn", new SetLobbySpawnCmd());
        m.put("arena", new ArenaCmd());
        m.put("cancel", new CancelCmd());
        m.put("start", new StartCmd());
        m.put("freeze", new FreezeCmd());
        m.put("host", new HostCommand());
        m.put("gameinfo", new GameInfoCommand());
        m.put("setslots", new SetSlotsCommand());
        m.forEach((s, c) -> getCommand(s).setExecutor(c));
    }
    private void registerManagers() {
        scenarioManager = new ScenarioManager();
        inventoryManager = new InventoryManager();
        tasksManager = new TasksManager();
    }


    public List<Player> getPlayersInGame() {
        List<Player> l = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers())
            if (Managers.getProfileManager().getProfile(p.getUniqueId()).isInGame()) l.add(p);
        return l;
    }


    public List<Player> getPlayersWaiting() {
        List<Player> l = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers())
            if (Managers.getProfileManager().getProfile(p.getUniqueId()).isWaiting()) l.add(p);
        return l;
    }


    public List<Player> getSpectators() {
        List<Player> l = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers())
            if (Managers.getProfileManager().getProfile(p.getUniqueId()).isSpectating()) l.add(p);
        return l;
    }


    public void msgAll(String m) {
        for (Player p : Bukkit.getOnlinePlayers()) C.c(p, m);
    }

    public void msg(String m) {
        if (isStarted()) {
            for (Player p : getPlayersInGame()) C.c(p, m);
            for (Player p : getSpectators()) C.c(p, m);
        } else if (isWaiting()) for (Player p : getPlayersWaiting()) C.c(p, m);
    }

    public void sound(Sound s, int v, int pi) {
        if (isStarted()) {
            for (Player p : getPlayersInGame()) p.playSound(p.getLocation(), s, v, pi);
            for (Player p : getSpectators()) p.playSound(p.getLocation(), s, v, pi);
        } else if (isWaiting()) for (Player p : getPlayersWaiting()) p.playSound(p.getLocation(), s, v, pi);
    }

    public void hide(Player player) {
        if (isStarted()) for (Player p : getPlayersInGame()) p.hidePlayer(player);
        else if (isWaiting()) for (Player p : getPlayersWaiting()) p.hidePlayer(player);
    }


    public void show(Player player) {
        for (Player p : Bukkit.getOnlinePlayers()) p.showPlayer(player);
    }

    public void setWaiting() {
        state = GameState.WAITING;
    }

    public void setStarted() {
        state = GameState.STARTED;
    }

    public void setIdle() {
        state = GameState.IDLE;
    }

    public boolean isStarted() {
        return state == GameState.STARTED;
    }

    public boolean isIdle() {
        return state == GameState.IDLE;
    }

    public boolean isWaiting() {
        return state == GameState.WAITING;
    }

    public boolean isInCooldown() {
        return state == GameState.IDLE && cooldownTimer > 0;
    }


    public void cancel(Player p) {
        for (Player pl : getPlayersWaiting()) leaveGame(pl);
        setIdle();
        Bukkit.broadcastMessage(C.c("&7&m--------------------------------------------------"));
        Bukkit.broadcastMessage(C.c("&c&l\u26A0 Game Cancelled"));
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(C.c("&7The game has been cancelled by " + p.getName() + "."));
        Bukkit.broadcastMessage(C.c("&7&m--------------------------------------------------"));
        for (Player pl : Bukkit.getOnlinePlayers()) pl.playSound(pl.getLocation(), Sound.ZOMBIE_UNFECT, 5, 2);
        setHost(null);
        arena = "";
    }

    public void start() {
        setStarted();
        rad = 500;
        startTimer = 30;
        pvpTimer = 0;
        gameTimer = 0;
        feastTimer = 0;
        borderTimer = 0;
        targetRad = 30;
        pvp = false;
        new Border();
        for (Player p : getPlayersInGame()) PlayerUtils.reset(p);
        msg("");
        msg("&e\u26A0 &cFrom now on, you will not be able to join the game again if you leave.");
        msg("");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isStarted() && startTimer >= 0) {
                    if (startTimer % 5 == 0 || startTimer <= 5) {
                        msg(startTimer > 0 ? C.CHAT_SECONDARY + "The game starts in " + C.MAIN + startTimer + C.CHAT_SECONDARY + "..." : "&aThe game has started.");
                        sound(startTimer > 0 ? Sound.CLICK : Sound.ZOMBIE_REMEDY, 5, 1);
                    }
                    if (startTimer == 0)
                        for (Player p : getPlayersInGame()) {
                            PlayerUtils.reset(p);
                            Location loc1 = LocationUtils.readGameSpawn().clone();
                            loc1.setPitch(p.getLocation().getPitch());
                            loc1.setYaw(p.getLocation().getYaw());
                            p.teleport(loc1);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1));
                        }
                    startTimer--;
                } else {
                    cancel();
                    if (isStarted()) startPvP();
                    if (isStarted()) startGameTimer();
                }
            }
        }.runTaskTimer(PotionSG.getInst(), 0L, 20L);
    }
    public void startPvP() {
        pvpTimer = 180;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isStarted() && pvpTimer >= 0) {
                    if (pvpTimer != 0 && pvpTimer % 60 == 0)
                        msg(C.CHAT_SECONDARY + "PvP will be enabled in " + C.MAIN + pvpTimer / 60 + (pvpTimer / 60 > 1 ? " minutes" : " minute") + C.CHAT_SECONDARY + "...");
                    else if (pvpTimer == 10 || pvpTimer <= 5)
                        msg(pvpTimer > 0 ? C.CHAT_SECONDARY + "PvP will be enabled in " + C.MAIN + pvpTimer + C.CHAT_SECONDARY + "..." : C.CHAT_SECONDARY + "PvP protection has expired!");
                    if (pvpTimer == 0) {
                        sound(Sound.FIREWORK_BLAST, 5, 1);
                        pvp = true;
                    }
                    pvpTimer--;
                } else {
                    cancel();
                    if (isStarted()) startFeast();
                }
            }
        }.runTaskTimer(PotionSG.getInst(), 0L, 20L);
    }

    public void startFeast() {
        feastTimer = 420;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isStarted() && feastTimer >= 0) {
                    if (feastTimer != 0 && feastTimer % 60 == 0)
                        msg(C.CHAT_SECONDARY + "The feast will spawn at [0, 0] in " + C.MAIN + feastTimer / 60 + (feastTimer / 60 > 1 ? " minutes" : " minute") + C.CHAT_SECONDARY + "...");
                    else if (feastTimer == 10 || feastTimer <= 5)
                        msg(feastTimer > 0 ? C.CHAT_SECONDARY + "The feast will spawn at [0, 0] in " + C.MAIN + feastTimer + C.CHAT_SECONDARY + "..." : C.CHAT_SECONDARY + "The feast has spawned at [0, 0]!");
                    if (feastTimer == 0) {
                        feast();
                        sound(Sound.FIREWORK_BLAST, 5, 1);
                    }
                    feastTimer--;
                } else {
                    cancel();
                    if (isStarted()) startBorder();
                }
            }
        }.runTaskTimer(PotionSG.getInst(), 0L, 20L);
    }

    private void feast() {
        feastRad = 10;
        Location lo = LocationUtils.readGameSpawn();
        int x1 = lo.getBlockX() + feastRad;
        int z1 = lo.getBlockZ() + feastRad;
        int x2 = lo.getBlockX() - feastRad;
        int z2 = lo.getBlockZ() - feastRad;
        int y = lo.getBlockY();
        World w = lo.getWorld();
        Location[] loc = new Location[]{
                new Location(w, x1 - 2, y, z1),
                new Location(w, x1, y, z1 - 2),
                new Location(w, x2 + 2, y, z1),
                new Location(w, x2, y, z1 - 2),
                new Location(w, x1 - 2, y, z2),
                new Location(w, x1, y, z2 + 2),
                new Location(w, x2 + 2, y, z2),
                new Location(w, x2, y, z2 + 2)};
        Location[] loc2 = new Location[]{
                new Location(w, lo.getX() - 2, y, lo.getZ()),
                new Location(w, lo.getX(), y, lo.getZ() - 2),
                new Location(w, lo.getX(), y, lo.getZ() + 2),
                new Location(w, lo.getX() + 2, y, lo.getZ())};
        w.getBlockAt(lo).setType(Material.ENCHANTMENT_TABLE);
        for (Location l : loc) w.getBlockAt(l).setType(Material.ENDER_CHEST);
        for (Location l : loc2) w.getBlockAt(l).setType(Material.BOOKSHELF);
    }
    public void startGameTimer() {
        gameTimer = 0;
        new BukkitRunnable() {
            @Override
            public void run() {
                gameTimer++;
            }
        }.runTaskTimer(this, 0L, 20L);
    }


    public void startBorder() {
        msg(C.CHAT_SECONDARY + "The border will be shrinking by " + C.MAIN + "50 blocks" + C.CHAT_SECONDARY + " every minute.");
        sound(Sound.WITHER_IDLE, 5, 1);
        borderTimer = 60;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isStarted() && rad > targetRad) {
                    if (borderTimer == 0) {
                        rad -= (rad == 50 ? 20 : 50);
                        borderTimer = 60;
                    }
                    borderTimer--;
                } else cancel();
            }
        }.runTaskTimer(PotionSG.getInst(), 0L, 20L);
    }

    public void check() {
        if (getPlayersInGame().size() == 1) {
            for (Player p : getSpectators()) stopSpectating(p, false);
            Bukkit.broadcastMessage(C.c("&7&m--------------------------------------------------"));
            Bukkit.broadcastMessage(C.c(C.MAIN + "&l\u26A0 Game Ended"));
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(C.c("&6" + getPlayersInGame().get(0).getName() + " has won the game!"));
            Bukkit.broadcastMessage(C.c("&7&m--------------------------------------------------"));
            for (Player p : getPlayersInGame()) {
                Managers.getPearlManager().removePearlCooldown(p, false);
                leaveGame(p);
                Managers.getLeaderboardManager().addWin(p.getUniqueId());
            }
            for (Player pl : Bukkit.getOnlinePlayers()) pl.playSound(pl.getLocation(), Sound.LEVEL_UP, 5, 1);
            Bukkit.broadcastMessage(C.c("&eThe world is going to be reset so... expect lag."));
            Managers.getMapManager().resetMap(LocationUtils.readGameSpawn());
            arena = "";
            setIdle();
            startCooldown();
        }
    }

    public void leaveGame(Player p) {
        Managers.getProfileManager().getProfile(p.getUniqueId()).setInLobby();
        p.teleport(LocationUtils.readLobbySpawn());
        PlayerUtils.lobbyItems(p);
    }

    public void spectate(Player p, boolean b) {
        hide(p);
        p.teleport(LocationUtils.readGameSpawn());
        PlayerUtils.spectateItems(p);
        p.setGameMode(GameMode.CREATIVE);
        Managers.getProfileManager().getProfile(p.getUniqueId()).setSpectating();
        if (b) msg(C.MAIN + p.getName() + C.CHAT_SECONDARY + " is now spectating.");
    }

    public void stopSpectating(Player p, boolean b) {
        if (b) msg(C.MAIN + p.getName() + C.CHAT_SECONDARY + " is no longer spectating.");
        p.setGameMode(GameMode.SURVIVAL);
        Managers.getProfileManager().getProfile(p.getUniqueId()).setInLobby();
        p.teleport(LocationUtils.readLobbySpawn());
        show(p);
        PlayerUtils.lobbyItems(p);
    }

    public void startCooldown() {
        cooldownTimer = 60;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (cooldownTimer == 0) {
                    hostGame(Bukkit.getConsoleSender().getName());
                    cancel();
                }
                cooldownTimer--;
            }
        }.runTaskTimer(this, 0L, 20L);
    }


    public void hostGame(String host) {
        Bukkit.broadcastMessage(C.c("&7&m--------------------------------------------------"));
        Bukkit.broadcastMessage(C.c(C.MAIN + "&l\u24D8 New PotionSG Game"));
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(C.c("&7A new game has been hosted by " + host + "."));
        Bukkit.broadcastMessage(C.c("&7&m--------------------------------------------------"));
        for (Player pl : Bukkit.getOnlinePlayers()) pl.playSound(pl.getLocation(), Sound.ZOMBIE_METAL, 5, 1);
        setHost(host);
        arena = Managers.getMapManager().getArenas().get(new Random().nextInt(Managers.getMapManager().getArenas().size()));
        setWaiting();
    }

    public Location getBorderLocation2() {
        return new Location(LocationUtils.readGameSpawn().getWorld(), LocationUtils.readGameSpawn().getX() + rad, 0, LocationUtils.readGameSpawn().getZ() + rad);
    }

    public Location getBorderLocation1() {
        return new Location(LocationUtils.readGameSpawn().getWorld(), LocationUtils.readGameSpawn().getX() - rad, 0, LocationUtils.readGameSpawn().getZ() - rad);
    }

    public void join(Player p) {
        Managers.getProfileManager().getProfile(p.getUniqueId()).setInGame();
        PlayerUtils.waitingItems(p);
        p.teleport(LocationUtils.readGameSpawn());
        p.playSound(p.getLocation(), Sound.NOTE_PLING, 5, 1);
        PotionSG.getInst().msgAll(C.MAIN + p.getName() + C.CHAT_SECONDARY + " joined the game.");
        p.setGameMode(GameMode.CREATIVE);
        Managers.getHostManager().check();
    }
}
