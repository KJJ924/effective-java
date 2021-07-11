package com.jaejoon.demo.item65;

import java.lang.reflect.Constructor;

/**
 * @author dkansk924@naver.com
 * @since 2021/07/11
 */
public class Run {
    public static void main(String[] args) throws Exception{
        Constructor<JaeJoon> constructor =
            JaeJoon.class.getDeclaredConstructor(String.class, Integer.class); // (1)

        constructor.setAccessible(true); // (2)

        JaeJoon kim = constructor.newInstance("Kim", 10); // (3)

        System.out.println(kim.getName());
        System.out.println(kim.getAge());
    }
}
