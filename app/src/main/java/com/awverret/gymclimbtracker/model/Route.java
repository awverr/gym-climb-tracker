package com.awverret.gymclimbtracker.model;

import java.util.Date;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 2/24/17.
 */

public class Route {

    String id;
    RouteColor color;
    RouteWall wall;
    String name;
    RouteSetter setter;
    Date setDate;

    public Route() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Route(String name, RouteSetter setter, RouteColor color, RouteWall wall, Date setDate){
        id = randomUUID().toString();
        this.name = name;
        this.setter = setter;
        this.color = color;
        this.wall = wall;
        this.setDate = setDate;
    }

    public RouteSetter getSetter() {
        return setter;
    }

    public String getName() {
        return name;
    }

    public RouteColor getColor() {
        return color;
    }

    public RouteWall getWall() {
        return wall;
    }

    public String getId() {return id;}

    public Date getSetDate() {return setDate;}
}
