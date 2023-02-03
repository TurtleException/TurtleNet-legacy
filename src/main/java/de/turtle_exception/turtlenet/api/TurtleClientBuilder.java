package de.turtle_exception.turtlenet.api;

import de.turtle_exception.turtlenet.api.exceptions.TurtleException;
import de.turtle_exception.turtlenet.core.TurtleClientImpl;
import de.turtle_exception.turtlenet.core.util.Checks;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * The TurtleClientBuilder is used to instantiate the {@link TurtleClient} interface, while avoiding an explicit
 * interaction with the {@code turtlenet.internal} package.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class TurtleClientBuilder {
    private Logger logger;

    public TurtleClientBuilder() { }

    public @NotNull TurtleClient build() throws IllegalArgumentException, TurtleException {
        try {
            Checks.nonNull(logger, "Logger");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        }

        TurtleClientImpl client;
        try {
            client = new TurtleClientImpl(logger);
        } catch (Exception e) { // TODO: replace with explicit exceptions (?)
            throw new TurtleException(e);
        }

        return client;
    }

    /* - - - */

    public TurtleClientBuilder setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public Logger getLogger() {
        return logger;
    }
}
