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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.activities.RouteRecyclerAdapter;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.store.LocalStore;
import com.topoutlabs.gymclimbtracker.store.PreferencesLocalStore;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aubry on 3/1/2018.
 */

public class ViewAllRoutesFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    CloudStore store;

    LocalStore localStore;

    ArrayList<Route> routeList = new ArrayList<>(); //For use in recylcer view.

    private RecyclerView mRecyclerView;
    private RouteRecyclerAdapter recyclerAdapter;

    Spinner routeWallSpinner; //For filtering routes

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
            view = inflater.inflate(R.layout.fragment_view_all_routes, container, false);

            routeWallSpinner = (Spinner) view.findViewById(R.id.filter_route_wall_spinner);
            initializeRouteWallSpinner(routeWallSpinner);
            routeWallSpinner.setOnItemSelectedListener(this);

            store = new FirebaseCloudStore();

            localStore = new PreferencesLocalStore(activity);

            initializeRecyclerView("");
        }

        return view;
    }

    private void initializeRouteWallSpinner(Spinner spinner){
        //filter_route_wall_spinner

        ArrayAdapter<CharSequence> routeWallAdapter = ArrayAdapter.createFromResource(activity,
                R.array.route_wall_filter_array, android.R.layout.simple_spinner_item);
        routeWallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeWallAdapter);
    }

    private void initializeRecyclerView(final String filterSelection) {
        routeList.clear();
        store.lookUpRoutes(new Callback<ArrayList<Route>>() {
            @Override
            public void receive(ArrayList<Route> strings) {

                if(!strings.isEmpty()) {
                    for (Route r : strings) {
                        if(r.wall.getText().equals(filterSelection) || filterSelection.equals("All Walls")) {
                            //            routes.add(r.getName());
                            routeList.add(r);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = (String) parent.getItemAtPosition(position);
        initializeRecyclerView(selection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

