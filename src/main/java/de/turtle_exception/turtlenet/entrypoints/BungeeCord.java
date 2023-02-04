package de.turtle_exception.turtlenet.entrypoints;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.TurtleClientBuilder;
import de.turtle_exception.turtlenet.api.exceptions.TurtleException;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

/**
 * This is the entrypoint for a BungeeCord plugin. This class will be loaded by BungeeCord, as instructed per the
 * 'bungee.yml' resource, and if loading this application as a plugin succeeds, {@link BungeeCord#onEnable()} will be
 * called.
 */
@SuppressWarnings("unused")
final class BungeeCord extends Plugin {
    /** The client instance. Stored to be shut down by {@link BungeeCord#onDisable()}. */
    private TurtleClient client;

    @Override
    public void onEnable() {
        // initialize in onEnable (as opposed to onLoad) because of possible duplicate jars

        // save default config
        File configFile = new File(getDataFolder(), "config.yml");
        try (InputStream configStream = getResourceAsStream("config.yml")) {
            if (!configFile.exists())
                Files.copy(configStream, Path.of(configFile.toURI()));
        } catch (IOException e) {
            throw new RuntimeException("Could not save default config!", e);
        }

        YamlConfiguration   config  = YamlConfiguration.loadConfiguration(configFile);
        TurtleClientBuilder builder = new TurtleClientBuilder(config);

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
