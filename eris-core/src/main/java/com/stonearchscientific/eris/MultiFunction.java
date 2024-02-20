package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.util.BitSet;
import java.util.Collection;

public class MultiFunction<C extends Comparable, D extends Comparable> extends AbstractContext<Domain<C, D>> {

    public String graphviz() {
        StringBuilder sb = new StringBuilder("digraph {\n");

        for (Vertex vertex : graph.getVertices()) {
            for (Edge edge : vertex.getEdges(Direction.BOTH)) {
                Vertex target = edge.getVertex(Direction.OUT);

                Domain<C, D> sourceElement = vertex.getProperty("label");
                Domain<C, D> targetElement = target.getProperty("label");

                //System.out.println(sourceElement + " -> " + targetElement);

                if (!sourceElement.equals(targetElement)) {
                    if (!targetElement.greaterOrEqual(sourceElement)) {
                        sb.append(" \"")
                                .append(sourceElement)
                                .append("\" -> \"")
                                .append(targetElement)
                                .append("\"[label=\"")
                                .append(edge.getLabel())
                                .append("\"]\n");
                    }
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }
    public MultiFunction() {
        super();
        lattice = new Lattice<>(graph, Domain.none());
    }
    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }
    public void draw(final String filename) {
        String graphvizOutput = this.graphviz();

        try {
            MutableGraph g = new Parser().read(graphvizOutput);
            Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
