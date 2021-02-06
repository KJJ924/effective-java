package com.jaejoon.demo.item13;

import com.jaejoon.demo.item10.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserCloneTest {

    @Test
    void userClone() throws CloneNotSupportedException {
        UserClone userClone = new UserClone("name" ,new Info(19,"seoul"));
        UserClone clone = userClone.clone();
        Info info = clone.getInfo();
        info.setAge(10);

        userClone.setName("editName");
//        assertThat(clone.getName()).isEqualTo(userClone.getName());
        assertThat(clone).isNotEqualTo(userClone);
        assertThat(clone.getName()).isEqualTo("name");

    }

}