package de.turtle_exception.turtlenet.api.entities.attributes;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.stash.Turtle;

/**
 * Represents a Resource that is ephemeral, meaning it will not be stored in the backing database or any local cache
 * (at leas not internally). It will however, fire an event so that it can be processed once.
 * <p> This might be useful for resources that are used often, but don't need to be logged - especially if they would
 * amount to great sums of unnecessarily complex data.
 */
@SuppressWarnings("unused")
public interface EphemeralType extends Turtle {
    /**
     * Returns {@code true} if this resource is ephemeral.
     * @return {@code true} if this resource is ephemeral.
     */
    @Field(name = "ephemeral")
    boolean isEphemeral();
}