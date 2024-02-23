package com.stonearchscientific.eris;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ContextTest {
    int[][] example = {
            {1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1},
            {0, 0, 1, 1, 0},
            {0, 0, 0, 0, 1},
            {1, 1, 1, 1, 0}
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
                  new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e")), relation);
    }
    @Test
    public void testDual() {
        context.dual();
    }
    @Test
    public void testAdd() {
        int[][] example2 = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 0, 1},
                {0, 0, 1, 1, 0},
        };
        Matrix relation2 = new Matrix(example2);
        Context<String, String> context2 = new Context<>(new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5")),
                                            new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e")), relation2);
        context2.add(new Concept(bitset("01000"), bitset("10000")));
        context2.add(new Concept(bitset("10000"), bitset("01111")));
        context2.draw("testAdd.png");
        assertEquals(context2.size(), 8);
        assertEquals(context2.order(), 20);
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
        assertEquals(last, new Concept(bitset("00001"), bitset("11111")));
        while (iterator.hasNext()) {
            last = iterator.next();
            count++;
        }
        assertEquals(count, 8);
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
        assertEquals(count, 8);
        assertEquals(last, new Concept(bitset("00001"), bitset("11111")));
    }
}