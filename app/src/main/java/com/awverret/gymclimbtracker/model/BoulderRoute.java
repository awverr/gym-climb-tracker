package com.awverret.gymclimbtracker.model;

import java.util.Date;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 3/1/17.
 */

public class BoulderRoute extends Route {
    String id;
    BoulderGrade boulderGrade;
    RouteColor color;
    RouteWall wall;
    String name;
    RouteSetter setter;
    //Date setDate;
    long setDate;

    public BoulderRoute() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public BoulderRoute(BoulderGrade boulderGrade, String name, RouteSetter setter, RouteColor color, RouteWall wall, Long setDate) {
        id = randomUUID().toString();
        this.boulderGrade = boulderGrade;
        this.name = name;
        this.setter = setter;
        this.color = color;
        this.wall = wall;
        this.setDate = setDate;
    }

    public BoulderGrade getBoulderGrade() {
        return boulderGrade;
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

    public Long getSetDate() {return setDate;}

    public String getId() {return id;}
}
