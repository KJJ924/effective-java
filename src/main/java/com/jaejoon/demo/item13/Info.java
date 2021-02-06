package com.jaejoon.demo.item13;

public class Info implements Cloneable{
    private int age;
    private String local;

    public Info(int age, String local) {
        this.age = age;
        this.local = local;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    protected Info clone() throws CloneNotSupportedException {
        return (Info)super.clone();
    }
}
