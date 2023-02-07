package de.turtle_exception.turtlenet.api.entities;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.attributes.TurtleContainer;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.CollectionField;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A Group is a collection of {@link User Users} with collective attributes. Groups can be used to categorize Users or
 * to simplify permission handling.
 */
@SuppressWarnings("unused")
public class Group extends Turtle implements TurtleContainer<User> {
    protected static final Resource RESOURCE = new Resource(Turtle.RESOURCE,
            new Field<>(String.class, "name", false, false, JsonSerializer.DEFAULT_STRING),
            new CollectionField<>(Set.class, HashSet::new, Long.class, "users", false, false, JsonSerializer.DEFAULT_LONG)
    );

    public Group(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) {
        super(client, resource, values);
    }

    /**
     * Provides the name of this Group. Group names are not guaranteed to be unique and rather function as a description.
     * Uniqueness can only be checked by {@link Group#getId()}.
     * @return The Group name.
     */
    public final @NotNull String getName() {
        return this.entity.get("name", String.class);
    }

    @Override
    public @NotNull Set<User> getTurtles() {
        return this.getUsers();
    }

    @Override
    public @Nullable User getTurtleById(long id) {
        return this.getClient().getTurtleById(id, User.class);
    }

    /**
     * Provides a List of all {@link User Users} that are a member of this Group.
     * <p> A Group can have multiple Users; A User can also be part of multiple Groups.
     * @return List of members.
     */
    @SuppressWarnings("unchecked")
    public final @NotNull Set<Long> getUserIds() {
        return Set.copyOf(this.entity.get("users", Set.class));
    }

    public @NotNull Set<User> getUsers() {
        return this.getClient().getTurtles(User.class, this.getUserIds());
    }
}
