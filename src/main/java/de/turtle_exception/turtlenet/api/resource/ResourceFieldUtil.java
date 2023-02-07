package de.turtle_exception.turtlenet.api.resource;

import com.google.common.collect.Table;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.turtle_exception.turtlenet.api.resource.fields.CollectionField;
import de.turtle_exception.turtlenet.api.resource.fields.MapField;
import de.turtle_exception.turtlenet.api.resource.fields.TableField;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public class ResourceFieldUtil {
    private ResourceFieldUtil() { }

    public static <T extends Collection<E>, E> @NotNull JsonArray serialize(@NotNull CollectionField<T, E> field, @NotNull T obj) {
        JsonArray arr = new JsonArray();
        for (E element : obj)
            arr.add(field.getElementParser().serialize(element));
        return arr.deepCopy();
    }

    public static <T extends Collection<E>, E> @NotNull T deserialize(@NotNull CollectionField<T, E> field, @NotNull JsonArray json) {
        T collection = field.newCollection();
        for (JsonElement element : json)
            collection.add(field.getElementParser().deserialize(element));
        return collection;
    }

    public static <T extends Map<K, V>, K, V> @NotNull JsonArray serialize(@NotNull MapField<T, K, V> field, @NotNull T obj) {
        JsonArray arr = new JsonArray();
        for (Map.Entry<K, V> entry : obj.entrySet()) {
            JsonObject jsonObj = new JsonObject();
            jsonObj.add("key", field.getKeyParser().serialize(entry.getKey()));
            jsonObj.add("val", field.getValParser().serialize(entry.getValue()));
            arr.add(jsonObj);
        }
        return arr.deepCopy();
    }

    public static <T extends Map<K, V>, K, V> @NotNull T deserialize(@NotNull MapField<T, K, V> field, @NotNull JsonArray json) {
        T map = field.newMap();
        for (JsonElement element : json) {
            JsonObject obj = element.getAsJsonObject();
            K key = field.getKeyParser().deserialize(obj.get("key"));
            V val = field.getValParser().deserialize(obj.get("val"));
            map.put(key, val);
        }
        return map;
    }

    public static <T extends Table<R, C, V>, R, C, V> @NotNull JsonArray serialize(@NotNull TableField<T, R, C, V> field, @NotNull T obj) {
        JsonArray arr = new JsonArray();
        for (Map.Entry<R, Map<C, V>> row : obj.rowMap().entrySet()) {
            JsonObject rowObj = new JsonObject();
            rowObj.add("row", field.getRowParser().serialize(row.getKey()));
            JsonArray entries = new JsonArray();
            for (Map.Entry<C, V> entry : row.getValue().entrySet()) {
                JsonObject entryObj = new JsonObject();
                entryObj.add("col", field.getColParser().serialize(entry.getKey()));
                entryObj.add("val", field.getValParser().serialize(entry.getValue()));
                entries.add(entryObj);
            }
            arr.add(rowObj);
        }
        return arr.deepCopy();
    }

    public static <T extends Table<R, C, V>, R, C, V> @NotNull T deserialize(@NotNull TableField<T, R, C, V> field, @NotNull JsonArray json) {
        T table = field.newTable();
        for (JsonElement element : json) {
            JsonObject rowObj = element.getAsJsonObject();
            R row = field.getRowParser().deserialize(rowObj.get("row"));
            JsonArray entries = rowObj.getAsJsonArray("entries");
            for (JsonElement entry : entries) {
                JsonObject entryObj = entry.getAsJsonObject();
                C col = field.getColParser().deserialize(entryObj.get("col"));
                V val = field.getValParser().deserialize(entryObj.get("val"));
                table.put(row, col, val);
            }
        }
        return table;
    }
}
