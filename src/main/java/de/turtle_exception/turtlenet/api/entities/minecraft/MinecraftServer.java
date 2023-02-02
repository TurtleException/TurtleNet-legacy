package de.turtle_exception.turtlenet.api.entities.minecraft;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Resource(path = "minecraft_server")
public interface MinecraftServer extends Turtle {
    @Field(name = "name")
    @NotNull String getName();

    @Field(name = "ip")
    @NotNull String getIP();
}
