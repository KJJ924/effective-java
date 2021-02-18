package com.jaejoon.demo.item21;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.collection.SynchronizedCollection;

import java.util.ArrayList;
import java.util.List;

public class Item21 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("test");
        list.add("test2");
        list.add("java Spring");
        list.add("java Spring Data JPA");

        SynchronizedCollection<String> sc = SynchronizedCollection.synchronizedCollection(list);

        Predicate<String> startJava =  s-> s.startsWith("java");
        sc.remove("test"); //SynchronizedCollection 가 재정의한 remove() 메서드 호출
        sc.removeIf(startJava::evaluate); // Collection 에 정의된 removeIf() 디폴트 메서드  호출

    }
}
