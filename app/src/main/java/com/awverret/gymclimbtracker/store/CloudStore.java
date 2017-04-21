package com.awverret.gymclimbtracker.store;

import com.awverret.gymclimbtracker.model.BoulderRoute;
import com.awverret.gymclimbtracker.model.LeadRoute;
import com.awverret.gymclimbtracker.model.TopRopeRoute;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.util.Callback;

/**
 * Created by aubry on 3/1/17. Storage interface.
 */

public interface CloudStore {

    void saveRoute(LeadRoute route);
    void saveRoute(TopRopeRoute route);
    void saveRoute(BoulderRoute route);
    void googleLogin(User user, Callback<User> callback);
}
