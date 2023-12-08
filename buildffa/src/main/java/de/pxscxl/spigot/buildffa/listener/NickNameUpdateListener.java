package de.pxscxl.spigot.buildffa.listener;

import de.pxscxl.origin.spigot.api.events.NickNameUpdateEvent;
import de.pxscxl.spigot.buildffa.manager.ScoreboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NickNameUpdateListener implements Listener {

    @EventHandler
    public void onNickNameUpdate(NickNameUpdateEvent event) {
        ScoreboardManager.getInstance().setScoreboardTeams(event.getPlayer());
    }
}
