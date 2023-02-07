package de.turtle_exception.turtlenet.api.resource;

import com.google.gson.JsonObject;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;

public final class Entity {
    private final @NotNull Resource resource;
    private final Object[] values;

    public Entity(@NotNull Resource resource, @NotNull Object[] values) throws IllegalArgumentException {
        this.resource = resource;
        this.values = values;

        Field<?>[] fields = resource.getFields();
        if (fields.length != values.length)
            throw new IllegalArgumentException("Values are not the same length as fields!");
        for (int i = 0; i < values.length; i++) {
            if (!fields[i].isNullable() && values[i] == null)
                throw new IllegalArgumentException("Value for non-null field '" + fields[i].getKey() + "' is null.");
            if (!fields[i].getType().isInstance(values[i]))
                throw new IllegalArgumentException("Value for field '" + fields[i].getKey() + "' does not match type.");
        }
    }

    public void set(@NotNull String key, Object value) throws ClassCastException, IllegalStateException, IllegalArgumentException, NullPointerException {
        Field<?>[] fields = resource.getFields();

        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getKey().equals(key)) continue;

            if (value == null && !fields[i].isNullable())
                throw new IllegalArgumentException("Field '" + key + "' may not be null!");

            if (!fields[i].getType().isInstance(value))
                throw new ClassCastException("Value is not an instance of " + fields[i].getType());

            if (fields[i].isFinal())
                throw new IllegalStateException("Field '" + key + "' is final!");

            synchronized (fields[i]) {
                values[i] = value;
            }

            return;
        }

        throw new NullPointerException("No such field: '" + key + "'");
    }

    public Object get(@NotNull String key) throws NullPointerException {
        Field<?>[] fields = resource.getFields();
        for (int i = 0; i < fields.length; i++)
            if (fields[i].getKey().equals(key))
                return values[i];
        throw new NullPointerException("No such field: '" + key + "'");
    }

    public <T> T get(@NotNull String key, Class<T> type) throws NullPointerException, ClassCastException {
        Field<?>[] fields = resource.getFields();
        for (int i = 0; i < fields.length; i++)
            if (fields[i].getKey().equals(key))
                return type.cast(values[i]);
        throw new NullPointerException("No such field: '" + key + "'");
    }

    public Object get(int index) throws IndexOutOfBoundsException {
        if (index >= values.length)
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + values.length);
        return values[index];
    }

    public @NotNull Class<?> getType(@NotNull String key) throws NullPointerException {
        for (Field<?> field : resource.getFields())
            if (field.getKey().equals(key))
                return field.getType();
        throw new NullPointerException("No such field: '" + key + "'");
    }

    public @NotNull JsonObject toJSON() {
        JsonObject json = new JsonObject();
        Field<?>[] fields = resource.getFields();
        for (int i = 0; i < values.length; i++)
            json.add(fields[i].getKey(), fields[i].getParser().serialize(values[i]));
        // deep copy to prevent access to JsonElement fields
        return json.deepCopy();
    }
}
