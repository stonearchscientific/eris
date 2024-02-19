package com.stonearchscientific.eris;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiFunctionTest {
    MultiFunction<Integer, Integer> mf;
    @Before
    public void setUp() {
        mf = new MultiFunction<>();
    }
    @Test
    public void testAdd() {

        mf.add(new Domain<>(Range.closed(1, 2), Range.closed(3, 6)));
        mf.add(new Domain<>(Range.closed(3, 4), Range.closed(5, 8)));
        mf.add(new Domain<>(Range.closed(5, 6), Range.closed(7, 10)));

        //RangeSet<Integer> rangeSet = TreeRangeSet.create();
        //rangeSet.add(Range.closed(1, 2));
        //rangeSet.add(Range.closed(3, 4));
        //System.out.println("SPAN: " + rangeSet.span());
        mf.draw("domain_test_add.png");

    }
}
