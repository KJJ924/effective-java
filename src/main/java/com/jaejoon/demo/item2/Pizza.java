package com.jaejoon.demo.item2;

import java.util.EnumSet;
import java.util.Objects;

public abstract class Pizza {
    public enum Topping {HAM, MUSHROOM,ONION,PEPPER}
    final EnumSet<Topping> toppings;

    abstract static class Builder<T extends Builder<T>>{
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
        public T addTopping(Topping topping){
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        protected abstract T self();

    }

    Pizza(Builder<?> builder){
        toppings = builder.toppings.clone();
    }

}
