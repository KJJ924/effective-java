package com.jaejoon.demo.item64;

import com.jaejoon.demo.Item55.Member;
import java.util.Optional;

public class RDBMemberRepo implements Repository<Member> {

    //TODO DB 커넥션 연결함.

    @Override
    public Member save(Member member) {
        //TODO : 저장 했음
        return null;
    }

    @Override
    public Optional<Member> get(Long id) {
        //TODO : DB 검색해서 가져옴
        return Optional.empty();
    }
}
