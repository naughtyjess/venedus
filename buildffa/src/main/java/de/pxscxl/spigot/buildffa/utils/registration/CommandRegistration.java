package de.pxscxl.spigot.buildffa.utils.registration;

import de.pxscxl.origin.utils.Reflections;
import de.pxscxl.spigot.buildffa.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;

public class CommandRegistration {

    private final SimpleCommandMap simpleCommandMap = Reflections.getField(Bukkit.getServer(), "commandMap");

    public void registerAllCommands() {
        simpleCommandMap.register("buildffa", new ForcemapCommand());
        simpleCommandMap.register("buildffa", new NextMapCommand());
        simpleCommandMap.register("buildffa", new SetupCommand());
        simpleCommandMap.register("buildffa", new StatsCommand());
        simpleCommandMap.register("buildffa", new TopCommand());
    }
}
