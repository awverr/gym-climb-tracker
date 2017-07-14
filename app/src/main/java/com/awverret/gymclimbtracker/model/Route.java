package com.awverret.gymclimbtracker.model;

import java.util.Date;

import static com.awverret.gymclimbtracker.util.Utils.createRouteName;
import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 2/24/17.
 */

public class Route {

    public RouteType type;
    public String id;
    public RouteGrade routeGrade;
    public RouteColor color;
    public RouteWall wall;
    public String name;
    public RouteSetter setter;
    //Date setDate;
    public long setDate;


    public Route() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Route(RouteType type, RouteGrade routeGrade, String name, RouteSetter setter, RouteColor color, RouteWall wall, long setDate) {
        this.type = type;
        id = randomUUID().toString();
        this.routeGrade = routeGrade;
        this.setter = setter;
        this.color = color;
        this.wall = wall;
        this.setDate = setDate;

        if(name == null) {
            this.name = createRouteName();
        }else {
            this.name = name;
        }
    }

    public String createRouteName(){

        return wall.getText() + "/ " + color.getText() + "/ " + routeGrade.getText();
    }

    public RouteType getType() {return type;}

    public String getId() {return id;}

    public RouteGrade getRouteGrade() {
        return routeGrade;
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
        return "Route{" +
                "id='" + id + '\'' +
                '}';
    }
}
