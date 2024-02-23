package com.stonearchscientific.eris;

import com.google.common.collect.Range;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public final class DomainTest {
    private Domain<Integer, Integer> a, b, c, d, z;
    @Before
    public void setUp() {
        a = Domain.all();
        b = new Domain<>(Range.closed(3, 4), Range.closed(3, 4));
        c = new Domain<>(Range.closed(1, 2), Range.closed(1, 2));
        d = new Domain<>(Range.closed(0, 4), Range.closed(0, 4));
        z = Domain.none();
    }
    @Test
    public void testSetUp() {
        System.out.println("Domain a = " + a);
        System.out.println("Domain b = " + b);
        System.out.println("Domain c = " + c);
        System.out.println("Domain d = " + d);
        System.out.println("Domain ALL = " + Domain.all());
        System.out.println("Domain NONE = " + Domain.none());
    }
    @Test
    public void testLessOrEqual() {
        assertTrue(a.lessOrEqual(Domain.all()));
        assertTrue(z.lessOrEqual(Domain.all()));
        assertTrue(z.lessOrEqual(Domain.none()));
        assertTrue(b.lessOrEqual(Domain.all()));
        assertTrue(d.lessOrEqual(b));
        assertTrue(b.lessOrEqual(b));
        assertTrue(b.lessOrEqual(a));
        assertTrue(d.lessOrEqual(b));
        assertFalse(a.lessOrEqual(z));
        assertFalse(b.lessOrEqual(c));
        assertFalse(c.lessOrEqual(b));
        assertFalse(b.lessOrEqual(d));
    }
    @Test
    public void testGreaterOrEqual() {
        assertTrue(Domain.all().greaterOrEqual(Domain.all()));
        assertTrue(Domain.all().greaterOrEqual(Domain.none()));
        assertTrue(Domain.none().greaterOrEqual(Domain.none()));
        assertTrue(b.greaterOrEqual(Domain.none()));
        assertTrue(a.greaterOrEqual(b));
        assertTrue(b.greaterOrEqual(b));
        assertTrue(b.greaterOrEqual(d));
        assertFalse(b.greaterOrEqual(c));
        assertFalse(c.greaterOrEqual(b));
        assertFalse(d.greaterOrEqual(b));
    }
    @Test
    public void testIntersect() {
        System.out.println("none intersect b: " + z.intersect(b));
        System.out.println("b intersect none: " + b.intersect(z));
    }
    @Test
    public void testEquals() {
        assertEquals(Domain.all(), Domain.all());
        assertEquals(Domain.none(), Domain.none());
        assertEquals(Domain.all(), a);
        assertEquals(Domain.none(), new Domain<>(null, Range.all()));
        assertEquals(b, b);
        assertNotEquals(b, c);

    }

}