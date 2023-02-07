package de.turtle_exception.turtlenet.api.entities.minecraft;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

@SuppressWarnings("unused")
public class MinecraftWorld extends Turtle {
    protected static final Resource RESOURCE = new Resource(Turtle.RESOURCE,
            new Field<>(UUID.class, "uuid", false, true, JsonSerializer.DEFAULT_UUID),
            new Field<>(String.class, "name", false, false, JsonSerializer.DEFAULT_STRING)
    );

    public MinecraftWorld(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    public final @NotNull UUID getUUID() {
        return this.entity.get("uuid", UUID.class);
    }

    public final @NotNull String getName() {
        return this.entity.get("name", String.class);
    }
}
