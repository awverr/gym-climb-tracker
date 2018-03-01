package com.awverret.gymclimbtracker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.Route;

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

        Bundle data = getIntent().getExtras();
        route = (Route) data.getParcelable("route");

        routeNameTextView = (TextView) container.findViewById(R.id.route_name_text_view);
        routeTypeTextView = (TextView) container.findViewById(R.id.route_type_text_view);
        routeGradeTextView = (TextView) container.findViewById(R.id.route_grade_text_view);
        routeColorTextView = (TextView) container.findViewById(R.id.route_color_text_view);
        routeWallTextView = (TextView) container.findViewById(R.id.route_wall_text_view);
        routeSetterTextView = (TextView) container.findViewById(R.id.route_setter_text_view);
        routeSetDateTextView = (TextView) container.findViewById(R.id.route_set_date_text_view);

        initializeRoute(route);

        return inflater.inflate(R.layout.activity_view_route, container, false);
    }

    public void initializeRoute(Route route){

        Instant inst = new Instant(route.getSetDate());
        LocalDate localDate = LocalDate.fromDateFields(inst.toDate());
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/d/yyyy");
        String stringDate = localDate.toString(fmt);
        System.out.println("VERRET: localDate: " + localDate);

        routeNameTextView.setText(route.getName());
        routeTypeTextView.setText(route.getType().getText());
        routeGradeTextView.setText(route.getRouteGrade().getText());
        routeColorTextView.setText(route.getColor().getText());
        routeWallTextView.setText(route.getWall().getText());
        routeSetterTextView.setText(route.getSetter());
        routeSetDateTextView.setText(stringDate);

    }
}
