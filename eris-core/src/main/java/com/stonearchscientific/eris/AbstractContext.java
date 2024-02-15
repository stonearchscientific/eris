package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractContext<R extends Relatable> implements Relation<R> {
    protected boolean up;
    protected Graph graph;
    protected Lattice lattice;
    public AbstractContext dual() {
        up = up ? false : true;
        return this;
    }
    public int size() { return lattice.size(); }
    public int order() { return lattice.order(); }
    public boolean isEmpty() { return lattice.size() == 0; }
    public boolean add(R relatable) {
        int size = lattice.size();
        lattice.insert(graph, relatable);
        return lattice.size() > size;
    }
    public boolean addAll(Collection<? extends R> c) {
        int size = lattice.size();
        for (Object o : c) {
            lattice.insert(graph, (R) o);
        }
        return lattice.size() > size;
    }
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll");
    }
    public void clear() { lattice.clear(); }
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
    public R[] toArray() {
        List<R> list = new ArrayList<>();
        Iterator<R> iterator = iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return (R[]) list.toArray();
    }
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("toArray");
    }
}
