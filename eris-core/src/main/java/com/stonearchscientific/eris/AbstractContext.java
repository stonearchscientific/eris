package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Graph;

public abstract class AbstractContext<R extends Relatable> implements Relation<R> {
    protected boolean up;
    protected Graph graph;
    protected Lattice<Concept> lattice;
    public AbstractContext dual() {
        up = up ? false : true;
        return this;
    }
    public static class Iterator<R extends Relatable> implements java.util.Iterator<R> {
        private Lattice.Iterator<R> iterator;
        public Iterator(final Lattice.Iterator<R> iterator) {
            this.iterator = iterator;
        }
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
        @Override
        public R next() {
            return iterator.next();
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }
    public Iterator<R> iterator() {
        Lattice.Iterator<R> iterator;
        if(up) {
            iterator = (Lattice.Iterator<R>) lattice.iterator();
        } else {
            iterator = lattice.dual().iterator();
        }
        return new Iterator<>(iterator);
    }
}
