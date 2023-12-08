package de.pxscxl.spigot.buildffa.listener;

import de.pxscxl.spigot.buildffa.manager.GameManager;
import de.pxscxl.spigot.buildffa.manager.MapManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import de.pxscxl.origin.spigot.api.OriginPlayer;
import de.pxscxl.origin.spigot.api.manager.OriginManager;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        OriginPlayer player = OriginManager.getInstance().getPlayer(event.getPlayer());
        event.setRespawnLocation(MapManager.getInstance().getActiveMap().getSpawn());
        GameManager.getInstance().prepareSpawn(player);
    }
}
