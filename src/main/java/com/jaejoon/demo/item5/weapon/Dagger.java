package com.jaejoon.demo.item5.weapon;

public class Dagger implements Weapon{

    private final String name = "단검";
    private final int damage = 4;
    @Override
    public void attack() {
        System.out.println(this.damage+" 만큼의 공격을 했습니다");

    }

    @Override
    public void infoWeapon() {
        System.out.println("해당무기의 타입은"+this.name +"입니다");
    }
}
