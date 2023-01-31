package de.turtle_exception.turtlenet.core.util.function;

@FunctionalInterface
public interface ExceptionalFunction<T, R> {
    R apply(T t) throws Exception;
}