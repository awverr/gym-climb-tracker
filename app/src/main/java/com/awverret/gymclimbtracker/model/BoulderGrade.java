package com.awverret.gymclimbtracker.model;

/**
 * Created by aubry on 2/27/17.
 */

public enum BoulderGrade {
    V_ZERO("v0"),
    V_ONE("v1"),
    V_TWO("v2"),
    V_THREE("v3"),
    V_FOUR("v4"),
    V_FIVE("v5"),
    V_SIX("v6"),
    V_SEVEN("v7"),
    V_EIGHT("v8"),
    V_NINE("v9"),
    V_TEN("v10");

    private String text;

    BoulderGrade(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static BoulderGrade fromString(String text){
        BoulderGrade enumValue = null;

        switch(text) {
            case "v0":  enumValue = V_ZERO;
                break;
            case "v1":  enumValue = V_ONE;
                break;
            case "v2":  enumValue = V_TWO;
                break;
            case "v3":  enumValue = V_THREE;
                break;
            case "v4":  enumValue = V_FOUR;
             break;
            case "v5":  enumValue = V_FIVE;
                break;
            case "v6":  enumValue = V_SIX;
                break;
            case "v7":  enumValue = V_SEVEN;
                break;
            case "v8":  enumValue = V_EIGHT;
                break;
            case "v9":  enumValue = V_NINE;
                break;
            case "v10":  enumValue = V_TEN;
                break;
        }
        return enumValue;
    }
}
