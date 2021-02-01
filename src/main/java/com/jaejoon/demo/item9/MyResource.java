package com.jaejoon.demo.item9;

public class MyResource implements AutoCloseable{

    public void run(){
        System.out.println("프로그램이 실행됨.");
        throw new RuntimeException("강제종료");
    }


    @Override
    public void close(){
        System.out.println("프로그램이 종료하는중");
        throw new RuntimeException("종료하는 도중 문제발생");
    }
}
