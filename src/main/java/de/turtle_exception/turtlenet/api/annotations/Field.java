package de.turtle_exception.turtlenet.api.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Deprecated
public @interface Field {
    /** Name of the field. */
    @NotNull String name();

    // nullability is implied by @NotNull or @Nullable annotations of target

    /** Whether this field is final, meaning the assigned value may not be modified. */
    boolean immutable() default false;

    /**
     * Whether this field is unique, meaning each value may only exist for one entity of this resource.
     * <p> If the field is a collection it is implied that each entry is unique (across all entities of that resource).
     * */
    boolean unique() default false;
}
