package de.turtle_exception.turtlenet.api.resource;

import com.google.gson.*;
import de.turtle_exception.turtlenet.core.util.JsonUtil;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@SuppressWarnings("unused")
@FunctionalInterface
public interface JsonSerializer<T> {
    JsonSerializer<String> DEFAULT_STRING = JsonElement::getAsString;
    JsonSerializer<Number> DEFAULT_NUMBER = JsonElement::getAsNumber;

    JsonSerializer<Boolean> DEFAULT_BOOLEAN = JsonElement::getAsBoolean;
    JsonSerializer<Byte>    DEFAULT_BYTE    = JsonElement::getAsByte;
    JsonSerializer<Double>  DEFAULT_DOUBLE  = JsonElement::getAsDouble;
    JsonSerializer<Float>   DEFAULT_FLOAT   = JsonElement::getAsFloat;
    JsonSerializer<Integer> DEFAULT_INTEGER = JsonElement::getAsInt;
    JsonSerializer<Long>    DEFAULT_LONG    = JsonElement::getAsLong;
    JsonSerializer<Short>   DEFAULT_SHORT   = JsonElement::getAsShort;

    JsonSerializer<JsonElement>   DEFAULT_JSON_ELEMENT   = json -> json;
    JsonSerializer<JsonObject>    DEFAULT_JSON_OBJECT    = JsonElement::getAsJsonObject;
    JsonSerializer<JsonArray>     DEFAULT_JSON_ARRAY     = JsonElement::getAsJsonArray;
    JsonSerializer<JsonPrimitive> DEFAULT_JSON_PRIMITIVE = JsonElement::getAsJsonPrimitive;

    JsonSerializer<UUID> DEFAULT_UUID = json -> UUID.fromString(json.getAsString());

    default @NotNull T deserialize(@NotNull JsonElement json) throws JsonSyntaxException {
        try {
            return this.deserializeExplicit(json);
        } catch (Exception e) {
            throw new JsonSyntaxException("Could not deserialize JSON", e);
        }
    }

    @NotNull T deserializeExplicit(@NotNull JsonElement json) throws Exception;

    default @NotNull JsonElement serialize(Object obj) {
        return JsonUtil.toJson(obj);
    }
}
