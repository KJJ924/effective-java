package com.jaejoon.demo.item75;

import java.util.Arrays;

/**
 * @author dkansk924@naver.com
 * @since 2021/07/17
 */
public class Member {

    private final String nickname;
    private final String name;
    private final int age;

    private Member(String nickname, String name, int age) {
        this.nickname = nickname;
        this.name = name;
        this.age = age;
    }

    public static Member create(String nickname, String name, int age) {
        nicknameValidation(nickname);
        return new Member(nickname, name, age);
    }

    private static void nicknameValidation(String nickname) {
        char[] chars = nickname.toCharArray();

        for (char aChar : chars) {
            if (!Character.isUpperCase(aChar)) {
                ErrorResponse response = new ErrorResponse("대문자만 허용합니다", "nickName", nickname);
                throw new RuntimeException(response.toString());
            }
        }
    }


}
