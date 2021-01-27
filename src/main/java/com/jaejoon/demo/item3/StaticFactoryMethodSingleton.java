package com.jaejoon.demo.item3;

public class StaticFactoryMethodSingleton {

    private static final StaticFactoryMethodSingleton INSTANCE = new StaticFactoryMethodSingleton();

    private StaticFactoryMethodSingleton() {
    }

    public static StaticFactoryMethodSingleton getInstance(){
        return new StaticFactoryMethodSingleton();
    }
}
