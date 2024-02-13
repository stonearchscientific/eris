package com.stonearchscientific.eris;

import com.google.common.collect.Range;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public final class DomainTest {
    private Domain<Integer, Integer> a, b, c, d;
    @Before
    public void setUp() {
        a = new Domain<>(Range.all(), null);
        b = new Domain<>(Range.closed(3, 4), Range.closed(3, 4));
        c = new Domain<>(Range.closed(1, 2), Range.closed(1, 2));
        d = new Domain<>(Range.closed(0, 4), Range.closed(0, 4));
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
        assertTrue(Domain.all().lessOrEqual(Domain.all()));
        assertTrue(Domain.none().lessOrEqual(Domain.all()));
        assertTrue(Domain.none().lessOrEqual(Domain.none()));
        assertTrue(b.lessOrEqual(Domain.all()));
        assertTrue(d.lessOrEqual(b));
        assertTrue(b.lessOrEqual(b));
        assertTrue(b.lessOrEqual(a));
        assertTrue(d.lessOrEqual(b));
        assertFalse(b.lessOrEqual(c));
        assertFalse(c.lessOrEqual(b));
        assertFalse(b.lessOrEqual(d));
    }

}