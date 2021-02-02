package com.jaejoon.demo.item10;

import java.awt.*;

public class ColorPoint extends Point{

    private final Color color;
    public ColorPoint(int x, int y ,Color color) {
        super(x, y);
        this.color = color;
    }
}
