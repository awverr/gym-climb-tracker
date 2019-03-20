package com.topoutlabs.gymclimbtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.activities.RouteRecyclerAdapter;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.store.PreferencesLocalStore;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.ArrayList;

public class ChooseGymFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    CloudStore store;

    ArrayList<Route> gymList = new ArrayList<>(); //For use in recylcer view.

    private RecyclerView mRecyclerView;
    private RouteRecyclerAdapter recyclerAdapter;

    Spinner gymStateSpinner; //For filtering routes

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
            view = inflater.inflate(R.layout.fragment_choose_gym, container, false);

            gymStateSpinner = (Spinner) view.findViewById(R.id.filter_gym_state_spinner);
            initializeGymStateSpinner(gymStateSpinner);
            gymStateSpinner.setOnItemSelectedListener(this);

            store = new FirebaseCloudStore();

            initializeRecyclerView("");
        }

        return view;
    }

    private void initializeRecyclerView(final String filterSelection) {
        gymList.clear();
        store.lookUpRoutes(new Callback<ArrayList<Route>>() {
            @Override
            public void receive(ArrayList<Route> strings) {

                if(!strings.isEmpty()) {
                    for (Route r : strings) {
                        if(r.wall.getText().equals(filterSelection) || filterSelection.equals("All States")) {
                            //            routes.add(r.getName());
                            gymList.add(r);
                        }
                    }
                }

                //  routeList.stream().filter(S -> S.equals(filterSelection)).collect(Collectors.toList());

                mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

                if(localStore.getUser().isPresent()) {
                    recyclerAdapter = new RouteRecyclerAdapter(routeList, localStore.getUser().get(), activity);
                }

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(recyclerAdapter);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
