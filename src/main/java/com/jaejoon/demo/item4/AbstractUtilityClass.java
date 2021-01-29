package com.jaejoon.demo.item4;

public abstract class AbstractUtilityClass {
    static void hello(String name){
        System.out.println("hello" + name);
    }

    public AbstractUtilityClass() {
        System.out.print("asd");
    }

    static class subClass extends AbstractUtilityClass{
        public subClass() {
        }
    }


    public static void main(String[] args) {

        // new 키워드를 이용한 인스턴스화 를 막을수는 있지만 ...??
        //AbstractUtilityClass utilityClass = new AbstractUtilityClass();

        //상속 받았음으로 AbstractUtilityClass 의 기본 생성자를 호출하게 됨으로 인스턴스가 생성이됨..
        subClass utilityClass1 =new subClass();
    }
}
