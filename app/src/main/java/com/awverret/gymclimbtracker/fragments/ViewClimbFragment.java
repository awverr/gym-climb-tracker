package com.awverret.gymclimbtracker.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.FirebaseCloudStore;

import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ViewClimbFragment extends Fragment {

    TextView routeNameTextView, routeTypeTextView, routeGradeTextView, routeColorTextView, routeWallTextView, routeSetterTextView, routeSetDateTextView, numAttempts, routeNotes;

    Spinner sentSpinner;

    Climb climb;

    Route route;

    User user;

    CloudStore store;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_view_climb, container, false);

        store = new FirebaseCloudStore(getActivity());

        Bundle bundle=getArguments();

        route = bundle.getParcelable("route");

        climb = bundle.getParcelable("climb");

        user = bundle.getParcelable("user");

        routeNameTextView = (TextView) rootView.findViewById(R.id.climb_name_text_view);
        routeTypeTextView = (TextView) rootView.findViewById(R.id.climb_type_text_view);
        routeGradeTextView = (TextView) rootView.findViewById(R.id.climb_grade_text_view);
        routeColorTextView = (TextView) rootView.findViewById(R.id.climb_color_text_view);
        routeWallTextView = (TextView) rootView.findViewById(R.id.climb_wall_text_view);
        routeSetterTextView = (TextView) rootView.findViewById(R.id.climb_setter_text_view);
        routeSetDateTextView = (TextView) rootView.findViewById(R.id.climb_set_date_text_view);
        numAttempts = (TextView) rootView.findViewById(R.id.num_attempt_textview);
        routeNotes = (TextView) rootView.findViewById(R.id.route_notes_textview);
        sentSpinner = (Spinner) rootView.findViewById(R.id.sent_spinner);

        //System.out.println("VERRET: routeNameTextView: " + routeNameTextView);

        initializeClimb(climb, route);

        return rootView;
    }

    public void initializeClimb(final Climb climb, Route route) {

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

        numAttempts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editNumAttempts();
            }
        });

        if (climb.getRouteNotes() == null) {
            routeNotes.setText("No notes");
        }
        else{
            routeNotes.setText(climb.getRouteNotes());
        }

        routeNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editRouteNotes();
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sent_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sentSpinner.setAdapter(adapter);

    }

    private void editNumAttempts(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Number of Attempts");
        builder.setMessage("How many attempts did this climb require?");

        final EditText input = new EditText(getActivity());

        input.setText(numAttempts.getText());

        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newNumAttempts = Integer.parseInt(input.getText().toString());
                climb.setNumAttempts(newNumAttempts);
                store.updateNumAttempts(climb, newNumAttempts);
                initializeClimb(climb, route);
            }
        });
        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void editRouteNotes(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Your Route Notes");
        builder.setMessage("What would you like to say about this route?");

        final EditText input = new EditText(getActivity());

        input.setText(routeNotes.getText());

        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newRouteNotes = input.getText().toString();
                climb.setRouteNotes(newRouteNotes);
                store.updateRouteNotes(climb, newRouteNotes);
                initializeClimb(climb, route);
            }
        });
        builder.setNegativeButton("Cancel", null);

        builder.show();
    }
}
