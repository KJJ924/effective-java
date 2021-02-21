package com.jaejoon.demo.item42;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CARDTest {

  @Test
  void test(){
    for (CARD card : CARD.values()) {
      int point = card.point(1000);
      System.out.println("1000원을 결제하면 "+card.getName() +"의 포인트 적립금은"+point+"원");
    }
  }
}