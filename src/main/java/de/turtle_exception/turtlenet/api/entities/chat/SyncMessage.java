package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.fancyformat.FormatText;
import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.User;
import de.turtle_exception.turtlenet.api.entities.attributes.MessageAuthor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A {@link MessageEntity} that was created by a {@link User}. */
@Resource(path = "message")
@SuppressWarnings("unused")
public interface SyncMessage extends MessageEntity {
    /**
     * Provides the {@link MessageAuthor} author of this message.
     * @return The Message author.
     */
    @Field(name = "author", immutable = true)
    long getAuthorId();

    default MessageAuthor getAuthor() {
        return this.getClient().getTurtleById(this.getAuthorId(), MessageAuthor.class);
    }

    /**
     * Provides the content of this message as a {@link FormatText}.
     * @return The Message content.
     */
    // FormatText#toString() will return the Turtle format
    @Field(name = "content")
    @NotNull FormatText getContent();

    /**
     * Provides the reference id of this message.
     * @return The Message format.
     */
    @Field(name = "reference")
    @Nullable Long getReferenceId();

    default @Nullable MessageEntity getReferenced() {
        Long id = getReferenceId();
        if (id == null) return null;
        return this.getClient().getTurtleById(id, MessageEntity.class);
    }
}
