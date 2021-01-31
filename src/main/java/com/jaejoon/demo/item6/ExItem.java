package com.jaejoon.demo.item6;

import java.util.regex.Pattern;

public class ExItem {
    public static final Pattern ROMA =Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    static boolean isRomanNumeral2(String s) {
        return ROMA.matcher(s).matches();
    }
    public static void main(String[] args) {
        String name ="jaejoon";
        String name1 ="jaejoon";
        String name2 = new String("jaejoon");
        System.out.println(name==name1);
        System.out.println(name==name2);

        //==========================
        Boolean aBoolean = new Boolean("true");
        Boolean bBoolean = Boolean.valueOf("true");

        //============================
        Long sum =0L;
        long start = System.currentTimeMillis();
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum+=i;
        }
        System.out.println("sum = " + sum);
        System.out.println(System.currentTimeMillis()-start);

    }
}
