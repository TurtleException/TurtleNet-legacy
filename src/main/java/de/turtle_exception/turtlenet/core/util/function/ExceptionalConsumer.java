package de.turtle_exception.turtlenet.core.util.function;

@FunctionalInterface
public interface ExceptionalConsumer<T> {
    void accept(T t) throws Exception;
}