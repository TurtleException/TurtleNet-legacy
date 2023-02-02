package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Reference;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface MessageEntity extends Turtle {
    @Field(name = "origin")
    @Reference(to = "endpoint")
    long getOriginId();

    default Endpoint getOrigin() {
        return this.getClient().getTurtleById(getOriginId(), Endpoint.class);
    }

    @Field(name = "channel")
    @Reference(to = "channel")
    long getChannelId();

    default SyncChannel getChannel() {
        return this.getClient().getTurtleById(getChannelId(), SyncChannel.class);
    }

    // TODO: this should not be a field - is it still possible to make it universal?
    @NotNull String getAsMention();
}
