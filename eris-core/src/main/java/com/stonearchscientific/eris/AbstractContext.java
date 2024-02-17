package com.stonearchscientific.eris;

import com.tinkerpop.blueprints.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractContext<R extends Relatable<R>> implements Collection<R> {
    protected boolean up;
    protected Graph graph;
    protected Lattice<R> lattice;
    public AbstractContext<R> dual() {
        up = !up;
        return this;
    }
    @Override
    public int size() { return lattice.size(); }
    public int order() { return lattice.order(); }
    @Override
    public boolean isEmpty() { return lattice.size() == 0; }
    @Override
    public boolean add(R relatable) {
        int size = lattice.size();
        lattice.insert(graph, relatable);
        return lattice.size() > size;
    }
    @Override
    public boolean addAll(Collection<? extends R> c) {
        int size = lattice.size();
        for (R o : c) {
            lattice.insert(graph, o);
        }
        return lattice.size() > size;
    }
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        checkNotNull(c);
        throw new UnsupportedOperationException("retainAll");
    }
    @Override
    public void clear() { lattice.clear(); }
    public static class Iterator<R extends Relatable<R>> implements java.util.Iterator<R> {
        private final Lattice.Iterator<R> iterator;
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
    @Override
    public Iterator<R> iterator() {
        Lattice.Iterator<R> iterator;
        if(up) {
            iterator = lattice.iterator();
        } else {
            iterator = lattice.dual().iterator();
        }
        return new Iterator<>(iterator);
    }
    @Override
    public Object[] toArray() {
        List<R> list = new ArrayList<>();
        Iterator<R> iterator = iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list.toArray();
    }
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("toArray");
    }
}
