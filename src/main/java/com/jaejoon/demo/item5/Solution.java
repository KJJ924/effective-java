package com.jaejoon.demo.item5;

import com.jaejoon.demo.item5.weapon.Dagger;
import com.jaejoon.demo.item5.weapon.Sword;
import com.jaejoon.demo.item5.weapon.Weapon;

public class Solution {
    public static void main(String[] args) {
        Weapon dagger = new Dagger();
        Weapon sword = new Sword();

        Warrior warrior = new Warrior(dagger);
        Warrior warrior1 = new Warrior(sword);

        warrior.attack();
        warrior1.attack();
    }
}
