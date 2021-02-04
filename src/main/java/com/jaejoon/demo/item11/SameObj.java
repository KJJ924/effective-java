package com.jaejoon.demo.item11;

import java.util.Objects;

public class SameObj {

    private final int id;

    public SameObj(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SameObj sameObj = (SameObj) o;
        return id == sameObj.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SameObj{" +
                "id=" + id +
                '}';
    }
}
