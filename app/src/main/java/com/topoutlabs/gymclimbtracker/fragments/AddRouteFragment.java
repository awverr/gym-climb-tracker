package com.topoutlabs.gymclimbtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.RouteColor;
import com.topoutlabs.gymclimbtracker.model.RouteGrade;
import com.topoutlabs.gymclimbtracker.model.RouteType;
import com.topoutlabs.gymclimbtracker.model.RouteWall;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Fragment that contains fields and button for adding a new route to the database.
 */

public class AddRouteFragment extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerFragment.NoticeDialogListener {

    Spinner routeTypeSpinner, routeGradeSpinner, routeWallSpinner, routeColorSpinner;

    Button setDateButton;

    Button saveRouteButton;

    TextView datePicked;

    private MainActivity activity;

    View view;

    CloudStore store;

    long dateInMillis = 0;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(activity == null && context instanceof MainActivity){
            activity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if(view == null) {
            view = inflater.inflate(R.layout.activity_add_route, container, false);

            //Set datePicked textView to current date as default when fragment is created.
            datePicked = (TextView) view.findViewById(R.id.date_picked);
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(Calendar.getInstance().getTime());
            datePicked.setText(date.toString());
            routeTypeSpinner = (Spinner) view.findViewById(R.id.route_type_spinner);
            routeGradeSpinner = (Spinner) view.findViewById(R.id.grade_spinner);
            routeWallSpinner = (Spinner) view.findViewById(R.id.route_wall_spinner);
            routeColorSpinner = (Spinner) view.findViewById(R.id.route_color_spinner);
            initializeRouteTypeSpinner(routeTypeSpinner);
            initializeRouteGradeSpinner(routeGradeSpinner);
            initializeRouteWallSpinner(routeWallSpinner);
            initializeRouteColorSpinner(routeColorSpinner);
            routeTypeSpinner.setOnItemSelectedListener(this);
            routeWallSpinner.setOnItemSelectedListener(this);
            routeColorSpinner.setOnItemSelectedListener(this);

            setDateButton = view.findViewById(R.id.date_picker);
            setDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getChildFragmentManager(), "date_Picker");
                }
            });

            saveRouteButton = view.findViewById(R.id.save_route_button);
            saveRouteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String stringType = (String) routeTypeSpinner.getSelectedItem();
                    String stringGrade = (String) routeGradeSpinner.getSelectedItem();
                    EditText editTextSetter = (EditText) view.findViewById(R.id.route_setter_text_view);
                    String stringColor = (String) routeColorSpinner.getSelectedItem();
                    String stringWall = (String) routeWallSpinner.getSelectedItem();
                    RouteType type = RouteType.fromString(stringType);
                    RouteGrade grade = RouteGrade.fromString(stringGrade);
                    String stringSetter = editTextSetter.getText().toString();
                    RouteColor color = RouteColor.fromString(stringColor);
                    RouteWall wall = RouteWall.fromString(stringWall);

                    store.saveRoute(new Route(type, grade, null, stringSetter, color, wall, dateInMillis));

                    // Now return to the viewAllRoutesFragment
                    ViewAllRoutesFragment viewAllRoutesFragment = new ViewAllRoutesFragment();

                    viewAllRoutesFragment.setArguments(getActivity().getIntent().getExtras());

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragment_container, viewAllRoutesFragment).commit();
                    transaction.addToBackStack(null);
                }
            });

            store = new FirebaseCloudStore();
        }

        return view;
    }

    public void initializeRouteTypeSpinner(Spinner spinner){
        //route_type_spinner

        ArrayAdapter<CharSequence> routeTypeAdapter = ArrayAdapter.createFromResource(activity,
                R.array.route_type_array, android.R.layout.simple_spinner_item);
        routeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeTypeAdapter);
    }

    public void initializeRouteGradeSpinner(Spinner spinner){
        //route_grade_spinner

        ArrayAdapter<CharSequence> routeGradeAdapter = ArrayAdapter.createFromResource(activity,
                R.array.route_grade_array, android.R.layout.simple_spinner_item);
        routeGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeGradeAdapter);
    }

    public void initializeRouteColorSpinner(Spinner spinner){
        //route_color_spinner

        ArrayAdapter<CharSequence> routeColorAdapter = ArrayAdapter.createFromResource(activity,
                R.array.route_color_array, android.R.layout.simple_spinner_item);
        routeColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeColorAdapter);
    }

    public void initializeRouteWallSpinner(Spinner spinner){
        //route_wall_spinner

        ArrayAdapter<CharSequence> routeWallAdapter = ArrayAdapter.createFromResource(activity,
                R.array.route_wall_array, android.R.layout.simple_spinner_item);
        routeWallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeWallAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0);
        dateInMillis = c.getTimeInMillis();

        //Set date_picked textView to chosen date.
        datePicked = (TextView) view.findViewById(R.id.date_picked);
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(c.getTime());
        datePicked.setText(date.toString());
    }
}
