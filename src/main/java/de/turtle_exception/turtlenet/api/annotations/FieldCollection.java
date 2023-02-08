package de.turtle_exception.turtlenet.api.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that may be added with a {@link Field} annotation to mark a resource field as a collection. This is
 * necessary because while it is possible to check at runtime whether a field is a collection, it is not possible to
 * obtain the content type of that collection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Deprecated
public @interface FieldCollection {
    /** Content-Type */
    @NotNull Class<?> type();
}
