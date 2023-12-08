package de.pxscxl.spigot.buildffa.manager;

import de.pxscxl.origin.spigot.api.OriginPlayer;
import de.pxscxl.origin.spigot.api.manager.OriginManager;
import de.pxscxl.origin.spigot.api.scoreboard.ScoreboardObjective;
import de.pxscxl.spigot.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.Bukkit;

public class ScoreboardManager {

    @Getter
    private static ScoreboardManager instance;

    private int count = 1;

    public ScoreboardManager() {
        instance = this;

        Bukkit.getScheduler().runTaskTimerAsynchronously(BuildFFA.getInstance(), () -> {
            if (count != 2) {
                count++;
            } else {
                count = 1;
            }
            OriginManager.getInstance().getPlayers().forEach(this::setCountScore);
        }, 0, 500);
    }

    public void setObjective(OriginPlayer player) {
        ScoreboardObjective objective = player.getScoreboard().getObjective();
        StatsManager.Stats stats = StatsManager.getInstance().getStats(player.getUniqueId());

        objective.setScore(13, "§2§8§m---------", "§8§m---------");
        objective.setScore(12, "§7", "§8");
        objective.setScore(11, "§8┃ §7" + player.language("Aktuelle ", "Current "), "§7" + player.language("Karte", "map"));
        objective.setScore(10, "§8» ", "§b" + MapManager.getInstance().getActiveMap().getName());
        objective.setScore(9, "§5", "§6");
        objective.setScore(8, "§8┃ §7" + player.language("Deine ", "Your "), "§7" + player.language("Kills", "kills"));
        objective.setScore(7, "§8» ", "§1§b§l" + stats.getKills());
        objective.setScore(6, "§3", "§4");
        objective.setScore(5, "§8┃ §7" + player.language("Dein ", "Your "), "§7" + player.language("Elo", "elo"));
        objective.setScore(4, "§8» ", "§2§b§l" + stats.getElo());
        objective.setScore(3, "§1", "§2");
        objective.setScore(2, "§2§8§m---------", "§8§m---------");
        switch (count) {
            case 1:
                objective.setScore(1, "§8× §bts.", "§bvenedus.net");
                break;
            case 2:
                objective.setScore(1, "§8× §b@", "§bVenedusNET");
                break;
        }

        objective.setObjective();
    }

    public void setCountScore(OriginPlayer player) {
        switch (count) {
            case 1:
                player.getScoreboard().getObjective().updateScore(1, "§8× §bts.", "§bvenedus.net");
                break;
            case 2:
                player.getScoreboard().getObjective().updateScore(1, "§8× §b@", "§bVenedusNET");
                break;
        }
    }

    public void updateMapScore(OriginPlayer player) {
        player.getScoreboard().getObjective().updateScore(10, "§8» ", "§b" + MapManager.getInstance().getActiveMap().getName());
    }

    public void updateKillsScore(OriginPlayer player, StatsManager.Stats stats) {
        player.getScoreboard().getObjective().updateScore(7, "§8» ", "§1§b§l" + stats.getKills());
    }

    public void updateEloScore(OriginPlayer player, StatsManager.Stats stats) {
        player.getScoreboard().getObjective().updateScore(4, "§8» ", "§2§b§l" + stats.getElo());
    }

    public void setScoreboardTeams(OriginPlayer player) {
        OriginManager.getInstance().getPlayers().forEach(players -> {
            player.getScoreboard().registerPlayer(players);
            players.getScoreboard().registerPlayer(player);
        });
    }
}
