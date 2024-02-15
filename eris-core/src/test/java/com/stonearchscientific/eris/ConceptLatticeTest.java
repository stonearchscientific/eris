package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


import java.util.BitSet;
import java.util.Arrays;
import java.util.List;

public class ConceptLatticeTest {
    private Graph graph;
    List<String> objects, attributes;
    Concept c1, c2, c3, c4, c5;
    Lattice<Concept> lattice;

    int[][] expected = {
            {0, 1, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 1, 1},
            {0, 0, 1, 0, 0, 0, 1, 1},
            {0, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 1, 0, 1, 1, 0, 0, 0}
    };

    private BitSet bitset(String bitstring) {
        return BitSet.valueOf(new long[]{Long.parseLong(bitstring, 2)});
    }

    private int numberOfVertices(Graph graph) {
        int count = 0;
        for(Vertex vertex : graph.getVertices()) {
            count++;
        }
        return count;
    }

    private int numberOfEdges(Graph graph) {
        int count = 0;
        for(Edge edge : graph.getEdges()) {
            count++;
        }
        return count;
    }

    @Before
    public void setUp() {
        graph = new TinkerGraph();
        objects = Arrays.asList("1", "2", "3", "4", "5");
        attributes = Arrays.asList("a", "b", "c", "d", "e");

        c1 = new Concept(bitset("00001"), bitset("11111"));
        c2 = new Concept(bitset("00010"), bitset("10111"));
        c3 = new Concept(bitset("00100"), bitset("01100"));
        c4 = new Concept(bitset("01000"), bitset("10000"));
        c5 = new Concept(bitset("10000"), bitset("01111"));

        lattice = new Lattice(graph, c1);
        lattice.insert(graph, c2);
        lattice.insert(graph, c3);
        lattice.insert(graph, c4);
        lattice.insert(graph, c5);
    }
    @Test
    public void testSetup() {
        assertEquals(numberOfVertices(graph), 8);
        assertEquals(numberOfEdges(graph), 20);
        assertEquals(lattice.size(), 8);
        assertEquals(lattice.order(), 20);
    }
    @Test
    public void testAddIntent() {
        int[][] observed = Matrix.generateAdjacencyMatrix(graph);

        for(int i = 0; i < observed.length; i++) {
            for(int j = 0; j < observed[i].length; j++) {
                assertEquals(expected[i][j], observed[i][j]);
            }
        }
    }
    /*
    @Test
    public void testSupremum() {

    }
     */
    @Test
    public void testIterator() {
        Lattice.Iterator<Concept> iterator = lattice.iterator();
        int count = 0;
        for(; iterator.hasNext(); iterator.next()) { count++; }
        assertEquals(count, 8);
        count = 0;
        iterator = lattice.iterator(c2);
        for(; iterator.hasNext(); iterator.next()) { count++; }
        assertEquals(count, 5);
        // test iterator on empty lattice with just none. Right now I think that doesn't work properly
        iterator = lattice.iterator();
        System.out.println("LATTICE");
        while(iterator.hasNext()) {
            Concept concept = iterator.next();
            System.out.println("Concept: " + concept);
        }
        System.out.println("DUAL LATTICE");
        iterator = lattice.dual().iterator();
        while(iterator.hasNext()) {
            Concept concept = iterator.next();
            System.out.println("Concept: " + concept);
        }
    }
}