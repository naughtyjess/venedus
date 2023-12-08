package de.pxscxl.spigot.buildffa.utils.registration;

import de.pxscxl.spigot.buildffa.BuildFFA;
import de.pxscxl.spigot.buildffa.listener.*;
import org.bukkit.Bukkit;

public class EventRegistration {

    public void registerAllEvents() {
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new BlockPhysicsListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityChangeBlockListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new NickNameUpdateListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerBedEnterListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new ProjectileHitListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new ServerListPingListener(), BuildFFA.getInstance());
        Bukkit.getPluginManager().registerEvents(new WeatherChangeListener(), BuildFFA.getInstance());
    }
}
