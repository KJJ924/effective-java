package com.jaejoon.demo.item13;

import java.io.Closeable;

// Cloneable 인터페이스내에 따로 구현체는 없지만 Object .clone 메서드에서 확인함.
// 구현하지않으면 예외던지기 때문에 복사하기위해선 해당 인터페이스를 구현하고 있어야함
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
    protected UserClone clone() throws CloneNotSupportedException {
        UserClone clone = (UserClone) super.clone();
        clone.info = clone.info.clone();
        return clone;
    }
}
