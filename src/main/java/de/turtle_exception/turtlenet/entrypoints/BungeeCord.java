package de.turtle_exception.turtlenet.entrypoints;

import net.md_5.bungee.api.plugin.Plugin;

final class BungeeCord extends Plugin {
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
