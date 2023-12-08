package de.pxscxl.spigot.buildffa;

import de.pxscxl.origin.spigot.api.OriginPlayer;
import de.pxscxl.origin.spigot.api.manager.OriginManager;
import de.pxscxl.spigot.buildffa.manager.*;
import de.pxscxl.spigot.buildffa.utils.registration.CommandRegistration;
import de.pxscxl.spigot.buildffa.utils.registration.EventRegistration;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class BuildFFA extends JavaPlugin {

    @Getter
    private static BuildFFA instance;

    @Getter
    private final String prefix = "§8┃ §bBuildFFA §8× §r";

    private CommandRegistration commandRegistry;
    private EventRegistration eventRegistry;

    @Getter
    private final ArrayList<OriginPlayer> players = new ArrayList<>();

    @Override
    public void onLoad() {
        instance = this;

        commandRegistry = new CommandRegistration();
        eventRegistry = new EventRegistration();
    }

    @Override
    public void onEnable() {
        commandRegistry.registerAllCommands();
        eventRegistry.registerAllEvents();

        new GameManager();
        new KitManager();
        new ScoreboardManager();
        new MapManager();
        new RegionManager();
        new StatsManager();

        Bukkit.getScheduler().runTaskTimerAsynchronously(BuildFFA.getInstance(), () -> OriginManager.getInstance().getPlayers().forEach(players -> players.sendActionbar(
                GameManager.getInstance().isTeaming() ? "§aTeaming erlaubt" : "§cTeaming verboten",
                GameManager.getInstance().isTeaming() ? "§aTeaming allowed" : "§cTeaming forbidden")), 0, 20);
    }
}
