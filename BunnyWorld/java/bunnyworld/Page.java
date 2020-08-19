package edu.stanford.cs108.bunnyworld;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Page {

    String name;
    ArrayList<Shape> shapes;

    Page(String name) {
        this.name = name;
        this.shapes = new ArrayList<Shape>();
    }

    public void draw(Canvas canvas) {
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }

    }
}
