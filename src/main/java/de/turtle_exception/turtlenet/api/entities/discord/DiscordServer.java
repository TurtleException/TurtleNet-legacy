package de.turtle_exception.turtlenet.api.entities.discord;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class DiscordServer extends Turtle {
    protected static final Resource RESOURCE = new Resource(Turtle.RESOURCE,
            new Field<>(Long.class, "snowflake", false, true, JsonSerializer.DEFAULT_LONG),
            new Field<>(String.class, "name", false, false, JsonSerializer.DEFAULT_STRING),
            new Field<>(String.class, "icon_url", false, false, JsonSerializer.DEFAULT_STRING)
    );

    public DiscordServer(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    public final long getSnowflake() {
        return this.entity.get("snowflake", Long.class);
    }

    public final @NotNull String getName() {
        return this.entity.get("name", String.class);
    }

    public final @NotNull String getIconUrl() {
        return this.entity.get("icon_url", String.class);
    }
}
