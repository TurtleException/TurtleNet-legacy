package de.turtle_exception.turtlenet.core.data;

import com.google.common.collect.Sets;
import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.exceptions.IllegalResourceException;
import de.turtle_exception.turtlenet.api.exceptions.ResourceInheritanceException;
import de.turtle_exception.turtlenet.api.resource.Resource;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceUtil {
    private static final ConcurrentHashMap<Class<? extends Turtle>, Integer> HASH_CODES = new ConcurrentHashMap<>();

    /** A set of known resource types, mapped to their {@link Resource} instances. */
    public static final ConcurrentHashMap<Class<? extends Turtle>, Resource> RESOURCE_CACHE = new ConcurrentHashMap<>();

    /** A set of all resource types that already have been validated. */
    public static final Set<Class<? extends Turtle>> VERIFIED_TYPES = Sets.newConcurrentHashSet();
    /** A set of all resource types that already have been processed and marked as illegal. */
    public static final Set<Class<? extends Turtle>> ILLEGAL_TYPES = Sets.newConcurrentHashSet();

    private ResourceUtil() { }

    public static <T extends Turtle> @NotNull Resource getResource(@NotNull Class<T> type) throws IllegalResourceException {
        if (RESOURCE_CACHE.containsKey(type))
            return RESOURCE_CACHE.get(type);
        if (ILLEGAL_TYPES.contains(type))
            throw new IllegalResourceException(type);

        String exceptionMsg;
        try {
            java.lang.reflect.Field field = type.getField("RESOURCE");
            if (!Modifier.isFinal(field.getModifiers())) {
                exceptionMsg = "RESOURCE field not final";
            } else if (Modifier.isAbstract(type.getModifiers())) {
                exceptionMsg = "Class is abstract";
            } else {
                Resource resource = (Resource) field.get(null);
                RESOURCE_CACHE.put(type, resource);
                return resource;
            }
        } catch (NoSuchFieldException e) {
            exceptionMsg = "Missing RESOURCE field";
        } catch (NullPointerException e) {
            exceptionMsg = "RESOURCE field is not static";
        } catch (ClassCastException e) {
            exceptionMsg = "RESOURCE field is not of type " + Resource.class.getName();
        } catch (Throwable t) {
            // unknown error
            ILLEGAL_TYPES.add(type);
            throw new IllegalResourceException(type, t);
        }

        throw new IllegalResourceException(type, exceptionMsg);
    }

    public static <T extends Turtle> void validateResource(@NotNull Class<T> type) throws IllegalResourceException {
        if (VERIFIED_TYPES.contains(type)) return;
        if (ILLEGAL_TYPES.contains(type))
            throw new IllegalResourceException(type);

        String exceptionMsg;
        try {
            java.lang.reflect.Field field = type.getField("RESOURCE");
            if (!Modifier.isFinal(field.getModifiers())) {
                exceptionMsg = "RESOURCE field not final";
            } else if (!Modifier.isStatic(field.getModifiers())) {
                exceptionMsg = "RESOURCE field is not static";
            } else {
                Objects.requireNonNull((Resource) field.get(null));

                Constructor<T> constructor = type.getConstructor(TurtleClient.class, Resource.class, ArrayList.class);
                if (Modifier.isPublic(constructor.getModifiers())) {
                    VERIFIED_TYPES.add(type);
                    return;
                } else {
                    exceptionMsg = "Non-public constructor";
                }
            }
        } catch (NoSuchFieldException e) {
            exceptionMsg = "Missing RESOURCE field";
        } catch (NullPointerException e) {
            exceptionMsg = "RESOURCE field may not be null";
        } catch (ClassCastException e) {
            exceptionMsg = "RESOURCE field is not of type " + Resource.class.getName();
        } catch (NoSuchMethodException e) {
            exceptionMsg = "Missing valid constructor";
        } catch (Throwable t) {
            // unknown error
            ILLEGAL_TYPES.add(type);
            throw new IllegalResourceException(type, t);
        }

        throw new IllegalResourceException(type, exceptionMsg);
    }

    /**
     * Checks whether a given {@code resource} is a valid {@link Resource} object for the provided {@code type}. If not,
     * an exception will be thrown. Otherwise, this method returns silently.
     * @param type An implementation of {@link Turtle}.
     * @param resource A given {@link Resource} that may or may not be valid for {@code type}.
     * @param <T> Type of entity.
     * @throws ResourceInheritanceException if {@code resource} does not inherit a Resource of a parent of {@code type}.
     * @throws IllegalResourceException if {@code type} is not a valid resource.
     * @see ResourceUtil#getResource(Class)
     * @see Turtle#Turtle(TurtleClient, Resource, ArrayList)
     */
    @SuppressWarnings("unchecked")
    public static <T extends Turtle> void validateResource(@NotNull Class<T> type, @NotNull Resource resource) throws IllegalResourceException {
        Integer hashCode = HASH_CODES.get(type);

        if (hashCode == null) {
            // validate resource hierarchy (necessary for custom implementations)
            Class<T> current = type;
            while (current != null) {
                Resource currentResource = getResource(current);

                if (current != type && !resource.isParent(currentResource))
                    throw new ResourceInheritanceException(type);

                // this is safe because turtle does not have parents & null can be cast to any type
                current = (Class<T>) current.getSuperclass();
            }

            hashCode = resource.getHashCode();
            HASH_CODES.put(type, hashCode);
        }

        if (hashCode != resource.getHashCode())
            throw new ResourceInheritanceException(type);
    }
}
