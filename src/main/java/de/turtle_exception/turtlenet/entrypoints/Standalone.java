package de.turtle_exception.turtlenet.entrypoints;

import de.turtle_exception.turtlenet.api.TurtleClientBuilder;
import de.turtle_exception.turtlenet.api.exceptions.TurtleException;

import java.util.logging.Logger;

/** Standalone Entrypoint. */
final class Standalone {
    /** Called by the JVM. */
    public static void main(String[] args) throws TurtleException {
        TurtleClientBuilder builder = new TurtleClientBuilder();

        builder.setLogger(Logger.getLogger("TurtleClient"));

        builder.build();

        // TODO: listen for input
    }
}
