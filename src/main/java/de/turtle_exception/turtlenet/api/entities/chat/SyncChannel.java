package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.entities.attributes.TurtleContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A message channel that is synchronized across multiple third-party channels. SyncChannels contain
 * {@link SyncMessage SyncMessages}, which are created by the responsible listener applications and contain any
 * information that may be necessary to process the message in another application.
 */
@Resource(path = "channels")
@SuppressWarnings("unused")
public interface SyncChannel extends Turtle, TurtleContainer<Endpoint> {
    @Override
    @Nullable Endpoint getTurtleById(long id);

    @Field(name = "name")
    @NotNull String getName();

    /* - LOGIC - */

    // TODO: send & receive
}
