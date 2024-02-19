package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.util.*;
public class Context<P, Q> extends AbstractContext<Concept> {
    private final List<P> objects;
    private final List<Q> attributes;
    private Matrix relation;
    public List<P> decodeObjects(BitSet bits) {
        List<P> decodedObjects = new ArrayList<>();
        for (int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i+1)) {
            decodedObjects.add(this.objects.get(i));
        }
        return decodedObjects;
    }
    /**
     * Decodes a BitSet into a list of attributes matching membership from left to right.
     *
     * @param bits the BitSet to decode
     * @return a list of attributes with membership indices represented the BitSet
     */
    public List<Q> decodeAttributes(BitSet bits) {
        List<Q> decodedAttributes = new ArrayList<>();
        for (int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i+1)) {
            decodedAttributes.add(this.attributes.get(i));
        }
        return decodedAttributes;
    }
    public String graphviz() {
        StringBuilder sb = new StringBuilder("digraph {\n");

        for (Vertex vertex : graph.getVertices()) {
            for (Edge edge : vertex.getEdges(Direction.BOTH)) {
                Vertex target = edge.getVertex(Direction.OUT);

                Concept sourceElement = vertex.getProperty("label");
                Concept targetElement = target.getProperty("label");

                //System.out.println(sourceElement + " -> " + targetElement);

                if (!sourceElement.equals(targetElement)) {
                    if (!targetElement.greaterOrEqual(sourceElement)) {
                        if (!objects.isEmpty() && !attributes.isEmpty()) {
                            sb.append(" \"")
                                    .append(decodeObjects((BitSet) sourceElement.extent()))
                                    .append(decodeAttributes((BitSet) sourceElement.intent()))
                                    .append("\" -> \"")
                                    .append(decodeObjects((BitSet) targetElement.extent()))
                                    .append(decodeAttributes((BitSet) targetElement.intent()))
                                    .append("\"[label=\"")
                                    .append(edge.getLabel())
                                    .append("\"]\n");
                        } else {
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
        }
        sb.append("}");
        return sb.toString();
    }
    public Context(List<P> objects, List<Q> attributes, Matrix relation) {
        this.objects = objects;
        this.attributes = attributes;
        BitSet all = new BitSet();
        all.set(0, attributes.size());
        lattice = new Lattice<>(graph, new Concept(new BitSet(objects.size()), all));
        this.relation = relation;
        for (int i = 0; i < this.relation.matrix.length; i++) {

            BitSet extent = new BitSet();
            extent.flip(i);
            Concept concept = new Concept(extent, this.relation.matrix[i]);
            lattice.insert(graph, concept);
        }
    }
    @Override
    public boolean contains(Object o) {
        if (o instanceof Concept) {
            Concept concept = (Concept) o;
            return lattice.find(concept).equals(concept);
        }
        return false;
    }
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
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
    @Override
    public boolean equals(Object o) {
        if (o instanceof Context<? ,?>) {
            Context<?, ?> context = (Context<?, ?>) o;
            return Arrays.equals(this.toArray(), context.toArray());
        }
        return false;
    }
}