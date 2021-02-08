package com.jaejoon.demo.item14;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AppleTest {

    @Test
    void compareTest(){
        List<Apple> apples = new ArrayList<>();

//        for (int i = 0; i <10; i++) {
//            apples.add(new Apple(i,Color.RED,"variety"));
//
//       }
        apples.add(new Apple(1,Color.RED,"bsd"));

        apples.add(new Apple(1,Color.BLUE,"asd"));
        System.out.println(apples);
        apples.sort(Comparator.comparingInt(Apple::getWeight).thenComparing(Apple::getVariety));
        System.out.println(apples);
    }

    @Test
    void compareToCollectionTest(){
        Set<BigDecimal> treeSet = new TreeSet<>();
        Set<BigDecimal> hashSet = new HashSet<>();
        BigDecimal decimal = new BigDecimal("1.0");
        BigDecimal decimal1 = new BigDecimal("1.00");

        treeSet.add(decimal);
        treeSet.add(decimal1);

        hashSet.add(decimal);
        hashSet.add(decimal1);

        // TreeSet 은  compareTo 를 이용
        assertThat(treeSet.size()).isEqualTo(1);

        // HashSet 은 equals() 를 이용
        assertThat(hashSet.size()).isEqualTo(2);
    }
}