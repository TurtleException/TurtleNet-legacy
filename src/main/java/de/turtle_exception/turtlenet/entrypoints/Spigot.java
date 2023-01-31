package de.turtle_exception.turtlenet.entrypoints;

import org.bukkit.plugin.java.JavaPlugin;

final class Spigot extends JavaPlugin {
    @Override
    public void onEnable() {
        // initialize in onEnable (as opposed to onLoad) because of possible duplicate jars
        // TODO: initialize
    }

    @Override
    public void onDisable() {
        // TODO: safely shut down
    }
}
