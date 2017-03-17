package com.awverret.gymclimbtracker.model;

/**
 * Created by aubry on 3/15/2017.
 */

public enum RouteSetter {
    SMASH("Smash(Walker E.)"),
    DH("DH(Danny H.)"),
    MONKEY_FACE("Monkey Face(Ronaldo E.)"),
    MEERKAT(">Meerkat(Ford M.)"),
    JD("JD(John D.)"),
    CAM("Cam(Cameron C.)");

    private String text;

    RouteSetter(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
