package com.awverret.gymclimbtracker.model;

/**
 * Created by aubry on 3/14/2017.
 */

public enum Color {
    BLACK("Black"),
    WHITE("White"),
    TAN("Tan"),
    ORANGE("Orange"),
    RED("Red"),
    PURPLE("Purple"),
    BLUE("Blue"),
    GREEN("Green"),
    YELLOW("Yellow");

    private String text;

    Color(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

}
