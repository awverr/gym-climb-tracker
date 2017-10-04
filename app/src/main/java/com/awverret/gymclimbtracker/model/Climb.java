package com.awverret.gymclimbtracker.model;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 10/3/17.
 */

public class Climb {

    String id;
    String userId;
    String routeId;
    //need to add send date

    public Climb(String userId, String routeId){
        id = randomUUID().toString();
        this.userId = userId;
        this.routeId = routeId;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getRouteId() {
        return routeId;
    }
}
