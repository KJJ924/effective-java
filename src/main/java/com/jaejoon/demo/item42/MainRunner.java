package com.jaejoon.demo.item42;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainRunner {

  public static void main(String[] args) {
    List<String> word  = new ArrayList<>();
    word.add("abasdasdd");
    word.add("asd");
    word.add("asdadsad");
    Collections.sort(word, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return Integer.compare(o1.length(),o2.length());
      }
    });
    System.out.println("word = " + word);

    //Collections.sort(word,(s1,s2) -> Integer.compare(s1.length(),s2.length()));
  }

}
