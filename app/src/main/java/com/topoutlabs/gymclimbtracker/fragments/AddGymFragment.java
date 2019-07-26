package com.topoutlabs.gymclimbtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.store.CloudStore;

public class AddGymFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    TextView gymNameTextView;

    Spinner gymStateSpinner;

    Button saveGymButton;

    CloudStore store;

    private MainActivity activity;

    View view;

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
            view = inflater.inflate(R.layout.fragment_add_gym, container, false);

            gymStateSpinner = (Spinner) view.findViewById(R.id.gym_state_spinner);
            initializeGymStateSpinner(gymStateSpinner);
            gymStateSpinner.setOnItemSelectedListener(this);

            saveGymButton = view.findViewById(R.id.save_gym_button);
            saveGymButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String stringType = (String) routeTypeSpinner.getSelectedItem();
                    String stringGrade = (String) routeGradeSpinner.getSelectedItem();
                    String stringColor = (String) routeColorSpinner.getSelectedItem();
                    String stringWall = (String) routeWallSpinner.getSelectedItem();
                    String stringSetter = editTextSetter.getText().toString();
                    RouteColor color = RouteColor.fromString(stringColor);
                    RouteWall wall = RouteWall.fromString(stringWall);

                    store.saveRoute(new Route(type, activity.getLocalStore().getGym().getId(), grade, null, stringSetter, color, wall, dateInMillis));

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
