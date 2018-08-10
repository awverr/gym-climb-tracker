package com.topoutlabs.gymclimbtracker.store;

import com.topoutlabs.gymclimbtracker.model.Climb;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.model.UserRouteInfo;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.ArrayList;

/**
 * Created by aubry on 3/1/17. Storage interface.
 */

public interface CloudStore {

    void saveRoute(Route route);
    void googleLogin(User user);
    void googleLogout();
    void lookUpRoutes(Callback<ArrayList<Route>> callback);
    void saveClimb(Climb climb, User user);
    void lookupClimbsForUser(User user, Callback<ArrayList<Climb>> callback);
    void lookupRouteName(String uid, Callback<String> callback);
    void lookupRouteFromClimb(Climb climb, Callback<Route> callback);
    void updateNumAttempts(Climb climb, int numAttempts);
    void updateRouteNotes(Climb climb, String routeNotes);
    void updateSent(Climb climb, boolean sent);
}
