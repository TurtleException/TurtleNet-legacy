package de.turtle_exception.turtlenet.api.entities.chat.endpoints;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.chat.Endpoint;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

@SuppressWarnings("unused")
public class MinecraftWorld extends Endpoint {
    protected static final Resource RESOURCE = new Resource(Endpoint.RESOURCE,
            new Field<>(UUID.class, "uuid", false, true, JsonSerializer.DEFAULT_UUID)
    );

    public MinecraftWorld(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    public @NotNull UUID getUUID() {
        return this.entity.get("uuid", UUID.class);
    }
}
