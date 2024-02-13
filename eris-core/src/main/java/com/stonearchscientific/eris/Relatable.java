package com.stonearchscientific.eris;

public interface Relatable<X> {
    public X intersect(X that);
    public X union(X that);
    public boolean lessOrEqual(X that);
    public boolean greaterOrEqual(X that);
}