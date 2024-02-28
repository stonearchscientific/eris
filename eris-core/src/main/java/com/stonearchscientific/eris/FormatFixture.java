package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Vertex;

public class FormatFixture<R extends Relatable<R>> extends DefaultFixture<R> {
    protected StringBuilder sb;
    public FormatFixture(final Vertex start) {
        super(start);
        sb = new StringBuilder();
    }
    @Override
    public String toString() {
        return sb.toString();
    }
}
