package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.BitSet;
import java.io.File;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
public class Context<P, Q> /*implements Collection<Concept<?, ?>>*/ {
    private boolean up;
    private Graph graph;
    private List<P> objects;
    private List<Q> attributes;
    private Type objectType;
    private Type attributeType;
    private Matrix relation;
    private Lattice<Concept> lattice;

    public static enum Type {
        DATE,
        INTEGER,
        DOUBLE,
        NONCOMPARABLE
    };
    public List<P> decodeObjects(BitSet bits) {
        List<P> decodedObjects = new ArrayList<>();
        for (int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i+1)) {
            decodedObjects.add(this.objects.get(i));
        }
        return decodedObjects;
    }
    /**
     * Decodes a BitSet into a list of attributes matchign membership from left to right.
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
        up = true;
        graph = new TinkerGraph();
        this.objects = objects;
        this.attributes = attributes;
        BitSet all = new BitSet();
        all.set(0, objects.size());
        lattice = new Lattice<Concept>(graph, new Concept(new BitSet(attributes.size()), all));
        this.relation = relation;
        for (int i = 0; i < this.relation.matrix.length; i++) {
            BitSet extent = new BitSet();
            extent.flip(i);
            Concept concept = new Concept(extent, this.relation.matrix[i]);
            lattice.insert(graph, concept);
        }
    }
    public void dual() { up = up ? false : true; }
    public static class Iterator<P, Q> implements java.util.Iterator<Concept> {
        private Lattice.Iterator<Concept> iterator;
        public Iterator(final Lattice.Iterator<Concept> iterator) {
            this.iterator = iterator;
        }
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
        @Override
        public Concept next() {
            return iterator.next();
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }
    public Iterator<P, Q> iterator() {
        Lattice.Iterator<Concept> iterator;
        if(up) {
            iterator = lattice.iterator();
        } else {
            iterator = lattice.dual().iterator();
        }
        return new Iterator(iterator);
    }
    public static class Builder<P, Q> {
        private List<P> objects;
        private List<Q> attributes;
        private Type objectType;
        private Type attributeType;
        private Matrix relation;

        public Builder<P, Q> withObjects(Type type) {
            this.objectType = type;
            return this;
        }

        public Builder<P, Q> withAttributes(Type type) {
            this.attributeType = type;
            return this;
        }

        public Builder<P, Q> withObjects(List<P> objects) {
            this.objects = objects;
            return this;
        }

        public Builder<P, Q> withAttributes(List<Q> attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder<P, Q> withRelation(final Matrix relation) {
            this.relation = relation;
            return this;
        }
        public Context<P, Q> build() {
            /*
            Context<P, Q> context = new Context<>();
            context.objects = this.objects;
            context.attributes = this.attributes;
            context.objectType = this.objectType;
            context.attributeType = this.attributeType;
            context.relation = this.relation;

             */

            return new Context(this.objects, this.attributes, this.relation);
        }

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