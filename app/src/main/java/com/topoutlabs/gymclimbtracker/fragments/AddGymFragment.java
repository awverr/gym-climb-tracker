package com.topoutlabs.gymclimbtracker.fragments;

import android.content.Context;
import android.os.Bundle;
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
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;

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

            store = new FirebaseCloudStore();

            gymStateSpinner = (Spinner) view.findViewById(R.id.gym_state_spinner);
            initializeGymStateSpinner(gymStateSpinner);
            gymStateSpinner.setOnItemSelectedListener(this);

            saveGymButton = view.findViewById(R.id.save_gym_button);
            saveGymButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    EditText editTextName = (EditText) view.findViewById(R.id.gym_name_text_view);
                    String stringGymState = (String) gymStateSpinner.getSelectedItem();
                    String stringName = editTextName.getText().toString();

                    store.saveGym(new Gym(stringName, stringGymState));

                    // TODO: Which fragment do you want to return to?
//                    ViewAllRoutesFragment viewAllRoutesFragment = new ViewAllRoutesFragment();
//
//                    viewAllRoutesFragment.setArguments(getActivity().getIntent().getExtras());
//
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//
//                    transaction.replace(R.id.fragment_container, viewAllRoutesFragment).commit();
//                    transaction.addToBackStack(null);
                }
            });
        }

        return view;
    }

    public void initializeGymStateSpinner(Spinner spinner){
        ArrayAdapter<CharSequence> gymStateAdapter = ArrayAdapter.createFromResource(activity,
                R.array.state_filter_array, android.R.layout.simple_spinner_item);
        gymStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gymStateAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
