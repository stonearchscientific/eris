package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class DotFormatFixture<R extends Relatable> extends FormatFixture<R> {
    public DotFormatFixture() {
        super();
    }
    @Override
    public boolean apply(final Vertex target, final Vertex source, final Edge edge) {
        R sourceElement = source.getProperty(Lattice.LABEL);
        R targetElement = target.getProperty(Lattice.LABEL);
        if (!sourceElement.equals(targetElement)) {
            if (!filter.test(targetElement, sourceElement)) {
                sb.append(" \"")
                        .append(sourceElement)
                        .append("\" -> \"")
                        .append(targetElement)
                        .append("\"[label=\"")
                        .append(edge.getLabel())
                        .append("\"]\n");
            }
        }
        return true;
    }
    @Override
    public void finish() {
    }
}
