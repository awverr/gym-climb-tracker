package com.awverret.gymclimbtracker.store;

import com.awverret.gymclimbtracker.model.BoulderRoute;
import com.awverret.gymclimbtracker.model.LeadRoute;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.model.TopRopeRoute;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aubry on 3/1/17. Storage interface.
 */

public interface CloudStore {

//    void saveRoute(LeadRoute route);
//    void saveRoute(TopRopeRoute route);
//    void saveRoute(BoulderRoute route);
    void saveRoute(Route route);
    void googleLogin(User user);
    void googleLogout();
    void lookUpRoutes(Callback<ArrayList<Route>> callback);
}
