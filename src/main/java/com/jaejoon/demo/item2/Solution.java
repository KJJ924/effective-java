package com.jaejoon.demo.item2;

import com.jaejoon.demo.item2.Item2;

public class Solution {
    public static void main(String[] args) {
        Item2.User user = new Item2.User.Builder("재준", 10).local("서울").phone("010").build();
        System.out.println(user);

        NYPizza build = new NYPizza.Builder(NYPizza.Size.LARGE)
                .addTopping(Pizza.Topping.HAM)
                .build();

    }
}
