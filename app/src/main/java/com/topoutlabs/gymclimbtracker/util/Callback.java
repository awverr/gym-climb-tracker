package com.topoutlabs.gymclimbtracker.util;

/**
 * Just a generic callback
 */
public interface Callback<T> {
    void receive(T t);
}
