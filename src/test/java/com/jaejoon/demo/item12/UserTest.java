package com.jaejoon.demo.item12;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void userInstance(){
        User user = new User("JaeJoon",99);
        System.out.println(user);

        assertThat(user).isNull();
    }

}