package com.jaejoon.demo.item10;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
    private Long Id; // 해당 값이 같으면 같은 객체로본다

    public User() {
    }

    public User(Long id) {
        Id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(Id, user.Id);
    }


    public static void main(String[] args) {
        User x = new User();
        User y = new User();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            x.equals(y); // 반복중 ture 이거나 false 항상 같은 값을 반환해야함.
        }
    }
}
