package com.awverret.gymclimbtracker.model;

/**
 * Created by aubry on 3/15/2017.
 */

public enum RouteWall {

    WELCOME_WALL("Welcome Wall"),
    LEFT_CHEEK("Left Cheek"),
    RIGHT_CHEEK("Right Cheek"),
    BOULDERING_SLABS("Bouldering Slabs"),
    TEN_DEGREE_WALL("10 Degree Wall"),
    PROW("Prow"),
    LEARNING_CENTER("Learning Center"),
    ALCOVE("Alcove"),
    STORAGE_SLABS("Storage Slabs"),
    TOWER("Tower"),
    GALLERY("Gallery"),
    TROPHY_WALL("Trophy Wall"),
    HOLIDAY_WALL("Holiday Wall"),
    CONSTANT_WALL("Constant Wall"),
    CRYSTAL("Crystal"),
    MARATHON_WALL("Marathon Wall"),
    DOUBLE_WAVE("Double Wave"),
    SUN_WALL("Sun Wall"),
    MEGA_SLAB("Mega Slab"),
    CROWS_NEST("Crows Nest"),
    CRACK_WALL("Crack Wall"),
    ICE_BOX("Ice Box"),
    BELLY("Belly"),
    WAVE("Wave"),
    CORNER_COPIA("Corner Copia");

    private String text;

    RouteWall(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static RouteWall fromString(String text){
        RouteWall enumValue = null;

        switch(text) {
            case "Welcome Wall":  enumValue = WELCOME_WALL;
                break;
            case "Left Cheek":  enumValue = LEFT_CHEEK;
                break;
            case "Right Cheek":  enumValue = RIGHT_CHEEK;
                break;
            case "Bouldering Slabs":  enumValue = BOULDERING_SLABS;
                break;
            case "10 Degree Wall":  enumValue = TEN_DEGREE_WALL;
                break;
            case "Prow":  enumValue = PROW;
                break;
            case "Learning Center":  enumValue = LEARNING_CENTER;
                break;
            case "Alcove":  enumValue = ALCOVE;
                break;
            case "Storage Slabs":  enumValue = STORAGE_SLABS;
                break;
            case "Tower":  enumValue = TOWER;
                break;
            case "Gallery":  enumValue = GALLERY;
                break;
            case "Trophy Wall":  enumValue = TROPHY_WALL;
                break;
            case "Holiday Wall":  enumValue = HOLIDAY_WALL;
                break;
            case "Constant Wall":  enumValue = CONSTANT_WALL;
                break;
            case "Crystal":  enumValue = CRYSTAL;
                break;
            case "Marathon Wall":  enumValue = MARATHON_WALL;
                break;
            case "Double Wave":  enumValue = DOUBLE_WAVE;
                break;
            case "Sun Wall":  enumValue = SUN_WALL;
                break;
            case "Mega Slab":  enumValue = MEGA_SLAB;
                break;
            case "Crows Nest":  enumValue = CROWS_NEST;
                break;
            case "Crack Wall":  enumValue = CRACK_WALL;
                break;
            case "Ice Box":  enumValue = ICE_BOX;
                break;
            case "Belly":  enumValue = BELLY;
                break;
            case "Wave":  enumValue = WAVE;
                break;
            case "Corner Copia":  enumValue = CORNER_COPIA;
                break;
        }
        return enumValue;
    }
}
