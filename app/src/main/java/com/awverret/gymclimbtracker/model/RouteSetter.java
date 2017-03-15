package com.awverret.gymclimbtracker.model;

/**
 * Created by aubry on 3/15/2017.
 */

public enum RouteSetter {
    SMASH("Walker E."),
    DH("Danny H."),
    MONKEY_FACE("Ronaldo E."),
    MEERKAT("Ford M."),
    JD("John D."),
    CAM("Cameron C.");

    private String text;

    RouteSetter(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
