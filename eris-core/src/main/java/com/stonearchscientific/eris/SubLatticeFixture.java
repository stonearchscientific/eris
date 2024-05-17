package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class SubLatticeFixture<R extends Relatable<R>> extends FormatFixture<R> {
    private Vertex source;
    private Graph graph;
    private Lattice<R> lattice;
    public SubLatticeFixture(final Graph graph, final Vertex start, int size) {
        super(start, size);
        //this.graph = graph;
        //lattice = new Lattice<>(this.graph, start.getProperty(Lattice.LABEL));
        //source = lattice.bottom();
    }
    public Lattice<R> getLattice() {
        return lattice;
    }
//    @Override
//    public boolean apply(final Vertex target, final Edge edge) {
//        R sourceLabel = visiting.getProperty(Lattice.LABEL);
//        R targetLabel = target.getProperty(Lattice.LABEL);
//        if (!sourceLabel.equals(targetLabel)) {
//            if (filter.test(targetLabel, sourceLabel)) {
//                //Vertex targetCopy = lattice.addVertex(graph, targetLabel);
//                //System.out.println("addVertex(" + targetLabel + ")");
//                //lattice.addUndirectedEdge(graph, source, targetCopy, edge.getLabel());
//                System.out.println("addUndirectedEdge(" + sourceLabel + ", " + targetLabel + ")");
//                System.out.println("addUndirectedEdge(" + visiting.getProperty("ID") + ", " + target.getProperty("ID") + ")");
//            }
//        }
//        return super.apply(target, edge);
//    }
}
