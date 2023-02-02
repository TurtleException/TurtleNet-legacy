package de.turtle_exception.turtlenet.api.entities.minecraft;

import de.turtle_exception.turtlenet.api.annotations.Field;
import de.turtle_exception.turtlenet.api.annotations.Resource;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@SuppressWarnings("unused")
@Resource(path = "minecraft_world")
public interface MinecraftWorld extends Turtle {
    @Field(name = "uuid")
    @NotNull UUID getUUID();

    @Field(name = "name")
    @NotNull String getName();
}
