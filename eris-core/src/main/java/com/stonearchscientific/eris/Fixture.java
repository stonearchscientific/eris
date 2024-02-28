package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class Fixture<R extends Relatable<R>>  {
    protected Collection<Vertex> visited;
    protected Collection<Vertex> collect;
    protected Filter<R> filter;
    public Fixture(final Filter<R> filter) { this.filter = filter; }
    public abstract R proceed();
    public abstract boolean apply(Vertex target, Vertex source, Edge edge);
    public abstract boolean finish();
}
