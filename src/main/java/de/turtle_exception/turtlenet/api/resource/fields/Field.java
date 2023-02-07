package de.turtle_exception.turtlenet.api.resource.fields;

import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Field<T> {
    protected final @NotNull Class<T> type;
    protected final @NotNull String key;
    protected final boolean nullable;
    protected final boolean terminal;

    // virtually final & non-null
    protected JsonSerializer<T> parser;

    public Field(@NotNull Class<T> type, @NotNull String key, boolean nullable, boolean terminal, @NotNull JsonSerializer<T> parser) {
        this(type, key, nullable, terminal);
        this.parser = parser;
    }

    protected Field(@NotNull Class<T> type, @NotNull String key, boolean nullable, boolean terminal) {
        this.type = type;
        this.key = key;
        this.nullable = nullable;
        this.terminal = terminal;
    }

    public final @NotNull Class<T> getType() {
        return type;
    }

    public final @NotNull String getKey() {
        return key;
    }

    public final boolean isNullable() {
        return nullable;
    }

    public final boolean isFinal() {
        return terminal;
    }

    public final @NotNull JsonSerializer<T> getParser() {
        return parser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key, nullable, terminal);
    }
}
