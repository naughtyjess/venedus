package de.pxscxl.spigot.buildffa.listener;

import de.pxscxl.origin.spigot.api.OriginPlayer;
import de.pxscxl.origin.spigot.api.manager.OriginManager;
import de.pxscxl.spigot.buildffa.manager.RegionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        OriginPlayer player = OriginManager.getInstance().getPlayer((Player) event.getWhoClicked());
        if (RegionManager.getInstance().isInRegion(player)) {
            event.setCancelled(!event.getView().getTitle().equals("§8" + player.language("Inventar", "Inventory")));
        } else {
            event.setCancelled(false);
        }
    }
}
