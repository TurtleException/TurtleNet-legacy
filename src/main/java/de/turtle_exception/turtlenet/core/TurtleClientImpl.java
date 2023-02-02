package de.turtle_exception.turtlenet.core;

import de.turtle_exception.fancyformat.FancyFormatter;
import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.TurtleClientBuilder;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.core.util.TurtleSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The standard implementation of {@link TurtleClient}. For now, it is recommended not to create a custom implementation
 * as the application is still in development. It may be impossible to create custom implementations once the first
 * release is out.
 */
public class TurtleClientImpl implements TurtleClient {
    /** The current version of this application. */
    public  static final String VERSION;
    /** The current version of this application as an integer array, starting with the most major version. */
    private static final int[] VERSIONS;
    // this will only throw an exception if packaging went wrong
    static {
        try {
            Properties properties = new Properties();
            properties.load(TurtleClient.class.getClassLoader().getResourceAsStream("version.properties"));
            VERSION = properties.getProperty("version");
        } catch (IOException e) {
            System.out.println("Unable to obtain version from resources.");
            throw new RuntimeException(e);
        }

        String[] split = VERSION.split("[.\\-_]");
        VERSIONS = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("alpha")) {
                VERSIONS[i] = -2;
                continue;
            }
            if (split[i].equals("beta")) {
                VERSIONS[i] = -1;
                continue;
            }

            // will throw NFE for illegal token
            VERSIONS[i] = Integer.parseInt(split[i]);
        }
    }

    /** The root logger of this client. */
    private final Logger logger;

    /** The FancyFormatter instance that will be used from all parts of the API. */
    private final FancyFormatter formatter;

    /** Cache for all non-ephemeral resources. */
    private final TurtleSet<Turtle> cache = new TurtleSet<>();

    /** It is not recommended to use this constructor directly. Please refer to {@link TurtleClientBuilder} if possible. */
    public TurtleClientImpl(Logger logger) {
        this.logger = logger;

        this.logger.log(Level.INFO, "Hello there");

        this.formatter = new FancyFormatter();

        this.logger.log(Level.INFO, "General Kenobi 0_0 (Startup done)");
    }

    @Override
    public @NotNull Logger getLogger() {
        return this.logger;
    }

    @Override
    public @NotNull String getVersion() {
        return VERSION;
    }

    @Override
    public int[] getVersions() {
        return Arrays.copyOf(VERSIONS, VERSIONS.length);
    }

    @Override
    public @NotNull FancyFormatter getFormatter() {
        return this.formatter;
    }

    @Override
    public @NotNull Set<Turtle> getTurtles() {
        return Set.copyOf(this.cache);
    }

    @Override
    public @Nullable Turtle getTurtleById(long id) {
        return this.cache.get(id);
    }

    @Override
    public void shutdown() throws IOException {
        this.logger.log(Level.INFO, "Shutting down...");
        // TODO
        this.logger.log(Level.INFO, "OK bye.");
    }

    @Override
    public void shutdownNow() throws IOException {
        this.logger.log(Level.WARNING, "Forcing shutdown...");
        // TODO
        this.logger.log(Level.INFO, "OK bye :c");
    }
}
