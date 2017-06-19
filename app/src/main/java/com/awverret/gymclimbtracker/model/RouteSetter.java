package com.awverret.gymclimbtracker.model;

/**
 * Created by aubry on 3/15/2017.
 */

public enum RouteSetter {
    SMASH("Smash"),
    DH("DH"),
    MONKEY_FACE("Monkey Face"),
    MEERKAT(">Meerkat"),
    JD("JD"),
    CAM("Cam"),
    MZ("MZ");

    private String text;

    RouteSetter(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static RouteSetter fromString(String text){
        RouteSetter enumValue = null;

        switch(text) {
            case "Smash":  enumValue = SMASH;
                break;
            case "DH":  enumValue = DH;
                break;
            case "Monkey Face":  enumValue = MONKEY_FACE;
                break;
            case "Meerkat":  enumValue = MEERKAT;
                break;
            case "JD":  enumValue = JD;
                break;
            case "Cam":  enumValue = CAM;
                break;
            case "MZ":  enumValue = MZ;
                break;
        }
        return enumValue;
    }
}
