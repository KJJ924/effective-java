package com.jaejoon.demo.item64;

import com.jaejoon.demo.Item55.Member;
import java.util.Optional;

public class MemberService {

    private final Repository<Member> repo;

    public MemberService(Repository<Member> repo) {
        this.repo = repo;
    }

    public Member save(Member member){
        return repo.save(member);
    }

    public Member getMember(Long id){
        Optional<Member> member = repo.get(id);
        return member.orElseThrow(IllegalArgumentException::new);
    }
}
