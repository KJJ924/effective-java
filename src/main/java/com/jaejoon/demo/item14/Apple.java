package com.jaejoon.demo.item14;

import java.util.Comparator;

public class Apple implements Comparable<Apple> {

    private int weight;
    private Color color;
    private String variety;

    public Apple(int weight, Color color, String variety) {
        this.weight = weight;
        this.color = color;
        this.variety = variety;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    @Override
    public int compareTo(Apple o) {
        Comparator<Apple> comparator = Comparator.comparingInt(Apple::getWeight)
                .thenComparing(Apple::getColor)
                .thenComparing(Apple::getVariety);
        return comparator.compare(this,o);
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                ", color=" + color +
                ", variety='" + variety + '\'' +
                '}';
    }
}
