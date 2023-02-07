package de.turtle_exception.turtlenet.api.entities;

import com.google.gson.JsonObject;
import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.Entity;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.core.data.ResourceUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A turtle entity is an entity that can be uniquely identified by its turtle id.
 * @see Turtle#getId()
 */
public abstract class Turtle /* do not extend or literally everything will break */ {
    protected static final Resource RESOURCE = new Resource(
            new Field<>(Long.class, "id", false, true, JsonSerializer.DEFAULT_LONG)
    );

    protected final TurtleClient client;
    protected final Entity entity;

    /**
     * TODO
     * The values of this instance are provided via the parameter {@code values}. The list should be of a length that
     * equals the amount of fields this resource has. The values for each field are sorted in the order that they are
     * defined, starting with the {@link Turtle uppermost class} and continuing down the order of inheritance and the
     * order that the fields were defined in their respective resources.
     * <p> A hypothetical entity with the fields {@code A} and {@code B} would require a list with the value for
     * {@code id} (from {@link Turtle}), then {@code A}, then {@code B}.
     * @param client The responsible client.
     * @param resource The Resource of any implementing class.
     * @param values A list that contains values for each field in the order of fields in the underlying {@link Entity}.
     * @throws ResourceInheritanceException If the provided {@link Resource} does not inherit the Resources of all
     *                                      parent classes.
     */
    public Turtle(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        this.client = client;
        this.entity = new Entity(resource, values.toArray());

        // validates that 'resource' inherits all parents' resource of this instance
        ResourceUtil.validateResource(this.getClass(), resource);

        // TODO: register to client
    }

    /**
     * Provides the unique turtle id of this entity. This id should never change and always only reference this entity.
     * @return Long representation of the id.
     */
    public final long getId()  {
        // this is safe because Turtle has no parents -> id will always be index 0 (minimal optimization)
        return ((long) this.entity.get(0));
    }

    /**
     * Provides the {@link TurtleClient} instance responsible for this Turtle object. The TurtleClient handles caching
     * of resources and provides API methods to use, create or retrieve them.
     * @return Responsible TurtleClient
     */
    public final @NotNull TurtleClient getClient() {
        return this.client;
    }

    public @NotNull Entity getEntity() {
        return this.entity;
    }

    public final @NotNull JsonObject toJSON() {
        return this.entity.toJSON();
    }
}
