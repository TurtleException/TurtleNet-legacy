package de.turtle_exception.turtlenet.api.entities;

import de.turtle_exception.turtlenet.api.TurtleClient;
import de.turtle_exception.turtlenet.api.entities.attributes.MessageAuthor;
import de.turtle_exception.turtlenet.api.resource.fields.Field;
import de.turtle_exception.turtlenet.api.resource.JsonSerializer;
import de.turtle_exception.turtlenet.api.resource.Resource;
import de.turtle_exception.turtlenet.api.resource.fields.CollectionField;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A user is a member of the community, part of the team or moderation or a bot. Users can have an unspecified amount of
 * Discord and Minecraft accounts linked to them (exclusively). A custom name can be set by the user themselves.
 * @see Group
 */
@SuppressWarnings("unused")
public class User extends Turtle implements MessageAuthor {
    protected static final Resource RESOURCE = new Resource(Turtle.RESOURCE,
            new Field<>(String.class, "name", false, false, JsonSerializer.DEFAULT_STRING),
            new CollectionField<>(Set.class, HashSet::new, Long.class, "groups", false, false, JsonSerializer.DEFAULT_LONG),
            new CollectionField<>(List.class, ArrayList::new, Long.class, "discord", false, false, JsonSerializer.DEFAULT_LONG),
            new CollectionField<>(List.class, ArrayList::new, UUID.class, "minecraft", false, false, JsonSerializer.DEFAULT_UUID)
    );

    public User(@NotNull TurtleClient client, @NotNull Resource resource, @NotNull ArrayList<Object> values) {
        super(client, resource, values);
    }

    /**
     * Provides the name of this User. Usernames are not guaranteed to be unique and can be set by the User themselves.
     * @return The username.
     */
    public @NotNull String getName() {
        return this.entity.get("name", String.class);
    }

    @SuppressWarnings("unchecked")
    public @NotNull Set<Long> getGroupIds() {
        return Set.copyOf(this.entity.get("groups", Set.class));
    }

    public @NotNull Set<Group> getGroups() {
        return this.getClient().getTurtles(Group.class, this.getGroupIds());
    }

    /**
     * Provides a List of snowflake ids that each represent a Discord user this User is linked to (exclusively).
     * @return List of snowflake ids.
     */
    @SuppressWarnings("unchecked")
    public @NotNull List<Long> getDiscordIds() {
        return List.copyOf(this.entity.get("discord", List.class));
    }

    /**
     * Provides a List of {@link UUID UUIDs} that each represent a Minecraft account this User is linked to (exclusively).
     * @return List of {@link UUID UUIDs}.
     */
    @SuppressWarnings("unchecked")
    public @NotNull List<UUID> getMinecraftIds() {
        return List.copyOf(this.entity.get("minecraft", List.class));
    }
}
