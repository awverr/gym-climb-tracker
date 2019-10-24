package com.topoutlabs.gymclimbtracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.model.Route;

import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by aubry on 2/8/18.
 */

public class ViewRouteFragment extends Fragment {

    TextView routeNameTextView, routeTypeTextView, routeGradeTextView, routeColorTextView, routeWallTextView, routeSetterTextView, routeSetDateTextView;
    Route route;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.activity_view_route, container, false);

        Bundle bundle=getArguments();

        if(bundle != null) {
            route = bundle.getParcelable("route");

            routeNameTextView = rootView.findViewById(R.id.route_name_text_view);
            routeTypeTextView = rootView.findViewById(R.id.route_type_text_view);
            routeGradeTextView = rootView.findViewById(R.id.route_grade_text_view);
            routeColorTextView = rootView.findViewById(R.id.route_color_text_view);
            routeWallTextView = rootView.findViewById(R.id.route_wall_text_view);
            routeSetterTextView = rootView.findViewById(R.id.route_setter_text_view);
            routeSetDateTextView = rootView.findViewById(R.id.route_set_date_text_view);

            initializeRoute(route);
        }
        return rootView;
    }

    public void initializeRoute(Route route){

        Instant inst = new Instant(route.getSetDate());
        LocalDate localDate = LocalDate.fromDateFields(inst.toDate());
        DateTimeFormatter fmt = DateTimeFormat.forPattern(getResources().getString(R.string.date_format_string));
        String stringDate = localDate.toString(fmt);

        routeNameTextView.setText(route.getName());
        routeTypeTextView.setText(route.getType().getText());
        routeGradeTextView.setText(route.getRouteGrade().getText());
        routeColorTextView.setText(route.getColor().getText());
        routeWallTextView.setText(route.getWall().getText());
        routeSetterTextView.setText(route.getSetter());
        routeSetDateTextView.setText(stringDate);

    }
}
