package de.turtle_exception.turtlenet.api.resource.fields;

import com.google.gson.JsonElement;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.ResourceFieldUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public class CollectionField<T extends Collection<E>, E> extends Field<T> {
    protected final @NotNull Class<E> contentType;
    protected final @NotNull Supplier<T> supplier;
    protected final boolean immutable;
    protected final @NotNull JsonSerializer<E> elementParser;

    public CollectionField(
            @NotNull Class<T> type,
            @NotNull Supplier<T> supplier,
            @NotNull Class<E> contentType,
            @NotNull String key,
            boolean terminal,
            boolean immutable,
            @NotNull JsonSerializer<E> elementParser
    ) {
        super(type, key, false, terminal);
        this.parser = new JsonSerializer<>() {
            @Override
            public @NotNull T deserializeExplicit(@NotNull JsonElement json) {
                return ResourceFieldUtil.deserialize(CollectionField.this, json.getAsJsonArray());
            }

            @Override
            @SuppressWarnings("unchecked")
            public @NotNull JsonElement serialize(Object obj) {
                return ResourceFieldUtil.serialize(CollectionField.this, ((T) obj));
            }
        };

        this.supplier = supplier;
        this.contentType = contentType;
        this.immutable = immutable;
        this.elementParser = elementParser;
    }

    public final @NotNull Class<E> getContentType() {
        return contentType;
    }

    public final boolean isImmutable() {
        return immutable;
    }

    public final @NotNull JsonSerializer<E> getElementParser() {
        return elementParser;
    }

    public @NotNull T newCollection() {
        return this.supplier.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key, nullable, terminal, contentType, immutable);
    }
}
