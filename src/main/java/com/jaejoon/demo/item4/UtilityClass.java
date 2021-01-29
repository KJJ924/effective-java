package com.jaejoon.demo.item4;


//인스턴스화를 막으려거든 private 생성자를 사용하라
public class UtilityClass {

    //생성자가 있지만 호출할 수 없는 생성자이기 때문에
    //직관적이지 않다. 그래서 다음과 같이 의미를 나타내는 아래처럼 주석을 쓰자.
    //기본 생성자가 만들어지는 것을 막는다 (인스턴스 방지용).
    private UtilityClass(){ }

    static void hello(String name){
        System.out.println("hello"+name);
    }
}
