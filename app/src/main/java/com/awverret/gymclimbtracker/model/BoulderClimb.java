package com.awverret.gymclimbtracker.model;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 3/1/17.
 */

public class BoulderClimb extends Climb {

    String id;
    BoulderGrade boulderGrade;

    public BoulderClimb() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public BoulderClimb(BoulderGrade boulderGrade) {
        id = randomUUID().toString();
        this.boulderGrade = boulderGrade;
    }

    public String getId() {
        return id;
    }

    public BoulderGrade getBoulderGrade() {
        return boulderGrade;
    }
}
