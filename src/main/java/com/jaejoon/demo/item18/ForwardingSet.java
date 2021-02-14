package com.jaejoon.demo.item18;

import java.util.*;

public class ForwardingSet<E> extends HashSet<E> {

    private final Set<E> set;

    public ForwardingSet(Set<E> set){
        this.set = set;
    }

    @Override
    public Iterator<E> iterator() {
        return set.iterator();
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public boolean add(E e) {
        return set.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public Spliterator<E> spliterator() {
        return set.spliterator();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return set.addAll(c);
    }
}
