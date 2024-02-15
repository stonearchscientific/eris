package com.stonearchscientific.eris;

public interface Relatable<R> {
    public R intersect(R that);
    public R union(R that);
    public boolean lessOrEqual(R that);
    public boolean greaterOrEqual(R that);
}