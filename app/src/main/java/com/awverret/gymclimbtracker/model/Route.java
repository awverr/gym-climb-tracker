package com.awverret.gymclimbtracker.model;

import java.util.Date;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 2/24/17.
 */

public class Route {

    String id;


    public Route() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Route(String name, RouteSetter setter, RouteColor color, RouteWall wall, Date setDate){
        id = randomUUID().toString();

    }

    public String getId() {return id;}


}
