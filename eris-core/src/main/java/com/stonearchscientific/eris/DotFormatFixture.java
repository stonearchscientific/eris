package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class DotFormatFixture<R extends Relatable<R>> extends FormatFixture<R> {
    public DotFormatFixture(final Vertex start) {
        super(start);
        sb.append("digraph {\n");
    }
    @Override
    public boolean apply(final Vertex target, final Vertex source, final Edge edge) {
        R sourceLabel = source.getProperty(Lattice.LABEL);
        R targetLabel = target.getProperty(Lattice.LABEL);
        System.out.println("sourceLabel: " + sourceLabel);
        System.out.println("targetLabel: " + targetLabel);
        if (!sourceLabel.equals(targetLabel)) {
            if (filter.test(targetLabel, sourceLabel)) {
                sb.append(" \"")
                        .append(sourceLabel)
                        .append("\" -> \"")
                        .append(targetLabel)
                        .append("\"[label=\"")
                        .append(edge.getLabel())
                        .append("\"]\n");
            }
        }
        return true;
    }
    @Override
    public boolean finish() {
        sb.append("}");
        return super.finish();
    }
}
