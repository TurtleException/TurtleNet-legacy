package de.turtle_exception.turtlenet.api.exceptions;

import org.jetbrains.annotations.NotNull;

public class ResourceInheritanceException extends IllegalResourceException {
    public ResourceInheritanceException(@NotNull Class<?> type) {
        super(type);
    }
}
