package de.turtle_exception.turtlenet.api.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Deprecated
public @interface FieldMap {
    /** Content-Type of key */
    @NotNull Class<?> keyType();

    /** Content-Type of value */
    @NotNull Class<?> valueType();
}
