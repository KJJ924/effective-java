package com.jaejoon.demo.item1;

public class SamSungPayment implements Payment{
    @Override
    public void print() {
        System.out.println("삼성카드 결제");
    }
}
