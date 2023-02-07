package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.entities.attributes.TurtleContainer;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.CollectionField;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A message channel that is synchronized across multiple third-party channels. SyncChannels contain
 * {@link SyncMessage SyncMessages}, which are created by the responsible listener applications and contain any
 * information that may be necessary to process the message in another application.
 */
@SuppressWarnings("unused")
public class SyncChannel extends Turtle implements TurtleContainer<Endpoint> {
    protected static final Resource RESOURCE = new Resource(Turtle.RESOURCE,
            new Field<>(String.class, "name", false, false, JsonSerializer.DEFAULT_STRING),
            new CollectionField<>(Set.class, HashSet::new, Long.class, "endpoints", false, false, JsonSerializer.DEFAULT_LONG)
    );

    public SyncChannel(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    @Override
    public @NotNull Set<Endpoint> getTurtles() {
        return this.client.getTurtles(Endpoint.class, this.getEndpointIds());
    }

    @Override
    public @Nullable Endpoint getTurtleById(long id) {
        return this.client.getTurtleById(id, Endpoint.class);
    }

    public final @NotNull String getName() {
        return this.entity.get("name", String.class);
    }

    @SuppressWarnings("unchecked")
    public final @NotNull Set<Long> getEndpointIds() {
        return Set.copyOf(this.entity.get("endpoint", Set.class));
    }

    /* - LOGIC - */

    // TODO: send & receive
}
