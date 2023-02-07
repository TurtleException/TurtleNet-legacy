package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@SuppressWarnings("unused")
public abstract class MessageEntity extends Turtle {
    protected static final Resource RESOURCE = new Resource(Turtle.RESOURCE,
            new Field<>(Long.class, "origin", false, true, JsonSerializer.DEFAULT_LONG),
            new Field<>(Long.class, "channel", false, true, JsonSerializer.DEFAULT_LONG)
    );

    protected MessageEntity(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    public final long getOriginId() {
        return this.entity.get("origin", Long.class);
    }

    public Endpoint getOrigin() {
        return this.getClient().getTurtleById(getOriginId(), Endpoint.class);
    }

    public final long getChannelId() {
        return this.entity.get("channel", Long.class);
    }

    public SyncChannel getChannel() {
        return this.getClient().getTurtleById(getChannelId(), SyncChannel.class);
    }

    // TODO: this should not be a field - is it still possible to make it universal?
    // note: why not?
    public @NotNull String getAsMention() {
        // TODO
        return "";
    }
}
