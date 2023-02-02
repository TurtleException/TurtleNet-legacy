package de.turtle_exception.turtlenet.core.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public final class JsonUtil {
    private JsonUtil() { }

    public static void addValue(@NotNull JsonObject json, @NotNull String key, Object obj) {
        if (obj == null)
            json.add(key, JsonNull.INSTANCE);
        else if (obj instanceof JsonElement objElement)
            json.add(key, objElement);
        else if (obj instanceof Boolean objBoolean)
            json.addProperty(key, objBoolean);
        else if (obj instanceof Character objCharacter)
            json.addProperty(key, objCharacter);
        else if (obj instanceof Number objNumber)
            json.addProperty(key, objNumber);
        else
            json.addProperty(key, String.valueOf(obj));
    }

    public static void addValue(@NotNull JsonArray json, Object obj) {
        if (obj == null)
            json.add(JsonNull.INSTANCE);
        else if (obj instanceof JsonElement objElement)
            json.add(objElement);
        else if (obj instanceof Boolean objBoolean)
            json.add(objBoolean);
        else if (obj instanceof Character objCharacter)
            json.add(objCharacter);
        else if (obj instanceof Number objNumber)
            json.add(objNumber);
        else
            json.add(String.valueOf(obj));
    }
}
