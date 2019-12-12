package com.topoutlabs.gymclimbtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.activities.GymRecyclerAdapter;
import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.store.LocalStore;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.ArrayList;

public class ChooseGymFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    CloudStore store;

    LocalStore localStore;

    ArrayList<Gym> gymList = new ArrayList<>(); //For use in recylcer view.

    Button addGymButton;

    private RecyclerView mRecyclerView;
    private GymRecyclerAdapter recyclerAdapter;

    Spinner gymStateSpinner; //For filtering gyms

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if(view == null) {
            view = inflater.inflate(R.layout.fragment_choose_gym, container, false);

            gymStateSpinner = view.findViewById(R.id.filter_gym_state_spinner);
            initializeGymStateSpinner(gymStateSpinner);
            gymStateSpinner.setOnItemSelectedListener(this);

            store = new FirebaseCloudStore();
            localStore = activity.getLocalStore();

            initializeRecyclerView("");

            addGymButton = view.findViewById(R.id.add_gym_button);
            addGymButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    AddGymFragment addGymFragment = new AddGymFragment();

                    addGymFragment.setArguments(getActivity().getIntent().getExtras());

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragment_container, addGymFragment).commit();
                    transaction.addToBackStack(null);
                }
            });
        }

        return view;
    }

    private void initializeGymStateSpinner(Spinner spinner){

        ArrayAdapter<CharSequence> gymStateAdapter = ArrayAdapter.createFromResource(activity,
                R.array.state_filter_array, android.R.layout.simple_spinner_item);
        gymStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gymStateAdapter);
    }

    //TODO: Handle Case where gyms isEmpty(). Display a message and add the "Add Gym button".
    private void initializeRecyclerView(final String filterSelection) {
        gymList.clear();
        store.lookupGyms(new Callback<ArrayList<Gym>>() {
            @Override
            public void receive(ArrayList<Gym> strings) {

                if(!strings.isEmpty()) {
                    for (Gym g : strings) {
                        if(g.state.equals(filterSelection) || filterSelection.equals("All States")) {
                            gymList.add(g);
                        }
                    }
                }

                mRecyclerView = view.findViewById(R.id.recyclerView);

                recyclerAdapter = new GymRecyclerAdapter(gymList, activity, localStore);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(recyclerAdapter);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selection = (String) adapterView.getItemAtPosition(i);
        initializeRecyclerView(selection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
