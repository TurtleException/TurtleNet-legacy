package de.turtle_exception.turtlenet.api.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that may be added with a {@link Field} annotation to mark a resource field as a reference to another
 * resource. If the field contains an object of the type of that resource (as opposed to its id), this annotation is not
 * required and its effect is implied. If it is present nonetheless, it will be ignored.
 * <p> If this annotation is combined with a {@link Collection} annotation it is implied that each element of the
 * collection is a reference to an entity of said resource.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Reference {
    /** Path of the resource this  */
    @NotNull String to();
}
