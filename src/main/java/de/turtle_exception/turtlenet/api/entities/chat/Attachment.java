package de.turtle_exception.turtlenet.api.entities.chat;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

// TODO: should this be an extension of an abstract "File" entity?
@SuppressWarnings("unused")
public class Attachment extends Turtle {
    protected static final Resource RESOURCE = new Resource(Turtle.RESOURCE,
            new Field<>(Long.class, "snowflake", true, true, JsonSerializer.DEFAULT_LONG),
            new Field<>(String.class, "url", false, true, JsonSerializer.DEFAULT_STRING),
            new Field<>(String.class, "filename", false, true, JsonSerializer.DEFAULT_STRING),
            new Field<>(String.class, "content_type", false, true, JsonSerializer.DEFAULT_STRING),
            new Field<>(String.class, "description", true, false, JsonSerializer.DEFAULT_STRING),
            // TODO: could size/height/width change?
            new Field<>(Long.class, "size", false, true, JsonSerializer.DEFAULT_LONG),
            new Field<>(Integer.class, "height", false, true, JsonSerializer.DEFAULT_INTEGER),
            new Field<>(Integer.class, "width", false, true, JsonSerializer.DEFAULT_INTEGER)
    );

    public Attachment(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) throws ResourceInheritanceException {
        super(client, resource, values);
    }

    public @Nullable Long getSnowflake() {
        return this.entity.get("snowflake", Long.class);
    }

    public @NotNull String getUrl() {
        return this.entity.get("url", String.class);
    }

    public @NotNull String getFileName() {
        return this.entity.get("filename", String.class);
    }

    public @NotNull String getContentType() {
        return this.entity.get("content_type", String.class);
    }

    public String getDescription() {
        return this.entity.get("description", String.class);
    }

    public long getSize() {
        return this.entity.get("size", Long.class);
    }

    // -1 if not an image
    public int getHeight() {
        return this.entity.get("height", Integer.class);
    }

    // -1 if not an image
    public int getWidth() {
        return this.entity.get("width", Integer.class);
    }
}
