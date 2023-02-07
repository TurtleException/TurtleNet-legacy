package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.fancyformat.FormatText;
import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.User;
import de.turtle_exception.turtlenet.api.entities.attributes.MessageAuthor;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/** A {@link MessageEntity} that was created by a {@link User}. */
@SuppressWarnings("unused")
public class SyncMessage extends MessageEntity {
    protected static final Resource RESOURCE = new Resource(MessageEntity.RESOURCE,
            new Field<>(Long.class, "author", false, true, JsonSerializer.DEFAULT_LONG),
            // TODO: should this be stored as a string in turtle / native format?
            new Field<>(FormatText.class, "content", false, false, /* TODO */ null),
            new Field<>(Long.class, "reference", true, false, JsonSerializer.DEFAULT_LONG)
    );

    public SyncMessage(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    /**
     * Provides the {@link MessageAuthor} author of this message.
     * @return The Message author.
     */
    public final long getAuthorId() {
        return this.entity.get("author", Long.class);
    }

    public MessageAuthor getAuthor() {
        return this.getClient().getTurtleById(this.getAuthorId(), MessageAuthor.class);
    }

    /**
     * Provides the content of this message as a {@link FormatText}.
     * @return The Message content.
     */
    // FormatText#toString() will return the Turtle format
    public final @NotNull FormatText getContent() {
        return this.entity.get("content", FormatText.class);
    }

    /**
     * Provides the reference id of this message.
     * @return The Message format.
     */
    public final @Nullable Long getReferenceId() {
        return this.entity.get("reference", Long.class);
    }

    public @Nullable MessageEntity getReferenced() {
        Long id = getReferenceId();
        if (id == null) return null;
        return this.getClient().getTurtleById(id, MessageEntity.class);
    }
}
