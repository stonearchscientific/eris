package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Vertex;

import static com.google.common.base.Preconditions.checkNotNull;

public class DefaultFixture<R extends Relatable> extends Fixture<R> {
    public DefaultFixture() {
        super(new DefaultFilter<>(true));
    }
    public DefaultFixture(final Filter<R> filter) {
        super(filter);
    }
    @Override
    public boolean apply(final Vertex source, final Vertex target) {
        checkNotNull(source);
        checkNotNull(target);
        return filter.test((R) source.getProperty(Lattice.LABEL), (R) target.getProperty(Lattice.LABEL));
    }
}
