package de.turtle_exception.turtlenet.core.util;

import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

public final class JsonUtil {
    private JsonUtil() { }

    public static void addValue(@NotNull JsonObject json, @NotNull String key, Object obj) {
        json.add(key, toJson(obj));
    }

    public static void addValue(@NotNull JsonArray json, Object obj) {
        json.add(toJson(obj));
    }

    public static @NotNull JsonElement toJson(Object obj) {
        if (obj == null)
            return JsonNull.INSTANCE;
        else if (obj instanceof JsonElement objElement)
            return objElement;
        else if (obj instanceof Boolean objBoolean)
            return new JsonPrimitive(objBoolean);
        else if (obj instanceof Character objCharacter)
            return new JsonPrimitive(objCharacter);
        else if (obj instanceof Number objNumber)
            return new JsonPrimitive(objNumber);
        else
            return new JsonPrimitive(String.valueOf(obj));
    }
}
