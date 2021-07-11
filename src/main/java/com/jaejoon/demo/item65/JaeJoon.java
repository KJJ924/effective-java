package com.jaejoon.demo.item65;

/**
 * @author dkansk924@naver.com
 * @since 2021/07/11
 */
public class JaeJoon {

    private String name;
    private Integer age;

    private JaeJoon(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
