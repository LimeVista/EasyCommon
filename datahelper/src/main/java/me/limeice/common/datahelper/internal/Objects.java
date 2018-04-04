package me.limeice.common.datahelper.internal;

public final class Objects {

    /**
     * Checks that the specified object reference is not {@code null}. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = Objects.requireNonNull(bar);
     * }
     * </pre></blockquote>
     *
     * @param obj the object reference to check for nullity
     * @param <T> the type of the reference
     * @return {@code data} if not {@code null}
     * @throws NullPointerException if {@code data} is {@code null}
     */
    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    public static <T> void requireNonNull(T... obj) {
        if (obj == null) throw new NullPointerException();
        for (T t : obj)
            if (t == null) throw new NullPointerException();
    }
}
