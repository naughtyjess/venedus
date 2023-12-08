package de.pxscxl.spigot.buildffa.listener;

import de.pxscxl.origin.spigot.api.OriginPlayer;
import de.pxscxl.origin.spigot.api.manager.OriginManager;
import de.pxscxl.spigot.buildffa.BuildFFA;
import de.pxscxl.spigot.buildffa.manager.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        OriginPlayer player = OriginManager.getInstance().getPlayer(event.getPlayer());
        if (RegionManager.getInstance().isInRegion(player)) {
            event.setCancelled(true);
        } else if (player.getItemInHand().getType().equals(Material.SANDSTONE) || player.getItemInHand().getType().equals(Material.WEB)) {
            event.setCancelled(false);
        } else if (player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(false);
        }

        if (event.getBlock().getType() == Material.SANDSTONE) {
            Bukkit.getScheduler().runTaskLater(BuildFFA.getInstance(), () -> event.getBlock().setType(Material.RED_SANDSTONE), 50);
            Bukkit.getScheduler().runTaskLater(BuildFFA.getInstance(), () -> event.getBlock().setType(Material.AIR), 100);
        } else if (event.getBlock().getType() == Material.WEB) {
            Bukkit.getScheduler().runTaskLater(BuildFFA.getInstance(), () -> event.getBlock().setType(Material.AIR), 80);
        }
    }
}
