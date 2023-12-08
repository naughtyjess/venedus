package de.pxscxl.spigot.buildffa.listener;

import de.pxscxl.origin.spigot.api.OriginPlayer;
import de.pxscxl.origin.spigot.api.manager.OriginManager;
import de.pxscxl.spigot.buildffa.BuildFFA;
import de.pxscxl.spigot.buildffa.manager.GameManager;
import de.pxscxl.spigot.buildffa.manager.RegionManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            OriginPlayer player = OriginManager.getInstance().getPlayer((Player) event.getEntity());
            if (event.getDamager() instanceof Player) {
                OriginPlayer damager = OriginManager.getInstance().getPlayer((Player) event.getDamager());
                if (RegionManager.getInstance().isInRegion(player) || RegionManager.getInstance().isInRegion(damager)) {
                    event.setCancelled(true);
                    damager.sendMessage(
                            BuildFFA.getInstance().getPrefix() + "§7Du kannst hier §cniemanden §7angreifen",
                            BuildFFA.getInstance().getPrefix() + "§7You §ccan't §7attack anyone here"
                    );
                    return;
                }
                GameManager.getInstance().getTargets().put(player, damager);
            }
            if (event.getDamager() instanceof Projectile) {
                Projectile damager = (Projectile) event.getDamager();
                if (RegionManager.getInstance().isInRegion(player)) {
                    event.setCancelled(true);
                    return;
                }
                GameManager.getInstance().getTargets().put(player, OriginManager.getInstance().getPlayer((Player) damager.getShooter()));
            }
        }
        event.setCancelled(false);
    }
}
