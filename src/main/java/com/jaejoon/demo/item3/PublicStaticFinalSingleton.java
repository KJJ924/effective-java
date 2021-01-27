package com.jaejoon.demo.item3;

public class PublicStaticFinalSingleton {

    public static final PublicStaticFinalSingleton INSTANCE  = new PublicStaticFinalSingleton();

    public static boolean status;

    private PublicStaticFinalSingleton() {
        if(status){
            throw new IllegalStateException("이 객체는 여러개의 인스턴스를 생성할수 없습니다 ");
        }
        status = true;
    }
}
