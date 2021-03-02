package com.jaejoon.demo.item46;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainRunner {

  public static void main(String[] args) {
    File file = new File("src/main/java/com/jaejoon/demo/item46/test.txt");

    Map<String ,Long> freq = new HashMap<>();
    try(Stream<String> words = new Scanner(file).tokens()) {
      freq = words.collect(Collectors.groupingBy(String::toLowerCase,Collectors.counting()));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


    List<String> lowerList = new ArrayList<>(List.of("a","b","c"));

    List<String> upperList = lowerList.stream().map(String::toUpperCase).collect(toList());

    List<String> topTen = freq.keySet().stream()
        .sorted(comparing(freq::get).reversed())
        .limit(10)
        .collect(Collectors.toList());
    System.out.println("topTen = " + topTen);
    System.out.println("upperList = " + upperList);
  }
}
