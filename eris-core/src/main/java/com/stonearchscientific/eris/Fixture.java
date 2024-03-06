package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.util.Collection;

public abstract class Fixture<R extends Relatable<R>>  {
    protected Collection<Vertex> visited;
    protected Collection<Vertex> collect;
    protected Filter<R> filter;
    public Fixture(final Filter<R> filter) { this.filter = filter; }
    public abstract R proceed();
    public abstract boolean apply(Vertex target, Edge edge);
    public abstract boolean finish();
}
