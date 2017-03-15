package com.awverret.gymclimbtracker.model;

/**
 * Created by aubry on 3/15/2017.
 */

public enum RouteWall {

    WELCOME_WALL("welcome wall"),
    LEFT_CHEEK("left cheek"),
    RIGHT_CHEEK("right cheek"),
    BOULDERING_SLABS("bouldering_slabs"),
    TEN_DEGREE_WALL("10 degree wall"),
    PROW("prow"),
    LEARNING_CENTER("learning center"),
    ALCOVE("alcove"),
    STORAGE_SLABS("storage slabs"),
    TOWER("tower"),
    GALLERY("gallery"),
    TROPHY_WALL("trophy wall"),
    HOLIDAY_WALL("holiday wall"),
    CONSTANT_WALL("constant wall"),
    CRYSTAL("crystal"),
    MARATHON_WALL("marathon wall"),
    DOUBLE_WAVE("double wave"),
    SUN_WALL("sun wall"),
    MEGA_SLAB("mega slab"),
    CROWS_NEST("crows nest"),
    CRACK_WALL("crack wall"),
    ICE_BOX("ice box"),
    BELLY("belly"),
    WAVE("wave"),
    CORNER_COPIA("corner copia");

    private String text;

    RouteWall(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
