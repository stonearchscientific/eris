package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public abstract class Fixture<R extends Relatable>  {
    protected Filter<R> filter;
    public Fixture(final Filter<R> filter) {
        this.filter = filter;
    }
    public abstract boolean apply(Vertex target, Vertex source, Edge edge);
    public abstract void finish();
}
