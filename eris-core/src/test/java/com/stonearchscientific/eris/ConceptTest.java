package com.stonearchscientific.eris;

import java.util.BitSet;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public final class ConceptTest {
    private Concept w, x, y, z;

    private BitSet bitset(String bitstring) {
        return BitSet.valueOf(new long[]{Long.parseLong(bitstring, 2)});
    }

    @Before
    public void setUp() {
        w = new Concept(bitset("11111"), bitset("00000"));
        x = new Concept(bitset("11010"), bitset("00001"));
        y = new Concept(bitset("11101"), bitset("00100"));
        z = new Concept(bitset("11000"), bitset("11101"));
    }
    @Test
    public void testConstructor() {
        Concept p = new Concept(new BitSet(5), new BitSet(5));
        assertEquals(p.extent(), bitset("00000"));
        assertEquals(p.intent(), bitset("00000"));
        //Concept<BitSet, BitSet> b = new Concept<BitSet, BitSet>(new BitSet());
        //Concept<BitSet, BitSet> c = new Concept<BitSet, BitSet>(new BitSet(), new BitSet());
    }
    @Test
    public void testLessOrEqual() {
        assertTrue(x.lessOrEqual(x));
        assertTrue(x.lessOrEqual(w));
        assertTrue(z.lessOrEqual(x));
        assertFalse(x.lessOrEqual(y));
        assertFalse(y.lessOrEqual(x));
        assertFalse(x.lessOrEqual(z));
    }
    @Test
    public void testGreaterOrEqual() {
        assertTrue(x.greaterOrEqual(x));
        assertTrue(w.greaterOrEqual(x));
        assertTrue(x.greaterOrEqual(z));
        assertFalse(x.greaterOrEqual(y));
        assertFalse(y.greaterOrEqual(x));
        assertFalse(z.greaterOrEqual(x));
    }
    @Test
    public void testIntersect() {
        assertEquals(x.intersect(x).intent(), x.intent());
        assertEquals(x.intersect(w).intent(), w.intent());
        assertEquals(z.intersect(x).intent(), x.intent());
        assertEquals(x.intersect(y).intent(), w.intent());
        assertEquals(y.intersect(x).intent(), w.intent());
        assertEquals(x.intersect(z).intent(), x.intent());
    }
    @Test
    public void testEqual() {
        assertEquals(x, x);
        assertEquals(Concept.none(), Concept.none());
        assertNotEquals(x, Concept.none());
        assertNotEquals(x, y);
        assertNotEquals(x, z);
        assertNotEquals(null, x);
        assertNotEquals(x, new Object());
    }


}