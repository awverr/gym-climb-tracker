package com.awverret.gymclimbtracker.model;

import static java.util.UUID.randomUUID;

/**
 * Created by aubry on 3/1/17.
 */

public class TopRopeRoute extends Route {

    String id;
    RopeGrade ropeGrade;

    public TopRopeRoute() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public TopRopeRoute(RopeGrade ropeGrade) {
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
