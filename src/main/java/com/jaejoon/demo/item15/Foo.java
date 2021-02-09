package com.jaejoon.demo.item15;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Foo {

    private static final String[] fooList = {"foo1","foo2","foo3"};

    // 방법 1)불변 리스트를 제공해주자
    public static List<String> immutableList = Collections.unmodifiableList(List.of(fooList));

    // 방법 2)
    public static String[] copyList(){
        return fooList.clone();
    }
    public static String[] getFooList() {
        return fooList;
    }

    public static void main(String[] args) {
        String[] fooList = Foo.fooList;

        for (String s : fooList) {
            System.out.println(s);
        }

        Foo.fooList[1] ="change";

        for (String s : fooList) {
            System.out.println(s);
        }
    }
}
