package com.jaejoon.demo.item1 ;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//생성자 대신 정적 팩터리 메서드(static factory method) 를 고려하라
public class Item1 {

    static class User{
        private String name;

        private static final User ADMIN = new User("admin");
        private static final User TEST_USER = new User("testUser");

        public User() { }

        //User 를 생성하는 생성자
        public User(String name) {
            this.name = name;
        }


        static User Name(String name){
            User user = new User();
            user.name =name;
            return user;
        }

        static User getAdmin(){
            return ADMIN;
        }
        static User getTestUser(){
            return TEST_USER;
        }
    }

    static class CARD{
        public static final String SAMSUNG_CARD = "삼성";
        public static final String KAKAO_CARD="카카오";
        public static final String TOSS_CARD ="토스";

        static Payment payment(String card){
            switch (card){
                case SAMSUNG_CARD:
                    // 3. 반환 타입의 하위타입 객체를 반환할 수 있는 능력이 있다.
                    return  new SamSungPayment();
                case KAKAO_CARD:
                    return new KakaoPayment();
                case TOSS_CARD:
                    Payment toss = null;
                    try {
                        // 5. 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도된다.
                        Constructor<?> tossConstructor = Class.forName("FQCN(Toss)").getConstructor();
                        toss  = (Payment) tossConstructor.newInstance();
                    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                            | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return toss;

            }
            throw new IllegalArgumentException();
        }

    }
    public static void main(String[] args) {
        //생성자에 넘기는 매개변수와 생성자 자체만으로는 반환될 특성을 설명하지못함.
        User user = new User("JaeJoon");

        // 1. 이름을 가질 수 있다. 반활될 객체의 특성을 설명할 수 있음
        User jaeJoon = User.Name("JaeJoon");

        /*
            2. 호출될 때마다 인스턴스를 새로 생성하지 않아도된다.
           -> 장점: 불필요한 객체의 생성을 피할 수 있다. / 객체가 자주요청되는 상황이라면 성능을 향상시킬 수 있음.
       */
        User admin = User.getAdmin();
        User testUser = User.getTestUser();



        // 4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할수 있다.
        Payment kakao = CARD.payment(CARD.KAKAO_CARD);
        kakao.print();
        Payment samsung = CARD.payment(CARD.SAMSUNG_CARD);
        samsung.print();

        // 5. 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도된다.
        Payment toss = CARD.payment(CARD.TOSS_CARD);
        toss.print();



    }
}
