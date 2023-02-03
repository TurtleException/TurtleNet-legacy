package de.turtle_exception.turtlenet.api.exceptions;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.TurtleClientBuilder;

/**
 * An exception that gets thrown by the {@link TurtleClientBuilder} if instantiating a {@link TurtleClient} fails due
 * to an internal exception.
 */
public class TurtleException extends Exception {
    public TurtleException(Throwable cause) {
        super(cause);
    }
}