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
}
