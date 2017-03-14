package com.awverret.gymclimbtracker.store;

import com.awverret.gymclimbtracker.model.BoulderClimb;
import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.LeadClimb;
import com.awverret.gymclimbtracker.model.TopRopeClimb;

/**
 * Created by aubry on 3/1/17. Storage interface.
 */

public interface CloudStore {

    void saveClimb(LeadClimb climb);
    void saveClimb(TopRopeClimb climb);
    void saveClimb(BoulderClimb climb);
}
