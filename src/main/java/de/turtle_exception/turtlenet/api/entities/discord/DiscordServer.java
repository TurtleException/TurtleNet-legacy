package de.turtle_exception.turtlenet.api.entities.discord;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Resource(path = "discord_server")
public interface DiscordServer extends Turtle {
    @Field(name = "snowflake", immutable = true, unique = true)
    long getSnowflake();

    @Field(name = "name")
    @NotNull String getName();

    @Field(name = "icon_url")
    @NotNull String getIconUrl();
}
