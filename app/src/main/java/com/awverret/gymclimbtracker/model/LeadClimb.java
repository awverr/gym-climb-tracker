package com.awverret.gymclimbtracker.model;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 3/1/17.
 */

public class LeadClimb extends Climb {

    String id;
    RopeGrade ropeGrade;

    public LeadClimb() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public LeadClimb(RopeGrade ropeGrade) {
        id = randomUUID().toString();
        this.ropeGrade = ropeGrade;
    }

    public String getId() {
        return id;
    }

    public RopeGrade getRopeGrade() {
        return ropeGrade;
    }
}
