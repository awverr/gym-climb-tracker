package com.awverret.gymclimbtracker.model;

import java.util.Date;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 3/1/17.
 */

public class LeadRoute extends Route {


    RopeGrade ropeGrade;
    RouteColor color;
    RouteWall wall;
    String name;
    RouteSetter setter;
    Date setDate;

    public LeadRoute() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public LeadRoute(RopeGrade ropeGrade, String name, RouteSetter setter, RouteColor color, RouteWall wall, Date setDate) {
        this.ropeGrade = ropeGrade;
        this.name = name;
        this.setter = setter;
        this.color = color;
        this.wall = wall;
        this.setDate = setDate;
    }

    public String getId() {
        return id;
    }

    public RopeGrade getRopeGrade() {
        return ropeGrade;
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

    public Date getSetDate() {return setDate;}
}
