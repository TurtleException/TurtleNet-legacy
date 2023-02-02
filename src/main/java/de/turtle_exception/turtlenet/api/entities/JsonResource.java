package de.turtle_exception.turtlenet.api.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.attributes.EphemeralType;
import org.jetbrains.annotations.NotNull;

/**
 * A simple data structure to store a {@link JsonElement}.
 * <p> This resource is mainly intended to be used in newer client-versions, in case the server is not updated yet.
 */
@Resource(path = "json_resources")
@SuppressWarnings("unused")
public interface JsonResource extends Turtle, EphemeralType {
    /**
     * Returns the String identifier of this JsonResource. Meaning, a hint for intended recipients as to how this
     * JsonResource should be handled / parsed.
     * <p> The identifier should be as unique as possible to avoid collisions. This also means that implementations
     * parsing this JsonResource should be prepared for possible identifier collisions by properly handling exceptions.
     * @return String identifying the nature of this JsonResource.
     */
    @Field(name = "identifier")
    @NotNull String getIdentifier();

    /**
     * Provides the underlying data in form of a {@link JsonElement}. This would probably be a {@link JsonObject} or
     * {@link JsonArray} for most use-cases of this resource.
     * @return The underlying JSON data.
     */
    @Field(name = "content")
    @NotNull JsonElement getContent();

    @Override
    long getId();
}
