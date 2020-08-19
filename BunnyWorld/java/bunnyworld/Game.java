package edu.stanford.cs108.bunnyworld;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Game {

    String name;
    Page start_page;
    Page current_page;
    ArrayList<Page> pages;
    ArrayList<Shape> possessions;
    Set<String> pages_name_pool;
    Set<String> shapes_name_pool;
    boolean is_editting;

    public Game(String name) {
        this.name = name;
        this.start_page = new Page("Page1");
        this.current_page = this.start_page;
        this.pages = new ArrayList<Page>();
        this.pages.add(start_page);

        this.possessions = new ArrayList<Shape>();
        this.is_editting = true;

        this.pages_name_pool = new HashSet<String>();
        this.pages_name_pool.add("Page1");

        this.shapes_name_pool = new HashSet<String>();
    }

    void draw(Canvas canvas) {
        current_page.draw(canvas);
        for (Shape shape : this.possessions) {
            shape.draw(canvas);
        }
    }

    public void update_name_pool() {
        shapes_name_pool.clear();
        pages_name_pool.clear();

        for (Page page : pages) {
            pages_name_pool.add(page.name);
            for (Shape shape : page.shapes) {
                shapes_name_pool.add(shape.shape_name);
            }
        }
    }

}
