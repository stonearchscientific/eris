package com.stonearchscientific.eris;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

public final class Domain<C extends Comparable, D extends Comparable> extends AbstractConcept<Range<C>, Range<D>> {
    private static final Domain ALL = new Domain<>(Range.all(), null);
    private static final Domain NONE = new Domain<>(null, Range.all());
    public Domain(Range<C> extent, Range<D> intent) {
        super(extent, intent);
    }
    public static <C extends Comparable<?>, D extends Comparable<?>> Domain<C, D> all() {
        return (Domain) ALL;
    }
    public static <C extends Comparable<?>, D extends Comparable<?>> Domain<C, D> none() {
        return (Domain) NONE;
    }
    public Domain<C, D> intersect(final Domain<C, D> that) {
        if (!(that instanceof Domain)) {
            throw new IllegalArgumentException("Cannot intersect Domain and " + that.getClass().getName() + ".");
        }
        if(that.intent == null) {
            return that;
        }
        if(this == ALL || that.intent == null) {
            return that;
        }
        if(this.intent == null) {
            return new Domain(Range.all(), null);
        }
        if(that.intent == Range.all()) {
            return new Domain(this.extent, this.intent);
        }
        if(this.intent.isConnected(that.intent)) {
            // todo: check if this is the correct way to intersect the Concepts with Range as their extent type
            return new Domain(this.extent, this.intent.intersection(that.intent));
        }
        return new Domain(Range.all(), null);

    }
    public Domain<C, D> union(final Domain<C, D> that) {
        if (!(that instanceof Domain)) {
            throw new IllegalArgumentException("Cannot union Domain and " + that.getClass().getName() + ".");
        }
        RangeSet<C> rangeSet = TreeRangeSet.create();
        rangeSet.add(this.extent);
        rangeSet.add(that.extent);
        this.extent = rangeSet.span();
        return this;
    }
    public boolean lessOrEqual(Domain<C, D> that) {
        if (!(that instanceof Domain)) {
            throw new IllegalArgumentException("Cannot compare Domain and " + that.getClass().getName() + ".");
        }
        if(that.intent == null) {
            return true;
        }
        if(this.intent == null) {
            return false;
        }
        Range intersectIntent = this.intersect(that).intent;
        Range thatIntent = that.intent;
        return intersectIntent == that.intent;
    }
    public boolean greaterOrEqual(Domain<C, D> that) {
        if (!(that instanceof Domain)) {
            throw new IllegalArgumentException("Cannot compare Domain and " + that.getClass().getName() + ".");
        }
        if(this.intent == null) { // this == ALL
            return true;
        }
        if(that.intent == null) { // that == ALL
            return false;
        }
        Range intersectIntent = this.intersect(that).intent;
        Range thatIntent = that.intent;
        return intersectIntent == this.intent;
    }
}