package com.jaejoon.demo.item20;

public abstract class AbstractPhone implements Phone{

    protected void booting() {
        System.out.println("booting");
    }

    protected void display() {
        System.out.println("turn on display");
    }

    protected abstract void greeting();

    @Override
    public void process() {
        booting();
        greeting();
        display();
    }
}
