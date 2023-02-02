package de.turtle_exception.turtlenet.api.entities;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.FieldCollection;
import de.turtle_exception.turtlenet.api.annotations.Reference;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.attributes.MessageAuthor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * A user is a member of the community, part of the team or moderation or a bot. Users can have an unspecified amount of
 * Discord and Minecraft accounts linked to them (exclusively). A custom name can be set by the user themselves.
 * @see Group
 */
@Resource(path = "user")
@SuppressWarnings("unused")
public interface User extends Turtle, MessageAuthor {
    /**
     * Provides the name of this User. Usernames are not guaranteed to be unique and can be set by the User themselves.
     * @return The username.
     */
    @Field(name = "name")
    @NotNull String getName();

    @Field(name = "groups")
    @FieldCollection(type = Long.class)
    @Reference(to = "group")
    @NotNull Set<Long> getGroupIds();

    default @NotNull Set<Group> getGroups() {
        return this.getClient().getTurtles(Group.class, this.getGroupIds());
    }

    /**
     * Provides a List of snowflake ids that each represent a Discord user this User is linked to (exclusively).
     * @return List of snowflake ids.
     */
    @Field(name = "discord", unique = true)
    @FieldCollection(type = Long.class)
    @NotNull List<Long> getDiscordIds();

    /**
     * Provides a List of {@link UUID UUIDs} that each represent a Minecraft account this User is linked to (exclusively).
     * @return List of {@link UUID UUIDs}.
     */
    @Field(name = "minecraft", unique = true)
    @FieldCollection(type = UUID.class)
    @NotNull List<UUID> getMinecraftIds();
}
