package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.entities.Turtle;

@SuppressWarnings("unused")
public interface Endpoint extends Turtle {
    @Field(name = "channel", immutable = true)
    long getChannelId();

    default SyncChannel getChannel() {
        return this.getClient().getTurtleById(getChannelId(), SyncChannel.class);
    }
}
