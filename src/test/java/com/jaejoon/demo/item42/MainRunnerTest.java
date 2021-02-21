package com.jaejoon.demo.item42;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainRunnerTest {

  @Test
  @DisplayName("자바8 이전에 사용하던 익명클래스")
  void anonymousClass() {
    List<String> word = new ArrayList<>(List.of("ab","abc","a"));

    Collections.sort(word, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return Integer.compare(o1.length(), o2.length());
      }
    });

  }

  @Test
  @DisplayName("람다 표현식 사용")
  void lambdaExpression(){
    List<String> word = new ArrayList<>(List.of("ab","abc","a"));

    Collections.sort(word,(String s1,String s2)-> Integer.compare(s1.length(),s2.length()));
  }

//  @Test
//  @DisplayName("로 타입 List 임으로 타입추론 불가.")
//  void compileError(){
//    List word = new ArrayList(List.of("ab","abc","a"));
//
//    Collections.sort(word,(s1, s2) -> Integer.compare(s1.length(),s2.length()));
//  }
}