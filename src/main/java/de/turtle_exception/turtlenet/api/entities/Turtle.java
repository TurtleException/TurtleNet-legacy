package de.turtle_exception.turtlenet.api.entities;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import org.jetbrains.annotations.NotNull;

/**
 * A turtle entity is an entity that can be uniquely identified by its turtle id.
 * @implNote All implementations of this interface must have the {@link Resource} annotation.
 * @see Turtle#getId()
 */
public interface Turtle {
    /**
     * Provides the unique turtle id of this entity. This id should never change and always only reference this entity.
     * @return Long representation of the id.
     */
    @Field(name = "id", immutable = true, unique = true)
    long getId();

    /**
     * Provides the {@link TurtleClient} instance responsible for this Turtle object. The TurtleClient handles caching
     * of resources and provides API methods to use, create or retrieve them.
     * @return Responsible TurtleClient
     */
    @NotNull TurtleClient getClient();
}
