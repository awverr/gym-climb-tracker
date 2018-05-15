package com.awverret.gymclimbtracker.store;

import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.model.UserRouteInfo;
import com.awverret.gymclimbtracker.util.Callback;

import java.util.ArrayList;

/**
 * Created by aubry on 3/1/17. Storage interface.
 */

public interface CloudStore {

    void saveRoute(Route route);
    void googleLogin(User user);
    void googleLogout();
    void lookUpRoutes(Callback<ArrayList<Route>> callback);
    void saveClimb(Climb climb);
    void lookupClimbsForUser(Callback<ArrayList<Climb>> callback);
    void lookupRouteName(String uid, Callback<String> callback);
    void lookupRouteFromClimb(Climb climb, Callback<Route> callback);
    void saveUserRouteInfo(UserRouteInfo info);
    void lookupUserRouteInfo(Callback<UserRouteInfo> callback);

}
