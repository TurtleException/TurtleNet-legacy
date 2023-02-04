package de.turtle_exception.turtlenet.entrypoints;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.TurtleClientBuilder;
import de.turtle_exception.turtlenet.api.exceptions.TurtleException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

/**
 * This is the entrypoint for a Spigot plugin. This class will be loaded by Spigot, as instructed per the 'plugin.yml'
 * resource, and if loading this application as a plugin succeeds, {@link Spigot#onEnable()} will be called.
 */
@SuppressWarnings("unused")
final class Spigot extends JavaPlugin {
    /** The client instance. Stored to be shut down by {@link Spigot#onDisable()}. */
    private TurtleClient client;

    @Override
    public void onEnable() {
        // initialize in onEnable (as opposed to onLoad) because of possible duplicate jars
        this.saveDefaultConfig();

        TurtleClientBuilder builder = new TurtleClientBuilder(getConfig());

        builder.setDataFolder(this.getDataFolder());
        builder.setLogger(this.getLogger());
        this.getLogger().setLevel(Level.WARNING);

        try {
            client = builder.build();
        } catch (TurtleException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        try {
            client.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
