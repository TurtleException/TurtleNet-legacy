package de.turtle_exception.turtlenet.api.entities.minecraft;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MinecraftServer extends Turtle {
    protected static final Resource RESOURCE = new Resource(Turtle.RESOURCE,
            new Field<>(String.class, "name", false, false, JsonSerializer.DEFAULT_STRING),
            new Field<>(String.class, "ip", false, false, JsonSerializer.DEFAULT_STRING)
    );

    public MinecraftServer(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    public final @NotNull String getName() {
        return this.entity.get("name", String.class);
    }

    public final @NotNull String getIP() {
        return this.entity.get("ip", String.class);
    }
}
