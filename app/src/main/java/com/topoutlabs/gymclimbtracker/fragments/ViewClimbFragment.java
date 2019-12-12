package com.topoutlabs.gymclimbtracker.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.model.Climb;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;

import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ViewClimbFragment extends Fragment{

    TextView routeNameTextView, routeTypeTextView, routeGradeTextView, routeColorTextView, routeWallTextView, routeSetterTextView, routeSetDateTextView, numAttempts, routeNotes;

    Spinner sentSpinner;

    Climb climb;

    Route route;

    User user;

    CloudStore store;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_view_climb, container, false);

        store = new FirebaseCloudStore();

        Bundle bundle=getArguments();

        if(bundle != null) {

            route = bundle.getParcelable("route");

            climb = bundle.getParcelable("climb");

            user = bundle.getParcelable("user");

            routeNameTextView = rootView.findViewById(R.id.climb_name_text_view);
            routeTypeTextView = rootView.findViewById(R.id.climb_type_text_view);
            routeGradeTextView = rootView.findViewById(R.id.climb_grade_text_view);
            routeColorTextView = rootView.findViewById(R.id.climb_color_text_view);
            routeWallTextView = rootView.findViewById(R.id.climb_wall_text_view);
            routeSetterTextView = rootView.findViewById(R.id.climb_setter_text_view);
            routeSetDateTextView = rootView.findViewById(R.id.climb_set_date_text_view);
            numAttempts = rootView.findViewById(R.id.num_attempt_textview);
            routeNotes = rootView.findViewById(R.id.route_notes_textview);
            sentSpinner = rootView.findViewById(R.id.sent_spinner);

            initializeClimb(climb, route);

        }

        return rootView;
    }

    public void initializeClimb(final Climb climb, final Route route) {

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
        numAttempts.setText(String.valueOf(climb.getNumAttempts()));

        numAttempts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editNumAttempts();
            }
        });

        if (climb.getRouteNotes() == null) {
            routeNotes.setText(getResources().getString(R.string.no_notes_string));
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


        //Initialize sentSpinner
        String compareValue;
        if(climb.getSent()){
            compareValue = "Yes";
        }else{
            compareValue = "No";
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sent_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sentSpinner.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(compareValue);
        sentSpinner.setSelection(spinnerPosition);
        sentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newSentValString = parent.getItemAtPosition(position).toString();
                boolean newSentVal;
                newSentVal = newSentValString.equals("Yes");
                climb.setSent(newSentVal);
                store.updateSent(climb, newSentVal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void editNumAttempts(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.edit_number_of_attempts_string));
        builder.setMessage(getResources().getString(R.string.how_many_attempts_string));

        final EditText input = new EditText(getActivity());

        input.setText(numAttempts.getText());

        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.save_button_string), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newNumAttempts = Integer.parseInt(input.getText().toString());
                climb.setNumAttempts(newNumAttempts);
                store.updateNumAttempts(climb, newNumAttempts);
                initializeClimb(climb, route);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel_button_string), null);

        builder.show();
    }

    private void editRouteNotes(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.edit_route_notes_string));
        builder.setMessage(getResources().getString(R.string.what_are_your_notes_string));

        final EditText input = new EditText(getActivity());

        input.setText(routeNotes.getText());

        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.save_button_string), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newRouteNotes = input.getText().toString();
                climb.setRouteNotes(newRouteNotes);
                store.updateRouteNotes(climb, newRouteNotes);
                initializeClimb(climb, route);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel_button_string), null);

        builder.show();
    }
}
