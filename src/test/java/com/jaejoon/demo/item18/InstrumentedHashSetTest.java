package com.jaejoon.demo.item18;

import com.jaejoon.demo.item20.IPhone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class InstrumentedHashSetTest {

    @Test
    @DisplayName("일반 add 메서드를 호출했을때는 정상적으로 작동한다.")
    void addValue(){
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        s.add("string");
        s.add("String 1");
        s.add("String 2");
        assertThat(s.getCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("addAll 메서드를 호출하면 내부적으로 add을 또 호출하여 중복으로 count 가 된다.")
    void addAllCount(){
        InstrumentedHashSet<String> s  = new InstrumentedHashSet<>();

        s.addAll(List.of("String","String2","String3"));

        assertThat(s.getCount()).isNotEqualTo(3); //result value =6 expect = 3

    }


    @Test
    @DisplayName("컴포지션 패턴을 이하여 기존 발생한 문제를 해결한다.")
    void addAllCount_success(){
        CountingSet<String> s  = new CountingSet<>(new HashSet<>());

        s.addAll(List.of("String","String2","String3"));

        assertThat(s.getCount()).isEqualTo(3);

    }

    @Test
    @DisplayName("Set 인터페이스 의 구현체에 대해서 count 기능을 이용할수 있다")
    void test(){
        CountingSet<String> s = new CountingSet<>(new HashSet<>());
        CountingSet<String> t = new CountingSet<>(new TreeSet<>());

        s.addAll(List.of("String","String2","String3"));
        t.addAll(List.of("String","String2","String3"));

        assertThat(s.getCount()).isEqualTo(3);
        assertThat(t.getCount()).isEqualTo(3);
    }

}