package com.awverret.gymclimbtracker.util;

import com.awverret.gymclimbtracker.model.BoulderRoute;
import com.awverret.gymclimbtracker.model.LeadRoute;
import com.awverret.gymclimbtracker.model.RopeGrade;
import com.awverret.gymclimbtracker.model.Route;

/**
 * Created by aubry on 3/8/17.
 */

public class Utils {

    public RopeGrade gradeStringToGradeEnum(String grade){
        return null;
    }

    public static String createRouteName(Route route){

        return route.getWall().toString() + " " + route.getColor() + " " + route.getRouteGrade().toString();
    }
}
