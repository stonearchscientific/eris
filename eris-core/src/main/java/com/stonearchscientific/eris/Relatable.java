package com.stonearchscientific.eris;

public interface Relatable<X> {
    //public abstract Relatable all();
    //public abstract Relatable none();
    //public Relatable intersect(Relatable that);
    //public Relatable union(Relatable that);
    //public boolean lessOrEqual(Relatable that);
    public abstract boolean greaterOrEqual(X that);
}