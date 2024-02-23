package com.stonearchscientific.eris;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

public final class Domain<C extends Comparable, D extends Comparable> extends AbstractConcept<Range<C>, Range<D>, Domain<C, D>> {
    private static final Domain<Comparable<?>, Comparable<?>> ALL = all();
    private static final Domain<Comparable<?>, Comparable<?>> NONE = none();
    public Domain(Range<C> extent, Range<D> intent) {
        super(extent, intent);
    }
    public static <C extends Comparable<?>, D extends Comparable<?>> Domain<C, D> all() {
        return new Domain<>(Range.all(), null);
    }
    public static <C extends Comparable<?>, D extends Comparable<?>> Domain<C, D> none() {
        return new Domain<>(null, Range.all());
    }
    public Domain<C, D> intersect(final Domain<C, D> that) {
        if(that.intent == null) {
            return that;
        }
        if(this == ALL) {
            return that;
        }
        if(this.intent == null) {
            return new Domain<>(Range.all(), null);
        }
        if(that.intent == Range.all()) {
            return new Domain<>(this.extent, this.intent);
        }
        Range<C> thisExtent = this.extent;
        // Range<C> thatExtent = that.extent;
        Range<D> thisIntent = this.intent;
        Range<D> thatIntent = that.intent;
        if(thisIntent.isConnected(thatIntent)) {
            // todo: check if this is the correct way to intersect the Concepts with Range as their extent type
            return new Domain<>(thisExtent, thisIntent.intersection(thatIntent));
        }
        return new Domain<>(Range.all(), null);

    }
    public Domain<C, D> union(final Domain<C, D> that) {
        if(that.extent == null) { // TODO: try that == none()
            return this;
        }
        if(this.extent == null) { // TODO: try this == none()
            this.extent = that.extent;
            return this;
        }
        RangeSet<C> rangeSet = TreeRangeSet.create();
        rangeSet.add(this.extent);
        rangeSet.add(that.extent);
        this.extent = rangeSet.span();
        return this;
    }
    public boolean lessOrEqual(final Domain<C, D> that) {
        if(that.intent == null) {
            return true;
        }
        if(this.intent == null) {
            return false;
        }
        Range<D> intersectIntent = this.intersect(that).intent;
        return intersectIntent == that.intent;
    }

    public boolean greaterOrEqual(final Domain<C, D> that) {
        if(this.intent == null) { // this == ALL
            return true;
        }
        if(that.intent == null) { // that == ALL
            return false;
        }
        Range<D> intersectIntent = this.intersect(that).intent;
        return intersectIntent == this.intent;
    }
    @Override
    public boolean equals(final Object that) {
        if (!(that instanceof Domain)) {
            return false;
        }
        if (that == this) {
            return true;
        }
        Domain<C, D> domain = (Domain) that;
        return java.util.Objects.equals(domain.extent, this.extent) && java.util.Objects.equals(domain.intent, this.intent);
    }
}