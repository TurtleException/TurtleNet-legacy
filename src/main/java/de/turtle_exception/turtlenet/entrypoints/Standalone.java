package de.turtle_exception.turtlenet.entrypoints;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.TurtleClientBuilder;
import de.turtle_exception.turtlenet.api.exceptions.TurtleException;
import de.turtle_exception.turtlenet.core.TurtleClientImpl;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

/** Standalone Entrypoint. */
final class Standalone {
    /** Called by the JVM. */
    public static void main(String[] args) throws TurtleException, URISyntaxException {
        System.out.print("Starting TurtleClient");
        System.out.println(" v" + TurtleClientImpl.VERSION);

        Logger logger = Logger.getLogger("TurtleClient");

        File dataFolder = new File(TurtleClient.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();

        // save default config
        File configFile = new File(dataFolder, "config.yml");
        try (InputStream configStream = TurtleClient.class.getResourceAsStream("config.yml")) {
            if (configStream == null)
                throw new NullPointerException("Resource not found: config.yml");

            if (!configFile.exists())
                Files.copy(configStream, Path.of(configFile.toURI()));
        } catch (IOException e) {
            throw new RuntimeException("Could not save default config!", e);
        }

        YamlConfiguration   config  = YamlConfiguration.loadConfiguration(configFile);
        TurtleClientBuilder builder = new TurtleClientBuilder(config);

        builder.setLogger(logger);
        builder.setDataFolder(dataFolder);
        builder.build();

        // TODO: listen for input
    }
}
