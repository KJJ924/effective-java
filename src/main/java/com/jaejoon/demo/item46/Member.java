package com.jaejoon.demo.item46;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Member {

  private Long classNumber;
  private String name;

  public Member(Long classNumber, String name) {
    this.classNumber = classNumber;
    this.name = name;
  }

  public Long getClassNumber() {
    return classNumber;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Member{" +
        "classNumber=" + classNumber +
        ", name='" + name + '\'' +
        '}';
  }

  public static void main(String[] args) {
    List<Member> memberList = new ArrayList<>();
    for (Long i = 0L; i < 10; i++) {
      memberList.add(new Member(10L,"kjj"));
    }

    Map<Character, List<Member>> collect1 = memberList.stream()
        .collect(Collectors.groupingBy(member -> member.getName().charAt(0)));

    System.out.println("collect = " + collect1);
  }

}
