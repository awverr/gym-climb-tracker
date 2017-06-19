package com.topoutlabs.gymclimbtracker.model;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 3/1/17.
 */

public class BoulderRoute extends Route {

    public String id;
    public BoulderGrade boulderGrade;
    public RouteColor color;
    public RouteWall wall;
    public String name;
    public RouteSetter setter;
    //Date setDate;
    public long setDate;

    public BoulderRoute() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public BoulderRoute(BoulderGrade boulderGrade, String name, RouteSetter setter, RouteColor color, RouteWall wall, long setDate) {
        id = randomUUID().toString();
        this.boulderGrade = boulderGrade;
        this.name = name;
        this.setter = setter;
        this.color = color;
        this.wall = wall;
        this.setDate = setDate;
    }

    public String getId() {return id;}

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

    public long getSetDate() {return setDate;}

    @Override
    public String toString() {
        return "BoulderRoute{" +
                "id='" + id + '\'' +
                '}';
    }
}
