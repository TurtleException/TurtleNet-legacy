package de.turtle_exception.turtlenet.api.entities.chat.endpoints;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.chat.Endpoint;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MinecraftServer extends Endpoint {
    protected static final Resource RESOURCE = new Resource(Endpoint.RESOURCE,
            new Field<>(Long.class, "server", false, true, JsonSerializer.DEFAULT_LONG)
    );

    public MinecraftServer(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    public final long getServerId() {
        return this.entity.get("server", Long.class);
    }
}
