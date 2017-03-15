package com.awverret.gymclimbtracker.model;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 3/1/17.
 */

public class LeadRoute extends Route {


    RopeGrade ropeGrade;

    public LeadRoute() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public LeadRoute(RopeGrade ropeGrade) {
        this.ropeGrade = ropeGrade;
    }

    public String getId() {
        return id;
    }

    public RopeGrade getRopeGrade() {
        return ropeGrade;
    }
}
