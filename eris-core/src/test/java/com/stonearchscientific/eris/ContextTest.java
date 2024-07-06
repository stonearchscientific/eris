package com.stonearchscientific.eris;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ContextTest {
    int[][] example = {
            {1, 1, 1, 1, 1, 0},
            {1, 1, 1, 0, 1, 0},
            {0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 1, 0},
            {1, 1, 1, 1, 0, 0}
    };
    Matrix relation;
    Context<String, String> context;
    private BitSet bitset(String bitstring) {
        return BitSet.valueOf(new long[]{Long.parseLong(bitstring, 2)});
    }
    @Before
    public void setUp() {
        relation = new Matrix(example);
        context = new Context<>(new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5")),
                  new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "x")), relation);
    }
    @Test
    public void testDual() {
        context.dual();
    }
    @Test
    public void testAdd() {
        int[][] example2 = {
                {1, 1, 1, 1, 1, 0},
                {1, 1, 1, 0, 1, 0},
                {0, 0, 1, 1, 0, 0},
        };
        Matrix relation2 = new Matrix(example2);
        Context<String, String> context2 = new Context<>(new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5")),
                                            new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "x")), relation2);
        context2.add(new Concept(bitset("01000"), bitset("10000")));
        context2.add(new Concept(bitset("10000"), bitset("01111")));
        context2.draw("testAdd.png");
        assertEquals(context2.size(), 9);
        assertEquals(context2.order(), 22);
        assertEquals(context2, context);
    }
    @Test
    public void testIterator() {
        Context.Iterator<Concept> iterator = context.iterator();
        int count = 1;
        Concept last = null;
        if(iterator.hasNext()) {
            last = iterator.next();
        } else {
            fail("Iterator should have at least one element");
        }
        assertEquals(last, new Concept(bitset("000000"), bitset("111111")));
        while (iterator.hasNext()) {
            last = iterator.next();
            count++;
        }
        assertEquals(count, 9);
        assertEquals(last, new Concept(bitset("11111"), bitset("00000")));
        // Test iteration in the other direction (dual)
        iterator = context.dual().iterator();
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
        assertEquals(count, 9);
        assertEquals(last, new Concept(bitset("000000"), bitset("111111")));
    }
    @Test
    public void testFind() {
        Concept concept = context.find(new ArrayList<>(Arrays.asList("a", "b", "c")));
        assertEquals(concept.extent(), bitset("10011"));
        concept = context.find(new ArrayList<>(Collections.singletonList("a")));
        assertEquals(concept.extent(), bitset("10011"));
        concept = context.find(new ArrayList<>(Arrays.asList("a", "e")));
        assertEquals(concept.extent(), bitset("00011"));
        concept = context.find(new ArrayList<>(Collections.singletonList("e")));
        assertEquals(concept.extent(), bitset("01011"));
        concept = context.find(new ArrayList<>(Arrays.asList("a", "x")));
        assertEquals(concept.extent(), bitset("00000"));
    }
    @Test
    public void testSupport() {
        ArrayList<String> a = new ArrayList<>(Collections.singletonList("a"));
        ArrayList<String> d = new ArrayList<>(Collections.singletonList("d"));
        ArrayList<String> ab = new ArrayList<>(Arrays.asList("a", "b"));
        ArrayList<String> cd = new ArrayList<>(Arrays.asList("c", "d"));
        ArrayList<String> e = new ArrayList<>(Collections.singletonList("e"));
        assertEquals(context.support(a, d), 0.4, 0.01);
        assertEquals(context.support(d, a), 0.4, 0.01);
        assertEquals(context.support(ab), 0.6, 0.01);
        assertEquals(context.support(cd, e), 0.2, 0.01);
    }
    @Test
    public void testConfidence() {
        ArrayList<String> c = new ArrayList<>(Collections.singletonList("c"));
        ArrayList<String> e = new ArrayList<>(Collections.singletonList("e"));
        assertEquals(context.confidence(c, e), 0.5, 0.01);
        assertEquals(context.confidence(e, c), 0.67, 0.01);
    }
    @Test
    public void testLift() {
        ArrayList<String> c = new ArrayList<>(Collections.singletonList("c"));
        ArrayList<String> e = new ArrayList<>(Collections.singletonList("e"));
        assertEquals(context.lift(c, e), 0.83, 0.01);
        assertEquals(context.lift(e, c), 0.83, 0.01);
    }
    @Test
    public void testConviction() {
        ArrayList<String> c = new ArrayList<>(Collections.singletonList("c"));
        ArrayList<String> e = new ArrayList<>(Collections.singletonList("e"));
        assertEquals(context.conviction(c, e), 0.8, 0.01);
        assertEquals(context.conviction(e, c), 0.6, 0.01);
    }
}