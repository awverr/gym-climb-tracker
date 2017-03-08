package com.awverret.gymclimbtracker.store;

import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.LeadClimb;

/**
 * Created by aubry on 3/1/17.
 */

public interface CloudStore {

    void saveLeadClimb(LeadClimb climb);
}
