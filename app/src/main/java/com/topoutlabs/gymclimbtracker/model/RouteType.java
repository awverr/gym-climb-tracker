package com.topoutlabs.gymclimbtracker.model;

/**
 * Created by aubry on 2/24/17.
 */

public enum RouteType {

    BOULDER("Boulder"),
    TOP_ROPE("Top Rope"),
    LEAD("Lead");

    private String text;

    RouteType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static RouteType fromString(String text){
        RouteType enumValue = null;

        switch(text) {
            case "Boulder":  enumValue = BOULDER;
                break;
            case "Top Rope":  enumValue = TOP_ROPE;
                break;
            case "Lead":  enumValue = LEAD;
                break;
        }
        return enumValue;
    }
}
