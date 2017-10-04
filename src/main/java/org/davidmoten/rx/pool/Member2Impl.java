package org.davidmoten.rx.pool;

import io.reactivex.plugins.RxJavaPlugins;

public class Member2Impl<T> implements Member2<T> {

    private T value;
    private final NonBlockingPool2<T> pool;

    public Member2Impl(T value, NonBlockingPool2<T> pool) {
        this.value = value;
        this.pool = pool;
    }

    @Override
    public void checkin() {
        pool.checkin(this);
    }

    @Override
    public void disposeValue() {
        try {
            pool.disposer.accept(value);
            value = null;
        } catch (Throwable e) {
            // make action configurable
            RxJavaPlugins.onError(e);
            value = null;
        }
    }

    @Override
    public T value() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
