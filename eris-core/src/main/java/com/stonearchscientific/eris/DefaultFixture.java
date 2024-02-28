package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.util.ArrayList;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.stonearchscientific.eris.Lattice.LABEL;

public class DefaultFixture<R extends Relatable<R>> extends Fixture<R> {
    public DefaultFixture(final Vertex start) {
        this(start, new DefaultFilter<>(true));
    }
    public DefaultFixture(final Vertex start, final Filter<R> filter) {
        super(filter);
        visited = new HashSet<>();
        visited.add(start);
        collect = new ArrayList<>();
        collect.add(start);
    }
    @Override
    public R proceed() {
        Vertex visiting = ((ArrayList<Vertex>) collect).remove(0);
        R visitingConcept = visiting.getProperty(LABEL);
        for (Edge edge : visiting.getEdges(Direction.BOTH)) {
            Vertex target = edge.getVertex(Direction.OUT);
            if (this.apply(target, visiting, edge) && !visited.contains(target)) {
                visited.add(target);
                collect.add(target);
            }
        }
        return visitingConcept;
    }
    @Override
    public boolean apply(final Vertex target, final Vertex source, final Edge edge) {
        checkNotNull(target);
        checkNotNull(source);
        return filter.test(target.getProperty(LABEL), source.getProperty(LABEL));
    }
    @Override
    public boolean finish() {
        return collect.isEmpty();
    }
}
