package de.turtle_exception.turtlenet.api.entities.chat.endpoints;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.chat.Endpoint;

@SuppressWarnings("unused")
@Resource(path = "endpoint_discordChannel")
public interface DiscordChannel extends Endpoint {
    @Field(name = "snowflake")
    long getSnowflake();
}
