package com.jaejoon.demo.Item55;


public class Member {

    private final String name;

    public Member(String name) {
        this.name = name;
    }

    public static Member of (String name){
        return new Member(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Member{" +
            "name='" + name + '\'' +
            '}';
    }
}
