package com.stonearchscientific.eris;

public abstract class Filter<R extends Relatable> {
    protected boolean up;
    public Filter(final boolean up) {
        this.up = up;
    }
    public abstract boolean test(R left, R right);
}
