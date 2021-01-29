package com.jaejoon.demo.item5;

import com.jaejoon.demo.item5.weapon.Weapon;

import java.util.Objects;

public class Warrior {

    private final Weapon weapon;

    public Warrior(Weapon weapon) {
        this.weapon = Objects.requireNonNull(weapon);
    }

    public void attack(){
        weapon.attack();
    }
}
