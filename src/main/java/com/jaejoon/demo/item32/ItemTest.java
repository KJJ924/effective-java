package com.jaejoon.demo.item32;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author dkansk924@naver.com
 * @since 2021/06/05
 */

public class ItemTest {

    public void print(String ... args){
        for (String arg : args) {
            System.out.println(arg);
        }
    }

    public void print(List<String> ... stringList){
        List<Integer> intList = List.of(42);
        Object[] objects = stringList;
        objects[0] = intList;    // 힙오염 발생
        String s = stringList[0].get(0) ;    // ClassCastException
    }

    static <T> T[] toArray(T... args) {
        System.out.println(args.getClass().getName());
        return args;
    }

    static <T> List<T> pickTwo(T a, T b, T c) {
        System.out.println(a.getClass().getName());
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return Arrays.asList(a, b);
            case 1:
                return Arrays.asList(b, c);
            case 2:
                return Arrays.asList(a, c);
        }
        throw new AssertionError();
    }

    public static void main(String[] args) {
        List<String> strings = pickTwo("좋은", "빠른", "저렴한");
    }
}
