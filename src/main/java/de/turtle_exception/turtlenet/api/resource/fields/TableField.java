package de.turtle_exception.turtlenet.api.resource.fields;

import com.google.common.collect.Table;
import com.google.gson.JsonElement;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.ResourceFieldUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class TableField<T extends Table<R, C, V>, R, C, V> extends Field<T> {
    private final @NotNull Class<R> rowType;
    private final @NotNull Class<C> colType;
    private final @NotNull Class<V> valType;
    private final @NotNull Supplier<T> supplier;
    private final boolean immutable;

    private final @NotNull JsonSerializer<R> rowParser;
    private final @NotNull JsonSerializer<C> colParser;
    private final @NotNull JsonSerializer<V> valParser;

    protected TableField(
            @NotNull Class<T> type,
            @NotNull Supplier<T> supplier,
            @NotNull Class<R> rowType,
            @NotNull Class<C> colType,
            @NotNull Class<V> valType,
            @NotNull String key,
            boolean terminal,
            boolean immutable,
            @NotNull JsonSerializer<R> rowParser,
            @NotNull JsonSerializer<C> colParser,
            @NotNull JsonSerializer<V> valParser
    ) {
        super(type, key, false, terminal);
        this.parser = new JsonSerializer<>() {
            @Override
            public @NotNull T deserializeExplicit(@NotNull JsonElement json) {
                return ResourceFieldUtil.deserialize(TableField.this, json.getAsJsonArray());
            }

            @Override
            @SuppressWarnings("unchecked")
            public @NotNull JsonElement serialize(Object obj) {
                return ResourceFieldUtil.serialize(TableField.this, ((T) obj));
            }
        };

        this.rowType = rowType;
        this.colType = colType;
        this.valType = valType;
        this.supplier = supplier;
        this.immutable = immutable;

        this.rowParser = rowParser;
        this.colParser = colParser;
        this.valParser = valParser;
    }

    public final @NotNull Class<R> getRowType() {
        return rowType;
    }

    public final @NotNull Class<C> getColType() {
        return colType;
    }

    public final @NotNull Class<V> getValType() {
        return valType;
    }

    public final boolean isImmutable() {
        return immutable;
    }

    public final @NotNull JsonSerializer<R> getRowParser() {
        return rowParser;
    }

    public final @NotNull JsonSerializer<C> getColParser() {
        return colParser;
    }

    public final @NotNull JsonSerializer<V> getValParser() {
        return valParser;
    }

    public @NotNull T newTable() {
        return this.supplier.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key, nullable, terminal, rowType, colType, valType, immutable);
    }
}
