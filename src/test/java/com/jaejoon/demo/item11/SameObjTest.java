package com.jaejoon.demo.item11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SameObjTest {

    @Test
    void hashCode_only_41(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<SameObj,Integer> sameObj= IntStream.range(1,1_000)
                .mapToObj(SameObj::new)
                .collect(Collectors.toMap(sameObj1 -> sameObj1,SameObj::getId));

        sameObj.get(new SameObj(100));
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

    }

    @Test
    void hashCode_not_override(){
        SameObj sameObjTest = new SameObj(1);
        Map<SameObj,Integer> sameObjIntegerMap = new HashMap<>();
        sameObjIntegerMap.put(sameObjTest, sameObjTest.getId());

        assertThat(sameObjTest).isEqualTo(new SameObj(1));
        assertThat(sameObjIntegerMap.get(new SameObj(1))).isNotNull();
    }
}