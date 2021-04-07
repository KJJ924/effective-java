package com.jaejoon.demo.item86;

import java.io.Serializable;


public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private String address;
    private String email;

    public Member(String name, int age, String address) {
        if(age ==26){
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public long getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Member{" +
            "name='" + name + '\'' +
            ", age=" + age +
            ", address='" + address + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
