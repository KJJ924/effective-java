package com.jaejoon.demo.item83;

import com.jaejoon.demo.Item55.Member;

public class TestClass {
//    private final Member member = createMember();

    private volatile Member member;

    private Member createMember() {
        return new Member("KJJ");
    }

    public Member getMember() {
        Member result = member;
        if (result == null) {
            member = result = createMember();
        }
        return result;

    }

    @Override
    public String toString() {
        return "TestClass{" +
            "member=" + member +
            '}';
    }
}
