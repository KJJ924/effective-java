package com.jaejoon.demo.item13;

import java.io.Closeable;

// Cloneable 인터페이스내에 따로 구현체는 없지만 Object .clone 메서드에서 확인함.
// 구현하지않으면 예외던지기 때문에 복사하기위해선 해당 인터페이스를 구현하고 있어야함
// 단 구현하고 있다해서 무조건 사용가능하다는 뜻은 아니다.
public class UserClone implements Cloneable {
    private String name;
    private Info  info;


    public UserClone(String name, Info info) {
        this.name = name;
        this.info = info;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public UserClone clone()  {
        try {  //굳이 외부 클라이언트에 예외처리를 던지지말자. 외부클라이언트에서는 해줄 수 있는 처리가없다.
            // 원래라면 cloneNotSupportedException 이 체크예외가아닌 언체크드예외로 설계하는것이 맞다고 책에선 말하고있다
            UserClone clone = (UserClone) super.clone();
            clone.info = clone.info.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
           throw new AssertionError();
        }
    }
}
