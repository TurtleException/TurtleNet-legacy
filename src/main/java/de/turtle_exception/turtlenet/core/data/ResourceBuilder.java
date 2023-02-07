package de.turtle_exception.turtlenet.core.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.exceptions.IllegalResourceException;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import de.turtle_exception.turtlenet.core.util.Checks;
import de.turtle_exception.turtlenet.core.util.logging.NestedLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

public final class ResourceBuilder {
    private final @NotNull TurtleClient client;
    private final @NotNull NestedLogger logger;

    public ResourceBuilder(@NotNull TurtleClient client) {
        this.client = client;
        this.logger = new NestedLogger("ResourceBuilder", client.getLogger());
    }

    public <T extends Turtle> @NotNull T buildObject(@NotNull Class<T> type, JsonObject json) throws NullPointerException, IllegalResourceException, IllegalArgumentException {
        Checks.nonNull(json, "JSON data");

        this.logger.log(Level.FINE, "Build call (JSON > obj) for resource of type " + type.getSimpleName());
        this.logger.log(Level.FINEST, "\tJSON:  " + json);

        Resource resource = ResourceUtil.getResource(type);
        Field<?>[] fields = resource.getFields();

        ArrayList<Object> args = new ArrayList<>(fields.length);

        for (Field<?> field : fields) {
            JsonElement element = json.get(field.getKey());

            if (element == null || element.isJsonNull()) {
                if (!field.isNullable())
                    throw new IllegalArgumentException("Unexpected null for non-null field " + field.getKey());

                args.add(null);
                continue;
            }

            Object obj = field.getParser().deserialize(element);
            // casting guarantees to throw an exception now, while the resource is still being built
            args.add(type.cast(obj));
        }

        try {
            Constructor<T> constructor = type.getConstructor(TurtleClient.class, Resource.class, ArrayList.class);
            return constructor.newInstance(client, resource, args);
        } catch (NoSuchMethodException e) {
            // this should never happen because it is checked in getResource(Class<T>)
            throw new AssertionError("NoSuchMethodException for constructor of already verified resource " + type.getName());
        } catch (IllegalArgumentException e) {
            // this should never happen because it is checked in getResource(Class<T>)
            throw new AssertionError("Unexpected IllegalArgumentException on constructor call for already verified resource " + type.getName() + ", indicating a parameter mismatch.");
        } catch (InstantiationException e) {
            // this should never happen because it is checked in getResource(Class<T>)
            throw new AssertionError("Unexpected InstantiationException on constructor call for already verified resource " + type.getName() + ", indicating that is is abstract.");
        } catch (IllegalAccessException | InvocationTargetException | ExceptionInInitializerError e) {
            throw new IllegalResourceException(type, "Unexpected exception caused by constructor call for already verified resource " + type.getName(), e);
        } catch (Throwable t) {
            throw new IllegalResourceException(type, t);
        }
    }
}
