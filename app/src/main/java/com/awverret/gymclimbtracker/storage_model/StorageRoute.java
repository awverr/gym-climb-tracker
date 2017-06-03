package com.awverret.gymclimbtracker.storage_model;

import com.awverret.gymclimbtracker.model.BoulderGrade;
import com.awverret.gymclimbtracker.model.RopeGrade;
import com.awverret.gymclimbtracker.model.RouteColor;
import com.awverret.gymclimbtracker.model.RouteSetter;
import com.awverret.gymclimbtracker.model.RouteWall;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 6/2/17.
 */

public class StorageRoute {

    public String id;
    public BoulderGrade boulderGrade;
    public RopeGrade ropeGrade;
    public RouteColor color;
    public RouteWall wall;
    public String name;
    public RouteSetter setter;
    public long setDate;

    public StorageRoute() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public StorageRoute(String id, BoulderGrade boulderGrade, RopeGrade ropeGrade, String name, RouteSetter setter, RouteColor color, RouteWall wall, long setDate) {
        this.id = id;
        this.boulderGrade = boulderGrade;
        this.ropeGrade = ropeGrade;
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

    public RopeGrade getRopeGrade() { return ropeGrade; }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setBoulderGrade(BoulderGrade boulderGrade) {
        this.boulderGrade = boulderGrade;
    }

    public void setRopeGrade(RopeGrade ropeGrade) {
        this.ropeGrade = ropeGrade;
    }

    public void setColor(RouteColor color) {
        this.color = color;
    }

    public void setWall(RouteWall wall) {
        this.wall = wall;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSetter(RouteSetter setter) {
        this.setter = setter;
    }

    public void setSetDate(long setDate) {
        this.setDate = setDate;
    }

    @Override
    public String toString() {
        return "StoreageRoute{" +
                "id='" + id + '\'' +
                '}';
    }
}
