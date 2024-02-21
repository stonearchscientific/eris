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
    public boolean apply(final Vertex target, final Vertex source) {
        checkNotNull(target);
        checkNotNull(source);
        return filter.test((R) target.getProperty(Lattice.LABEL), (R) source.getProperty(Lattice.LABEL));
    }
}
