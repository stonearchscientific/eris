package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class Lattice<R extends Relatable> implements Iterable<R> {
    private boolean up;
    private Vertex top;
    private final Vertex bottom;
    private int size, order;
    static final String LABEL = "label";

    public Lattice(Graph graph, final R bottom) {
        checkNotNull(graph);
        up = true;
        order = 0;
        //color = 0;
        this.bottom = graph.addVertex(null);
        this.bottom.setProperty(LABEL, bottom);
        //this.bottom.setProperty(COLOR, color);
        //System.out.println("constructor added bottom vertex: " + this.bottom.getProperty(LABEL));
        this.top = this.bottom;
        //System.out.println("top: " + this.top.getProperty(LABEL));
        size = 1;
    }
    public Vertex top() {
        return up ? top : bottom;
    }
    public Vertex bottom() { return up ? bottom : top; }
    public Lattice dual() {
        up = !up;
        return this;
    }
    public void clear() {
        top = bottom;
        size = 1;
        order = 0;
    }
    public int size() { return size; }
    public int order() { return order; }
    public static class Iterator<R extends Relatable> implements java.util.Iterator<R> {
        private final Set<Vertex> visited;
        private final List<Vertex> queue;
        private final Fixture<R> fixture;
        public Iterator(final Vertex start, final Fixture<R> fixture) {
            visited = new HashSet<>();
            visited.add(start);
            queue = new ArrayList<>();
            queue.add(start);
            this.fixture = fixture;
        }
        @Override
        public boolean hasNext() {
            if(queue.isEmpty()) {
                fixture.finish();
                return false;
            }
            return true;
        }
        @Override
        public R next() {
            Vertex visiting = queue.remove(0);
            R visitingConcept = visiting.getProperty(LABEL);

            for (Edge edge : visiting.getEdges(Direction.BOTH)) {
                Vertex target = edge.getVertex(Direction.OUT);
                R targetConcept = target.getProperty(LABEL);
                if (fixture.apply(target, visiting, edge) && !visited.contains(target)) {
                    visited.add(target);
                    queue.add(target);
                }
            }
            return visitingConcept;
        }
    }

    /**
     *
     * @return new iterator starting from the bottom concept
     * Note: Iterator is constructed from the method Lattice.bottom() instead of the attribute
     * Lattice.bottom. This is for consistency when construction happens in a static context outside of a Lattice
     * instance.
     */
    @Override
    public Iterator<R> iterator() { return new Iterator<>(bottom(), new DefaultFixture<>(new DefaultFilter<>(up))); }
    public Iterator<R> iterator(final R from) {
        Vertex found = supremum(from, bottom);
        return new Iterator<>(found, new DefaultFixture<>(new DefaultFilter<>(up)));
    }
    public Iterator<R> iterator(final R from, final Fixture<R> fixture) {
        Vertex found = supremum(from, bottom);
        return new Iterator<>(found, fixture);
    }
    public boolean filter(final Vertex source, final Vertex target) {
        R sourceConcept = source.getProperty(LABEL);
        R targetConcept = target.getProperty(LABEL);
        return filter(sourceConcept, targetConcept);
    }

    private boolean filter(final Vertex source, final R right) {
        if(source.getProperty(LABEL) == null) {
            return false;
        }

        R sourceConcept = source.getProperty(LABEL);
        return filter(sourceConcept, right);
    }

    private boolean filter(final R left, final Vertex target) {
        R targetConcept = target.getProperty(LABEL);
        return filter(left, targetConcept);
    }

    private boolean filter(final R left, final R right) {
        //System.out.println(right + " >=  " + left + " : " + (right.greaterOrEqual(left) ? "true" : "false"));
        return right.greaterOrEqual(left);
    }
    /*
    protected final Vertex find(final Graph graph, final Concept<T, U> proposed, Vertex generator) {
    boolean max = true;
    while (max) {
        max = false;
        Iterator<Vertex> iterator = graph.traversal().V(generator.id()).both();
        while (iterator.hasNext()) {
            Vertex target = iterator.next();
            if (!filter(target, generator)) {
                continue;
            }
            if (filter(target, proposed)) {
                generator = target;
                max = true;
                break;
            }
        }
    }
    return generator;
}*/
    protected final Vertex supremum(final R proposed, Vertex generator) {
        boolean max = true;
        while (max) {
            max = false;
            for (Edge edge : generator.getEdges(Direction.BOTH)) {
                Vertex target = edge.getVertex(Direction.OUT);
                //System.out.println("supremum(" + proposed + ", " + generator.getProperty(LABEL) + ") : " + target.getProperty(LABEL));
                if (filter(target, generator)) {
                    continue;
                }
                //Concept proposed = new Concept(new MutableBitSet(), proposed.intent);

                if (filter(target, proposed)) {
                    generator = target;
                    max = true;
                    break;
                }
            }
        }
        return generator;
    }
    public R find(final R proposed) {
        Vertex found = supremum(proposed, bottom);
        return found.getProperty(LABEL);
    }

    public Vertex insert(final Graph graph, final R relatable) {
        Vertex added = addIntent(graph, relatable, bottom);
        //System.out.println("ADDED INTENT");
        // TODO: replace with iteration
        Set<Vertex> visited = new HashSet<>();
        visited.add(added);
        List<Vertex> queue = new ArrayList<>();
        queue.add(added);

        while (!queue.isEmpty()) {
            Vertex visiting = queue.remove(0);
            R visitingConcept = visiting.getProperty(LABEL);
            //System.out.println("UNION");
            visitingConcept.union(relatable);
            //System.out.println("DONE");

            for (Edge edge : visiting.getEdges(Direction.BOTH)) {
                Vertex target = edge.getVertex(Direction.OUT);
                if (!visited.contains(target) && filter(visiting, target)) {
                    visited.add(target);
                    queue.add(target);
                }
            }
        } // TODO: end
        return added;
    }
    private Vertex addVertex(final Graph graph, final R label) {
        Vertex child = graph.addVertex(null);
        //System.out.println("addVertex(" + label + ")");
        child.setProperty("label", label);
        ++size;
        return child;
    }
    private Edge addUndirectedEdge(final Graph graph, final Vertex source, final Vertex target, final String weight) {
        graph.addEdge(null, source, target, weight);
        //System.out.println("addUndirectedEdge(" + source.getProperty(LABEL) + ", " + target.getProperty(LABEL) + ")");
        Edge edge = graph.addEdge(null, target, source, weight);
        order += 2;
        return edge;
    }
    private void removeUndirectedEdge(final Graph graph, final Vertex source, final Vertex target) {
        //System.out.println("removeUndirectedEdge(" + source.getProperty(LABEL) + ", " + target.getProperty(LABEL) + ")");
        for (Edge edge : source.getEdges(Direction.BOTH)) {
            if (edge.getVertex(Direction.OUT).equals(target)) {
                graph.removeEdge(edge);
                //break;
                --order;
            }

            if (edge.getVertex(Direction.IN).equals(target)) {
                graph.removeEdge(edge);
                --order;
                //break;
            }

        }
    }
    public final Vertex addIntent(final Graph graph, final R proposed, Vertex generator) {
        //System.out.println("addIntent(" + proposed + ", " + generator.getProperty(LABEL) + ")");
        generator = supremum(proposed, generator);

        if (filter(generator, proposed) && filter(proposed, generator)) {
            return generator;
        }

        List parents = new ArrayList<>();
        for (Edge edge : generator.getEdges(Direction.BOTH)) {
            Vertex target = edge.getVertex(Direction.OUT);
            if (filter(target, generator)) {
                continue;
            }

            Vertex candidate = target;
            if (!filter(target, proposed) && !filter(proposed, target)) {
                R targetElement = target.getProperty(LABEL);
                R intersect = (R) targetElement.intersect(proposed);
                //System.out.println(targetElement + " intersect " + proposed + " = " + intersect);
                candidate = addIntent(graph, intersect, candidate);
            }

            boolean add = true;
            List doomed = new ArrayList<>();
            for (java.util.Iterator it = parents.iterator(); it.hasNext();) {
                Vertex parent = (Vertex) it.next();

                if (filter(parent, candidate)) {
                    add = false;
                    break;
                }
                else if (filter(candidate, parent)) {
                    doomed.add(parent);
                }
            }

            for (java.util.Iterator it = doomed.iterator(); it.hasNext();) {
                Vertex vertex = (Vertex) it.next();
                parents.remove(vertex);
            }

            if (add) {
                parents.add(candidate);
            }
        }

        R generatorLabel = generator.getProperty(LABEL);

        Vertex child = addVertex(graph, (R) proposed.union(generatorLabel));
        addUndirectedEdge(graph, generator, child, "");

        top = filter(top, proposed) ? child : top;

        for (java.util.Iterator it = parents.iterator(); it.hasNext();) {
            Vertex parent = (Vertex) it.next();
            if (!parent.equals(generator)) {
                removeUndirectedEdge(graph, parent, generator);
                addUndirectedEdge(graph, parent, child, "");
            }
        }
        return child;
    }
}