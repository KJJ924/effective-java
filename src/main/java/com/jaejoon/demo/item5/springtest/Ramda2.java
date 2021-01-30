package com.jaejoon.demo.item5.springtest;

import java.util.function.BiFunction;

public class Ramda2 {

    public static void main(String[] args) {
        test sum= (a, b) -> a+b;
        test minus =(a,b) ->a-b;

        int pack = sum.PACK(1, 2);
        System.out.println("pack = " + pack);

        BiFunction<Integer, Integer, Integer> su2 =(a,b) ->a+b;
        Integer apply = su2.apply(1, 2);
        System.out.println("apply = " + apply);
    }
}
