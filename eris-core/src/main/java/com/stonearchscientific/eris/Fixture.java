package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Vertex;

public abstract class Fixture<R extends Relatable>  {
    protected Filter<R> filter;
    public Fixture(final Filter<R> filter) {
        this.filter = filter;
    }
    public abstract boolean apply(Vertex source, Vertex target);
}
