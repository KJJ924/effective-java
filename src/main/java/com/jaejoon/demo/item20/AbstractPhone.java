package com.jaejoon.demo.item20;

public abstract class AbstractPhone implements Phone{

    @Override
    public void booting() {
        System.out.println("booting");
    }

    @Override
    public void display() {
        System.out.println("turn on display");
    }

    @Override
    public void process() {
        booting();
        greeting();
        display();
    }
}
