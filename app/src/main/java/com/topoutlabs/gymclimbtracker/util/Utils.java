package com.topoutlabs.gymclimbtracker.util;

import com.topoutlabs.gymclimbtracker.model.Route;

/**
 * Created by aubry on 3/8/17.
 */

public class Utils {

    public static String createRouteName(Route route){

        return route.getWall().getText() + "/ " + route.getColor().getText() + "/ " + route.getRouteGrade().getText();
    }
}
