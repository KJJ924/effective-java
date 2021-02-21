package com.jaejoon.demo.item42;

import com.jaejoon.demo.item1.Payment;
import java.util.function.Function;
import org.springframework.context.annotation.EnableMBeanExport;

public enum CARD {

  KAKAO("카카오",money->{
   return money/100;
  });


  public final String name;
  private final Function<Integer, Integer> op;
  CARD(String name ,Function<Integer,Integer> op) {
    this.name = name;
    this.op =op;
  }
  public int point(int money){
    return op.apply(money);
  }
  public String getName() {
    return name;
  }
}
