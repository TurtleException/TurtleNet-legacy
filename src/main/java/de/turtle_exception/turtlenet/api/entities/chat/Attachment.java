package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: should this be an extension of a new "File" entity?
@SuppressWarnings("unused")
@Resource(path = "attachment")
public interface Attachment extends Turtle {
    @Field(name = "snowflake", immutable = true)
    @Nullable Long getSnowflake();

    @Field(name = "url", immutable = true)
    @NotNull String getUrl();

    @Field(name = "filename", immutable = true)
    @NotNull String getFileName();

    @Field(name = "content_type", immutable = true)
    @NotNull String getContentType();

    @Field(name = "description")
    String getDescription();

    @Field(name = "size", immutable = true)
    long getSize();

    // -1 if not an image
    @Field(name = "height", immutable = true)
    int getHeight();

    // -1 if not an image
    @Field(name = "width", immutable = true)
    int getWidth();
}
