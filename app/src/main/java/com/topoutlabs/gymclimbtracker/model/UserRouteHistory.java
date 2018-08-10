package com.topoutlabs.gymclimbtracker.model;

import java.util.HashSet;

/**
 * Created by aubry on 8/18/17.
 */

public class UserRouteHistory {

    String user;
    HashSet<Route> userHistory;

    public UserRouteHistory(User user, Route route){
        this.user = user.getUid();
        userHistory = new HashSet<>();
        userHistory.add(route);
    }

    public String getUser() {
        return user;
    }

    public HashSet<Route> getUserHistory() {
        return userHistory;
    }

    public void addRouteToHistory(Route route) {
        userHistory.add(route);
    }
}
