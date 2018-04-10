package com.awverret.gymclimbtracker.model;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 4/9/18.
 */

public class UserRouteInfo {

    String userId;
    String routeId;

    int numAttempts;
    String routeNotes;

    public UserRouteInfo(){

    }

    public UserRouteInfo(String userId, String routeId){
        this.userId = userId;
        this.routeId = routeId;
    }

    public int getNumAttempts() {
        return numAttempts;
    }

    public void incrementNumAttempts() {
        numAttempts++;
    }

    public String getRouteNotes() {
        return routeNotes;
    }

    public void setRouteNotes(String routeNotes) {
        this.routeNotes = routeNotes;
    }

    public String getUserId() {
        return userId;
    }

    public String getRouteId() {
        return routeId;
    }

}
