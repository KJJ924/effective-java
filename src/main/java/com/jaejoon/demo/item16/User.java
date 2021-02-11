package com.jaejoon.demo.item16;

public class User { //public class 일때.

    private String name; //해당 클래스의 필드의 맴버를 private 으로 감추자.
    private int age;
    private String phone;


    public String getName() { // getter 메서드를 통해 데이터를 주자.
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        //기존 저장형식이 01012345678 인 경우
        // 클라이언트에게 넘겨줄때는 010-0000-0000으로 넘겨줄수있다.
        return this.phone.replaceFirst("(^[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    }

    public void setName(String name) {
        this.name = name;
    }

    // setter 를 쓰게되면 불변객체로 보장을 해주지못한다는데..?
    // 하지만 제공하지 않아도 얼마든지  클라이언트 측에서 리플렉션을 이용하여 얼마든지 조작이가능하긴한데?
    // 해당 주제에서는 벗어나니 일단지나가자.
    public void setAge(int age) {
        if(age<0) {
            throw new IllegalArgumentException("음수값을 가질 수 없습니다"); //해당코드 처럼 불변식을 보장해줄수 있다
        }
        this.age = age;
    }

    public void setPhone(String phone) {
        if(!phone.matches("[0-9]{11}")){
            throw new IllegalArgumentException("11자리수를 맞춰주세요"); //간단하게 자리수 만 확인..
        }
        this.phone = phone;
    }
}
