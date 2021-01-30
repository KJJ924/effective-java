package com.jaejoon.demo.item5.springtest;

import org.springframework.stereotype.Service;

@Service
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
