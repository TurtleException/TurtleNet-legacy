package de.turtle_exception.turtlenet.api.entities.chat.endpoints;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.chat.Endpoint;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@SuppressWarnings("unused")
@Resource(path = "endpoint_minecraftWorld")
public interface MinecraftWorld extends Endpoint {
    @Field(name = "uuid")
    @NotNull UUID getUUID();
}
