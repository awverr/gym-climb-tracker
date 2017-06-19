package com.topoutlabs.gymclimbtracker.store;

import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.ArrayList;

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
    void lookUpRoutes(Callback<ArrayList<String>> callback);
}
