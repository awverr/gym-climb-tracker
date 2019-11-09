package com.topoutlabs.gymclimbtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.topoutlabs.gymclimbtracker.activities.GymRecyclerAdapter;
import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.RouteColor;
import com.topoutlabs.gymclimbtracker.model.RouteGrade;
import com.topoutlabs.gymclimbtracker.model.RouteType;
import com.topoutlabs.gymclimbtracker.model.RouteWall;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Fragment that contains fields and button for adding a new route to the database.
 */

public class AddRouteFragment extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerFragment.NoticeDialogListener {

    Spinner routeTypeSpinner, routeGradeSpinner, routeWallSpinner, routeColorSpinner, gymSpinner;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if(view == null) {
            view = inflater.inflate(R.layout.activity_add_route, container, false);

            //Set datePicked textView to current date as default when fragment is created.
            datePicked = view.findViewById(R.id.date_picked);
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
            String date = simpleDateFormat.format(Calendar.getInstance().getTime());
            datePicked.setText(date);
            routeTypeSpinner = view.findViewById(R.id.route_type_spinner);
            routeGradeSpinner = view.findViewById(R.id.grade_spinner);
            routeWallSpinner = view.findViewById(R.id.route_wall_spinner);
            routeColorSpinner = view.findViewById(R.id.route_color_spinner);
            gymSpinner
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
                    EditText editTextSetter = view.findViewById(R.id.route_setter_text_view);
                    String stringColor = (String) routeColorSpinner.getSelectedItem();
                    String stringWall = (String) routeWallSpinner.getSelectedItem();
                    RouteType type = RouteType.fromString(stringType);
                    RouteGrade grade = RouteGrade.fromString(stringGrade);
                    String stringSetter = editTextSetter.getText().toString();
                    RouteColor color = RouteColor.fromString(stringColor);
                    RouteWall wall = RouteWall.fromString(stringWall);
                    Gym gym = activity.getLocalStore().getGym();
                    store.saveRoute(new Route(type, gym.getId(), grade, null, stringSetter, color, wall, dateInMillis));

                    // Now return to the viewAllRoutesFragment
                    ViewAllRoutesFragment viewAllRoutesFragment = new ViewAllRoutesFragment();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("gym", gym);

                    viewAllRoutesFragment.setArguments(bundle);

                   // viewAllRoutesFragment.setArguments(getActivity().getIntent().getExtras());

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragment_container, viewAllRoutesFragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
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

    public void initializeGymSpinner(Spinner spinner){
        final ArrayList<Gym> gymList = new ArrayList<>();
        store.lookupGyms(new Callback<ArrayList<Gym>>() {
            @Override
            public void receive(ArrayList<Gym> strings) {

                if(!strings.isEmpty()) {
                    for (Gym g : strings) {
                        gymList.add(g);
                    }
                }

            }
        });

        ArrayAdapter<CharSequence> routeTypeAdapter = ArrayAdapter.createFromResource(activity,
                R.array.route_type_array, android.R.layout.simple_spinner_item);
        routeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeTypeAdapter);
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
        datePicked = view.findViewById(R.id.date_picked);
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        String date = simpleDateFormat.format(c.getTime());
        datePicked.setText(date);
    }
}
