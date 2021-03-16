package com.jaejoon.demo.item64;

import com.jaejoon.demo.Item55.Member;
import java.util.HashMap;
import java.util.Optional;

public class MemoryMemberRepo implements Repository<Member> {

    private static final HashMap<Long ,Member> memoryDB = new HashMap<>();
    private static Long key = 1L;

    @Override
    public Member save(Member member) {
        memoryDB.put(key,member);
        key++;
        return member;
    }

    @Override
    public Optional<Member> get(Long id) {
        if(memoryDB.containsKey(key)){
            return Optional.of(memoryDB.get(key));
        }
        return Optional.empty();
    }
}
