package com.jaejoon.demo.item5.springtest;


public class Warrior {

    private final Weapon weapon;

    public Warrior(Weapon weapon) {
        this.weapon = weapon;
    }

    public void attack(){
        weapon.attack();
    }
}
