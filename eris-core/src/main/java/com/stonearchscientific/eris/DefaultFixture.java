package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.stonearchscientific.eris.Lattice.LABEL;

public class DefaultFixture<R extends Relatable<R>> extends Fixture<R> {
    private final boolean dfs;
    protected Vertex visiting;
    public DefaultFixture(final Vertex start) {
        this(start, new DefaultFilter<>(true), false);
    }
    public DefaultFixture(final Vertex start, final Filter<R> filter) {
        this(start, filter, false);
    }
    public DefaultFixture(final Vertex start, final Filter<R> filter, final boolean depthFirst) {
        super(filter);
        this.dfs = depthFirst;
        visited = new HashSet<>();
        visited.add(start);
        if(this.dfs) {
            collect = new ArrayDeque<>();
            ((ArrayDeque<Vertex>) collect).push(start);
        } else {
            collect = new ArrayList<>();
            collect.add(start);
        }
    }
    @Override
    public R proceed() {
        if(dfs) {
            visiting = ((ArrayDeque<Vertex>) collect).pop();
        } else {
            visiting = ((ArrayList<Vertex>) collect).remove(0);
        }
        for (Edge edge : visiting.getEdges(Direction.BOTH)) {
            Vertex target = edge.getVertex(Direction.OUT);
            if (this.apply(target, edge) && !visited.contains(target)) {
                visited.add(target);
                if(dfs) {
                    ((ArrayDeque<Vertex>) collect).push(target);
                } else {
                    collect.add(target);
                }
            }
        }
        return visiting.getProperty(LABEL);
    }
    @Override
    public boolean apply(final Vertex target, final Edge edge) {
        checkNotNull(target);
        return filter.test(target.getProperty(LABEL), visiting.getProperty(LABEL));
    }
    @Override
    public boolean finish() {
        return !collect.isEmpty();
    }
}
