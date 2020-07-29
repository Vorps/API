package net.vorps.api.utils;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public abstract class LazyInitialiser<T> {

    private volatile T value;

    public T get() {
        if (value == null) {
            synchronized (this) {
                value = initialise();
            }
        }
        return value;
    }

    public abstract T initialise();

}
