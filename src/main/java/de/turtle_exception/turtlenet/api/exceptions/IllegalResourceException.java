package de.turtle_exception.turtlenet.api.exceptions;

import org.jetbrains.annotations.NotNull;

public class IllegalResourceException extends RuntimeException {
    private final @NotNull Class<?> type;

    public IllegalResourceException(@NotNull Class<?> type) {
        this(type, "Illegal resource");
    }

    public IllegalResourceException(@NotNull Class<?> type, @NotNull String reason) {
        super(reason + ": " + type.getName());
        this.type = type;
    }

    public IllegalResourceException(@NotNull Class<?> type, Throwable cause) {
        super(cause);
        this.type = type;
    }

    public IllegalResourceException(@NotNull Class<?> type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public @NotNull Class<?> getType() {
        return type;
    }
}
