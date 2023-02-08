package de.turtle_exception.turtlenet.api.annotations;

import de.turtle_exception.turtlenet.stash.Turtle;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a type as a resource. This signals the resource parser that the annotated class can be represented in a JSON
 * format. All annotated classes must implement {@link Turtle}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Deprecated
public @interface Resource {
    /** The path (name) under which this resource should be stored. */
    @NotNull String path();
}