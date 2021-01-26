package com.jaejoon.demo.item2;

public class Item2 {
    static class User{
        private final String name;
        private final int age;
        private final String local;
        private final String phone;

        public static class Builder{
            private final String name;
            private final int age;

            private String local = "";
            private String phone = "";
            public Builder(String name, int age) {
                this.name = name;
                this.age = age;
            }
            public Builder local(String local){
                this.local=local;
                return this;
            }
            public Builder phone(String phone){
                this.phone = phone;
                return this;
            }
            public User build(){
                return new User(this);
            }
        }

        public User(Builder builder) {
            this.name = builder.name;
            this.age = builder.age;
            this.local = builder.local;
            this.phone = builder.phone;
        }

    }
}
