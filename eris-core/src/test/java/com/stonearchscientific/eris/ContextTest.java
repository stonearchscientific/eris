package com.stonearchscientific.eris;

import java.util.BitSet;
import java.util.ArrayList;
//import com.stonearchscientific.eris.Concept;
//import com.stonearchscientific.eris.Lattice;
import com.stonearchscientific.eris.Context;
import com.stonearchscientific.eris.Matrix;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class ContextTest {
    int[][] example = {
            {1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1},
            {0, 0, 1, 1, 0},
            {0, 0, 0, 0, 1},
            {1, 1, 1, 1, 0}
    };
    Matrix relation;
    Context context;
    @Before
    public void setUp() {
        relation = new Matrix(example);
        context = new Context(new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5")),
                  new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e")), relation);
    }
    @Test
    public void testDual() {
        context.dual();
    }
    @Test
    public void testIterator() {
        Context.Iterator<Concept> iterator = context.iterator();
        while (iterator.hasNext()) {
            System.out.println("Context:Concept = " + iterator.next());
        }
    }
}