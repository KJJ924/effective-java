package com.jaejoon.demo.item16;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Item16Test {

    @Test
    void userExceptionTest(){
        User user = new User();

        assertThatThrownBy(() -> user.setAge(-10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("음수값을 가질 수 없습니다");
    }

    @Test
    void userPhone(){
        User user = new User();
        assertThatThrownBy(()->user.setPhone("010"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("11자리수를 맞춰주세요");
    }

    @Test
    void userGetPhone(){
        User user = new User();
        user.setPhone("01000000000");

        assertThat(user.getPhone()).isEqualTo("010-0000-0000");
    }
}