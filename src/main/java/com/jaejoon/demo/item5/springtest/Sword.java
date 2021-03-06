package com.jaejoon.demo.item5.springtest;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Sword implements Weapon {

    private final String name ="롱소드";
    private final int damage = 10;

    @Override
    public void attack() {
        System.out.println(this.damage+" 만큼의 공격을 했습니다");
    }

    @Override
    public void infoWeapon() {
        System.out.println("해당무기의 타입은"+this.name +"입니다");
    }
}
