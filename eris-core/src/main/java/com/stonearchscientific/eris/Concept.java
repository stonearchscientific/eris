package com.stonearchscientific.eris;

import java.util.BitSet;
import com.google.common.base.Objects;
import com.google.common.collect.Range;
/**
 * A concept of a context (G, M, I) is defined to be a pair (A, B) where:<br>
 * - A is a subset of G<br>
 * - B is a subset of M<br>
 * - A' equals B<br>
 * - B' equals A<br>
 * <br>
 * In this context:<br>
 * - G is the set of objects<br>
 * - M is the set of attributes<br>
 * - I is a binary relation between G and M<br>
 * - A is the extent of the concept<br>
 * - B is the intent of the concept<br>
 * - A' is the set of all attributes that are common to all objects in A<br>
 * - B' is the set of all objects that have all attributes in B<br>
 * Davey, BA, &amp; Priestley, HA (2002). Introduction to Lattices and Order (2nd ed.). Cambridge University Press<br>
 */
public class Concept extends AbstractConcept<BitSet, BitSet, Concept> {


    private BitSet bitset(String bitstring) {
        return BitSet.valueOf(new long[]{Long.parseLong(bitstring, 2)});
    }

    private Concept() {
        super(null, null);
    }

    public Concept(BitSet extent, BitSet intent) {
        super(extent, intent);
    }

    public static Concept none(Class clazz) {
        if (clazz == BitSet.class) {
            return new Concept(new BitSet(), null);
        }
        return new Concept(null, null);
    }


    public Concept intersect(Concept that) {
        if (!(that instanceof Concept)) {
            throw new IllegalArgumentException("Both intent and extent must be instances of Range for intersection.");
        }
        if(that.intent == null) {
            return that;
        }
        BitSet thisIntent = (BitSet) this.intent;
        BitSet thatIntent = (BitSet) that.intent;
        BitSet meet = (BitSet) thisIntent.clone();
        meet.and(thatIntent);
        return new Concept(new BitSet(), meet);
    }

    public Concept union(Concept that) {
        if (!(that instanceof Concept)) {
            throw new IllegalArgumentException("Both intent and extent must be instances of Range for intersection.");
        }
        BitSet thisExtent = (BitSet) this.extent;
        BitSet thatExtent = (BitSet) that.extent;
        BitSet join = (BitSet) thisExtent.clone();
        thisExtent.or(thatExtent);
        return this;
    }
    /**
     * For concepts (A, B) and (C, D)<br>
     * - (A, B) &lt;= (C, D) if and only if A is a subset of C<br>
     * - By implication, B is a superset of D, and the reverse implication is also true<br>
     * Davey, BA, &amp; Priestley, HA (2002)<br>
     * @param that the concept to compare to<br>
     * @return true if this concept is &lt;= the given concept<br>
     * @see Concept#greaterOrEqual(Concept)
     */
    public boolean lessOrEqual(Concept that) {
        if (!(that instanceof Concept)) {
            throw new IllegalArgumentException("Both intent and extent must be instances of Range for intersection.");
        }
        if(this.intent == null) {
            return false;
        }
        if(that.intent == null) {
            return true;
        }
        BitSet thisIntent = (BitSet) this.intent;
        BitSet thatIntent = (BitSet) that.intent;
        BitSet join = (BitSet) thisIntent.clone();
        join.or(thatIntent);
        return join.cardinality() == thisIntent.cardinality();
    }
    /**
     * For concepts (A, B) and (C, D)<br>
     * - (A, B) &gt;= (C, D) if and only if A is a superset of C<br>
     * - By implication, B is a subset of D, and the reverse implication is also true<br>
     * Davey, BA, &amp; Priestley, HA (2002)<br>
     * @param that the concept to compare to<br>
     * @return true if this concept is &lt;= the given concept<br>
     * @see Concept#lessOrEqual(Concept)
     */
    public boolean greaterOrEqual(Concept that) {
        if (!(that instanceof Concept)) {
            throw new IllegalArgumentException("Both intent and extent must be instances of Range for intersection.");
        }
        if(this.intent == null) {
            return true;
        }
        if(that.intent == null) {
            return false;
        }
        BitSet thisIntent = (BitSet) this.intent;
        BitSet thatIntent = (BitSet) that.intent;
        BitSet meet = (BitSet) thatIntent.clone();
        meet.and(thisIntent);
            // System.out.println(thisIntent + " and " + thatIntent + " = " + meet);
        return meet.cardinality() == thisIntent.cardinality();
    }

    @Override
    public boolean equals(final Object that) {
        if (!(that instanceof Concept)) {
            return false;
        }
        if (that == this) {
            return true;
        }
        Concept concept = (Concept) that;
        Class<?> clazz = concept.extent.getClass();
        if (this == Concept.none(clazz) && that == Concept.none(clazz)) {
            return true;
        }
        return java.util.Objects.equals(concept.extent, this.extent) && java.util.Objects.equals(concept.intent, this.intent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.extent);
        if(this.intent == null){
            sb.append("()");
        } else {
            sb.append(this.intent);
        }
        return sb.toString();
    }
}