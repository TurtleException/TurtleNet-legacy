package de.turtle_exception.turtlenet.api.resource.fields;

import com.google.gson.JsonElement;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.ResourceFieldUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class MapField<T extends Map<K, V>, K, V> extends Field<T> {
    protected final @NotNull Class<K> keyType;
    protected final @NotNull Class<V> valType;
    protected final @NotNull Supplier<T> supplier;
    protected final boolean immutable;

    protected final @NotNull JsonSerializer<K> keyParser;
    protected final @NotNull JsonSerializer<V> valParser;

    public MapField(
            @NotNull Class<T> type,
            @NotNull Supplier<T> supplier,
            @NotNull Class<K> keyType,
            @NotNull Class<V> valType,
            @NotNull String key,
            boolean terminal,
            boolean immutable,
            @NotNull JsonSerializer<K> keyParser,
            @NotNull JsonSerializer<V> valParser
    ) {
        super(type, key, false, terminal);
        this.parser = new JsonSerializer<>() {
            @Override
            public @NotNull T deserializeExplicit(@NotNull JsonElement json) {
                return ResourceFieldUtil.deserialize(MapField.this, json.getAsJsonArray());
            }

            @Override
            @SuppressWarnings("unchecked")
            public @NotNull JsonElement serialize(Object obj) {
                return ResourceFieldUtil.serialize(MapField.this, ((T) obj));
            }
        };

        this.keyType = keyType;
        this.valType = valType;
        this.supplier = supplier;
        this.immutable = immutable;
        this.keyParser = keyParser;
        this.valParser = valParser;
    }

    public final @NotNull Class<K> getKeyType() {
        return keyType;
    }

    public final @NotNull Class<V> getValType() {
        return valType;
    }

    public final boolean isImmutable() {
        return immutable;
    }

    public final @NotNull JsonSerializer<K> getKeyParser() {
        return keyParser;
    }

    public final @NotNull JsonSerializer<V> getValParser() {
        return valParser;
    }

    // M83 intensifies
    public @NotNull T newMap() {
        return this.supplier.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key, nullable, terminal, keyType, valType, immutable);
    }
}
