package com.topoutlabs.gymclimbtracker.model;

/**
 * Created by aubry on 3/14/2017.
 */

public enum RouteColor {
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

    RouteColor(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static RouteColor fromString(String text){
        RouteColor enumValue = null;

        switch(text) {
            case "Black":  enumValue = BLACK;
                break;
            case "White":  enumValue = WHITE;
                break;
            case "Tan":  enumValue = TAN;
                break;
            case "Orange":  enumValue = ORANGE;
                break;
            case "Red":  enumValue = RED;
                break;
            case "Purple":  enumValue = PURPLE;
                break;
            case "Blue":  enumValue = BLUE;
                break;
            case "Green":  enumValue = GREEN;
                break;
            case "Yellow":  enumValue = YELLOW;
                break;
        }
        return enumValue;
    }

}
