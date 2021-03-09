package com.jaejoon.demo.Item55;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class MemberRepository {

    private static final Map<Long,Member> REPO = new HashMap<>();

    private static Long genKey = 1L;

    public static void save(Member member){
        REPO.put(genKey,member);
        genKey++;
    }

    public static Optional<Member> optionalFindByKey(Long key){
        if(REPO.containsKey(key)){
            return Optional.of(REPO.get(key));
        }
        return  Optional.empty();
    }

    public static Member exceptionFindByKey(Long key){
        Member member = REPO.get(key);

        if(Objects.isNull(member)){
            throw new IllegalArgumentException();
        }
        return member;
    }

    public static Member nullFindByKey(Long key){
        return REPO.get(key);
    }

}
