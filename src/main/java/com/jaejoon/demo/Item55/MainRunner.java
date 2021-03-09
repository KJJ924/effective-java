package com.jaejoon.demo.Item55;

import static com.jaejoon.demo.Item55.MemberRepository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

public class MainRunner {
    public static Member getCommon(){
        System.out.println("실행됨");
        return Member.of("사용자");
    }
    public static void main(String[] args) {

        Optional<Member> opMember = MemberRepository.optionalFindByKey(1L);
        if(opMember.isPresent()){
            System.out.println(opMember.get());
        }else{
            System.out.println("없는 객체입니다.");
        }

        List<String> emptyList = new ArrayList<>();
        Optional<List<String>> list = Optional.of(emptyList);
        if(list.isPresent()){
            System.out.println("빈 리스트이지만 사용가능 객체인가??");
        }else {
            System.out.println("빈 리스트이지만 사용 불가능 객체인가??");
        }

        // 따라하지마세요
        Optional<Integer> integer = Optional.of(10);
        Optional<String> string = Optional.of("String");

        OptionalInt optionalInt = OptionalInt.of(10);
        OptionalLong optionalLong = OptionalLong.of(10L);
    }
}
