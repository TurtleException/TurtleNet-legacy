package de.turtle_exception.turtlenet.api.entities;

import de.turtle_exception.turtlenet.api.annotations.FieldCollection;
import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.attributes.TurtleContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * A Group is a collection of {@link User Users} with collective attributes. Groups can be used to categorize Users or
 * to simplify permission handling.
 */
@Resource(path = "groups")
@SuppressWarnings("unused")
public interface Group extends Turtle, TurtleContainer<User> {
    /**
     * Provides the name of this Group. Group names are not guaranteed to be unique and rather function as a description.
     * Uniqueness can only be checked by {@link Group#getId()}.
     * @return The Group name.
     */
    @Field(name = "name")
    @NotNull String getName();

    @Override
    default @NotNull Set<User> getTurtles() {
        return this.getUsers();
    }

    /**
     * Provides a List of all {@link User Users} that are a member of this Group.
     * <p> A Group can have multiple Users; A User can also be part of multiple Groups.
     * @return List of members.
     */
    @Field(name = "users")
    @FieldCollection(type = Long.class)
    @NotNull Set<Long> getUserIds();

    default @NotNull Set<User> getUsers() {
        return this.getClient().getTurtles(User.class, this.getUserIds());
    }
}
