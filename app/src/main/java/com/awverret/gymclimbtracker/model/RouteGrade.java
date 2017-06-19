package com.awverret.gymclimbtracker.model;

/**
 * Created by aubry on 6/6/17.
 */

public enum RouteGrade {
    FIVE_FIVE("5.5"),
    FIVE_SIX("5.6"),
    FIVE_SEVEN("5.7"),
    FIVE_EIGHT("5.8"),
    FIVE_NINE("5.9"),
    FIVE_TEN_A("5.10a"),
    FIVE_TEN_B("5.10b"),
    FIVE_TEN_C("5.10c"),
    FIVE_TEN_D("5.10d"),
    FIVE_ELEVEN_A("5.11a"),
    FIVE_ELEVEN_B("5.11b"),
    FIVE_ELEVEN_C("5.11c"),
    FIVE_ELEVEN_D("5.11d"),
    FIVE_TWELVE_A("5.12a"),
    FIVE_TWELVE_B("5.12b"),
    FIVE_TWELVE_C("5.12c"),
    FIVE_TWELVE_D("5.12d"),
    FIVE_THIRTEEN_A("5.13a"),
    FIVE_THIRTEEN_B("5.13b"),
    FIVE_THIRTEEN_C("5.13c"),
    FIVE_THIRTEEN_D("5.13d"),
    FIVE_FOURTEEN("5.14"),
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

    RouteGrade(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static RouteGrade fromString(String text){
        RouteGrade enumValue = null;

        switch(text) {
            case "5.5":  enumValue = FIVE_FIVE;
                break;
            case "5.6":  enumValue = FIVE_SIX;
                break;
            case "5.7":  enumValue = FIVE_SEVEN;
                break;
            case "5.8":  enumValue = FIVE_EIGHT;
                break;
            case "5.9":  enumValue = FIVE_NINE;
                break;
            case "5.10a":  enumValue = FIVE_TEN_A;
                break;
            case "5.10b":  enumValue = FIVE_TEN_B;
                break;
            case "5.10c":  enumValue = FIVE_TEN_C;
                break;
            case "5.10d":  enumValue = FIVE_TEN_D;
                break;
            case "5.11a":  enumValue = FIVE_ELEVEN_A;
                break;
            case "5.11b":  enumValue = FIVE_ELEVEN_B;
                break;
            case "5.11c":  enumValue = FIVE_ELEVEN_C;
                break;
            case "5.11d":  enumValue = FIVE_ELEVEN_D;
                break;
            case "5.12a":  enumValue = FIVE_TWELVE_A;
                break;
            case "5.12b":  enumValue = FIVE_TWELVE_B;
                break;
            case "5.12c":  enumValue = FIVE_TWELVE_C;
                break;
            case "5.12d":  enumValue = FIVE_TWELVE_D;
                break;
            case "5.13a":  enumValue = FIVE_THIRTEEN_A;
                break;
            case "5.13b":  enumValue = FIVE_THIRTEEN_B;
                break;
            case "5.13c":  enumValue = FIVE_THIRTEEN_C;
                break;
            case "5.13d":  enumValue = FIVE_THIRTEEN_D;
                break;
            case "5.14":  enumValue = FIVE_FOURTEEN;
                break;
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
