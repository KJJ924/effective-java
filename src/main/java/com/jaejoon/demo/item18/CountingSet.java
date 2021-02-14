package com.jaejoon.demo.item18;

import java.util.Collection;
import java.util.Set;

public class CountingSet<E> extends ForwardingSet<E> {

    private int count;

    public CountingSet(Set<E> set) {
        super(set);
    }

    @Override
    public boolean add(E e) {
        count++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        count+=c.size();
        return super.addAll(c);
    }

    public int getCount() {
        return count;
    }
}
