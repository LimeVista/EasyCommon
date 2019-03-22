package me.limeice.common.function;

/**
 * copy from Java8 Consumer
 *
 * @param <T> Any
 */
public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);
}
