package com.awverret.gymclimbtracker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.Route;

import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by aubry on 8/24/2017.
 */

public class ViewRouteActivity extends AppCompatActivity {

    TextView routeNameTextView, routeTypeTextView, routeGradeTextView, routeColorTextView, routeWallTextView, routeSetterTextView, routeSetDateTextView;
    Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_route);

        Bundle data = getIntent().getExtras();
        route = (Route) data.getParcelable("route");

        routeNameTextView = (TextView) findViewById(R.id.route_name_text_view);
        routeTypeTextView = (TextView) findViewById(R.id.route_type_text_view);
        routeGradeTextView = (TextView) findViewById(R.id.route_grade_text_view);
        routeColorTextView = (TextView) findViewById(R.id.route_color_text_view);
        routeWallTextView = (TextView) findViewById(R.id.route_wall_text_view);
        routeSetterTextView = (TextView) findViewById(R.id.route_setter_text_view);
        routeSetDateTextView = (TextView) findViewById(R.id.route_set_date_text_view);

        initializeRoute(route);
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
