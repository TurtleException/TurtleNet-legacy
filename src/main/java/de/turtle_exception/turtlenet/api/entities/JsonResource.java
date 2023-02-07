package de.turtle_exception.turtlenet.api.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.attributes.EphemeralType;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple data structure to store a {@link JsonElement}.
 * <p> This resource is mainly intended to be used in newer client-versions, in case the server is not updated yet.
 */
@SuppressWarnings("unused")
public class JsonResource extends Turtle implements EphemeralType {
    protected static final Resource RESOURCE = new Resource(Turtle.RESOURCE,
            new Field<>(String.class, "identifier", false, true, JsonSerializer.DEFAULT_STRING),
            // TODO: should this really be terminal?
            new Field<>(JsonElement.class, "content", false, true, JsonSerializer.DEFAULT_JSON_ELEMENT),
            new Field<>(Boolean.class, "ephemeral", false, true, JsonSerializer.DEFAULT_BOOLEAN)
    );

    public JsonResource(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) {
        super(client, resource, values);
    }

    /**
     * Returns the String identifier of this JsonResource. Meaning, a hint for intended recipients as to how this
     * JsonResource should be handled / parsed.
     * <p> The identifier should be as unique as possible to avoid collisions. This also means that implementations
     * parsing this JsonResource should be prepared for possible identifier collisions by properly handling exceptions.
     * @return String identifying the nature of this JsonResource.
     */
    public final @NotNull String getIdentifier() {
        return this.entity.get("identifier", String.class);
    }

    /**
     * Provides the underlying data in form of a {@link JsonElement}. This would probably be a {@link JsonObject} or
     * {@link JsonArray} for most use-cases of this resource.
     * @return The underlying JSON data.
     */
    public final @NotNull JsonElement getContent() {
        return this.entity.get("content", JsonElement.class);
    }

    @Override
    public boolean isEphemeral() {
        return this.entity.get("ephemeral", Boolean.class);
    }
}
