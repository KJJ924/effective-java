package com.jaejoon.demo.item13;

import com.jaejoon.demo.item10.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserCloneTest {

    @Test
    void userClone(){
        UserClone resource = new UserClone("name" ,new Info(19,"seoul"));
        UserClone resourceClone = resource.clone();
        Info info = resourceClone.getInfo(); // 클론객체의 info 객체를 가져와
        info.setAge(10);//값을 변경하면?

        assertThat(resource.getInfo().getAge()).isEqualTo(19); //원본 데이터의 값은 그대로 유지되어야한다.
    }

}