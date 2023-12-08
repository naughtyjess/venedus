package de.pxscxl.spigot.buildffa.commands;

import de.pxscxl.origin.spigot.Origin;
import de.pxscxl.origin.spigot.api.OriginPlayer;
import de.pxscxl.origin.spigot.api.manager.NickManager;
import de.pxscxl.origin.spigot.api.manager.OriginManager;
import de.pxscxl.spigot.buildffa.BuildFFA;
import de.pxscxl.spigot.buildffa.manager.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

public class StatsCommand extends Command {

    public StatsCommand() {
        super("stats");
    }

    @Override
    public boolean execute(CommandSender commandSender, String arg, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command is only executable as a player!");
            return true;
        }

        OriginPlayer player = OriginManager.getInstance().getPlayer((Player) commandSender);
        switch (args.length) {
            case 0:
                player.sendMessage(
                        BuildFFA.getInstance().getPrefix() + "§7Deine Stats werden §ageladen§8...",
                        BuildFFA.getInstance().getPrefix() + "§7Your stats will be §aloaded§8..."
                );
                Bukkit.getScheduler().runTaskLaterAsynchronously(BuildFFA.getInstance(), () -> showStats(player, StatsManager.getInstance().getStats(player.getUniqueId())), 5);
                break;
            case 1:
                String nick = NickManager.getInstance().getCurrentNicks().values().stream().filter(name -> args[0].equalsIgnoreCase(name)).findFirst().orElse(null);
                if (nick == null) {
                    OriginPlayer target = OriginManager.getInstance().getPlayer(args[0]);
                    if (target != null) {
                        if (target != player) {
                            player.sendMessage(
                                    BuildFFA.getInstance().getPrefix() + target.getDisplayName() + "'s §7Stats werden §ageladen§8...",
                                    BuildFFA.getInstance().getPrefix() + target.getDisplayName() + "'s §7stats will be §aloaded§8..."
                            );
                            Bukkit.getScheduler().runTaskLaterAsynchronously(BuildFFA.getInstance(), () -> showStats(player, StatsManager.getInstance().getStats(target.getUniqueId())), 5);
                        } else {
                            player.sendMessage(
                                    Origin.getInstance().getPrefix() + "§7Du darfst §cnicht §7mit dir selbst interagieren!",
                                    Origin.getInstance().getPrefix() + "§7You §ccan't §7interact with yourself!"
                            );
                        }
                    } else {
                        player.sendMessage(
                                Origin.getInstance().getPrefix() + "§7Dieser Spieler wurde §cnicht §7gefunden!",
                                Origin.getInstance().getPrefix() + "§7This player was §cnot §7found!"
                        );
                    }
                } else {
                    if (!StatsManager.getInstance().getFakeStats().containsKey(player.getNick())) {
                        StatsManager.getInstance().getFakeStats().put(player.getNick(), new StatsManager.Stats(UUID.randomUUID(), new Random().nextInt(5), new Random().nextInt(5), new Random().nextInt(200)));
                    }
                    player.sendMessage(
                            BuildFFA.getInstance().getPrefix() + "§7" + nick + "'s §7Stats werden §ageladen§8...",
                            BuildFFA.getInstance().getPrefix() + "§7" + nick + "'s §7stats will be §aloaded§8..."
                    );
                    Bukkit.getScheduler().runTaskLaterAsynchronously(BuildFFA.getInstance(), () -> showStats(player, StatsManager.getInstance().getFakeStats().get(nick)), 5);
                }
                break;
            default:
                player.sendMessage(
                        BuildFFA.getInstance().getPrefix() + "§7Bitte nutze: §f/stats [Name]",
                        BuildFFA.getInstance().getPrefix() + "§7Please use: §f/stats [Name]"
                );
                break;
        }
        return false;
    }

    private void showStats(OriginPlayer player, StatsManager.Stats stats) {
        player.sendMessage(BuildFFA.getInstance().getPrefix() + "§7Kills§8: §e" + stats.getKills());
        player.sendMessage(BuildFFA.getInstance().getPrefix() + "§7" + player.language("Tode", "Deaths") + "§8: §e" + stats.getDeaths());
        player.sendMessage(BuildFFA.getInstance().getPrefix() + "§7Elo§8: §e" + stats.getElo());
        player.sendMessage(BuildFFA.getInstance().getPrefix() + "§7" + player.language("Rang", "Ranking") + "§8: §e" + stats.getRank().getColor() + player.language(stats.getRank().getGerman(), stats.getRank().getEnglish()));
    }
}
