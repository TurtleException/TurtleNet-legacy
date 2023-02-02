package de.turtle_exception.turtlenet.api.entities.attributes;

import de.turtle_exception.turtlenet.api.entities.Turtle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Represents an object that can cache {@link Turtle Turtles}. */
@SuppressWarnings("unused")
public interface TurtleContainer<T extends Turtle> {
    /**
     * Returns an immutable List of all cached {@link Turtle} objects.
     * @return List of cached Turtles.
     */
    @NotNull Set<T> getTurtles();

    /**
     * Returns a single {@link Turtle} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the Turtle.
     * @return The requested Turtle (may be {@code null}).
     * @see Turtle#getId()
     */
    @Nullable T getTurtleById(long id);

    /**
     * Returns an immutable List of all cached {@link Turtle} objects of type {@code T}.
     * @param type The requested Turtle (may be {@code null}).
     * @return List of cached Turtles of type {@code T}.
     * @param <T1> Subclass of {@code T}.
     */
    @SuppressWarnings("unchecked")
    default <T1 extends T> @NotNull Set<T1> getTurtles(@NotNull Class<T1> type) {
        HashSet<T1> list = new HashSet<>();
        for (T turtle : this.getTurtles())
            if (type.isInstance(turtle))
                list.add((T1) turtle);
        return Set.copyOf(list);
    }

    /**
     * Returns a single {@link Turtle} of type {@code T1} specified by its id, or {@code null} if no such object is
     * stored in the underlying cache, or it is of a different type.
     * @param id The unique id of the Turtle.
     * @param type The requested Turtle (mqy be {@code null}).
     * @return The requested Turtle (may be {@code null}).
     * @param <T1> Subclass of {@code T}.
     */
    default <T1 extends T> @Nullable T1 getTurtleById(long id, @NotNull Class<T1> type) {
        T turtle = this.getTurtleById(id);
        return type.isInstance(turtle) ? type.cast(turtle) : null;
    }

    default <T1 extends T> @NotNull Set<T1> getTurtles(@NotNull Class<T1> type, @NotNull Collection<Long> ids) {
        return this.getTurtles(type, ids.stream());
    }

    default <T1 extends T> @NotNull Set<T1> getTurtles(@NotNull Class<T1> type, @NotNull Stream<Long> ids) {
        return ids
                .filter(Objects::nonNull)
                .map(l -> this.getTurtleById(l, type))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}