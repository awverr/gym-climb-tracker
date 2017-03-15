package com.awverret.gymclimbtracker.store;

import com.awverret.gymclimbtracker.model.BoulderRoute;
import com.awverret.gymclimbtracker.model.LeadRoute;
import com.awverret.gymclimbtracker.model.TopRopeRoute;

/**
 * Created by aubry on 3/1/17. Storage interface.
 */

public interface CloudStore {

    void saveRoute(LeadRoute route);
    void saveRoute(TopRopeRoute route);
    void saveRoute(BoulderRoute route);
}
