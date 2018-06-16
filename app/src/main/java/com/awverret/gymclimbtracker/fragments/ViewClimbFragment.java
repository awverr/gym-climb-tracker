package com.awverret.gymclimbtracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.FirebaseCloudStore;

import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ViewClimbFragment extends Fragment {

    TextView routeNameTextView, routeTypeTextView, routeGradeTextView, routeColorTextView, routeWallTextView, routeSetterTextView, routeSetDateTextView, numAttempts, routeNotes;

    Climb climb;

    Route route;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_view_climb, container, false);

        Bundle bundle=getArguments();

        route = bundle.getParcelable("route");

        climb = bundle.getParcelable("climb");

        routeNameTextView = (TextView) rootView.findViewById(R.id.climb_name_text_view);
        routeTypeTextView = (TextView) rootView.findViewById(R.id.climb_type_text_view);
        routeGradeTextView = (TextView) rootView.findViewById(R.id.climb_grade_text_view);
        routeColorTextView = (TextView) rootView.findViewById(R.id.climb_color_text_view);
        routeWallTextView = (TextView) rootView.findViewById(R.id.climb_wall_text_view);
        routeSetterTextView = (TextView) rootView.findViewById(R.id.climb_setter_text_view);
        routeSetDateTextView = (TextView) rootView.findViewById(R.id.climb_set_date_text_view);
        numAttempts = (TextView) rootView.findViewById(R.id.num_attempt_textview);
        routeNotes = (TextView) rootView.findViewById(R.id.route_notes_textview);

        //System.out.println("VERRET: routeNameTextView: " + routeNameTextView);

        initializeClimb(climb, route);

        return rootView;
    }

    public void initializeClimb(Climb climb, Route route){

        Instant inst = new Instant(route.getSetDate());
        LocalDate localDate = LocalDate.fromDateFields(inst.toDate());
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/d/yyyy");
        String stringDate = localDate.toString(fmt);

        routeNameTextView.setText(route.getName());
        routeTypeTextView.setText(route.getType().getText());
        routeGradeTextView.setText(route.getRouteGrade().getText());
        routeColorTextView.setText(route.getColor().getText());
        routeWallTextView.setText(route.getWall().getText());
        routeSetterTextView.setText(route.getSetter());
        routeSetDateTextView.setText(stringDate);
        numAttempts.setText(String.valueOf(climb.getNumAttempts()));

        if(climb.getRouteNotes() == null){
            routeNotes.setText("No notes");
        }


    }
}
