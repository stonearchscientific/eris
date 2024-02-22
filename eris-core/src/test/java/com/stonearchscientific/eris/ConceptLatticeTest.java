package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Before;
import org.junit.Test;


import java.util.BitSet;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

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
        Concept topConcept = lattice.top().getProperty(Lattice.LABEL);
        assertEquals(topConcept.intent(), bitset("00000"));
        assertEquals(topConcept.extent(), bitset("11111"));
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
        int count = 1;
        Concept last = null;
        if(iterator.hasNext()) {
            last = iterator.next();
        } else {
            fail("Iterator should have at least one element");
        }
        assertEquals(last, c1);
        while (iterator.hasNext()) {
            last = iterator.next();
            count++;
        }
        assertEquals(count, 8);
        assertEquals(last, new Concept(bitset("11111"), bitset("00000")));
        // Test iteration in the other direction (dual)
        iterator = lattice.dual().iterator();
        count = 1;
        last = null;
        if(iterator.hasNext()) {
            last = iterator.next();
        } else {
            fail("Iterator should have at least one element");
        }
        assertEquals(last, new Concept(bitset("11111"), bitset("00000")));
        while (iterator.hasNext()) {
            last = iterator.next();
            count++;
        }
        assertEquals(count, 8);
        assertEquals(last, new Concept(bitset("00001"), bitset("11111")));
        // Test iteration from given Concept
        iterator = lattice.iterator(c3);
        count = 1;
        last = null;
        if(iterator.hasNext()) {
            last = iterator.next();
        } else {
            fail("Iterator should have at least one element");
        }
        assertEquals(last, c3);
        while (iterator.hasNext()) {
            last = iterator.next();
            count++;
        }
        assertEquals(count, 3);
        assertEquals(last, c1);
    }
}