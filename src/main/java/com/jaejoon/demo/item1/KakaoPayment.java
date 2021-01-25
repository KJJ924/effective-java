package com.jaejoon.demo.item1;

public class KakaoPayment implements Payment {
    @Override
    public void print() {
        System.out.println("카카오카드 결제");
    }
}
