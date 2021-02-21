package com.jaejoon.demo.item42;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainRunner {
  public int value =10;

  Foo foo = new Foo() {
    final int value =10;
    @Override
    public int anyThing(int i) {
      return i+ this.value;
    }
  };

  Foo foo2 = i -> i+this.value;


  public static void main(String[] args) {
    List<String> word  = new ArrayList<>();
    word.add("ABCDE");
    word.add("AB");
    word.add("ABC");
    Collections.sort(word, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        System.out.println(this);
        return Integer.compare(o1.length(),o2.length());
      }
    });


    Collections.sort(word,(s1,s2) ->String.CASE_INSENSITIVE_ORDER.compare(s1,s2));
    System.out.println("word = " + word);

    Foo foo= new Foo() {
      final int value =10;
      @Override
      public int anyThing(int i) {
        return i+ this.value;
      }
    };
  }

}
