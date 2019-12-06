package com.topoutlabs.gymclimbtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.activities.RouteRecyclerAdapter;
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.store.LocalStore;
import com.topoutlabs.gymclimbtracker.store.PreferencesLocalStore;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.ArrayList;

/**
 * Created by aubry on 3/1/2018.
 */

public class ViewAllRoutesFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    CloudStore store;

    LocalStore localStore;

    ArrayList<Route> routeList = new ArrayList<>(); //For use in recylcer view.

    private RecyclerView mRecyclerView;
    private RouteRecyclerAdapter recyclerAdapter;

    Spinner routeWallSpinner, gymSpinner; //For filtering routes and gyms

    ArrayAdapter<CharSequence> routeWallAdapter;

    private MainActivity activity;

    View view;

    Gym gym;

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
            view = inflater.inflate(R.layout.fragment_view_all_routes, container, false);

            store = new FirebaseCloudStore();

            localStore = new PreferencesLocalStore(activity);

            Bundle bundle = getArguments();

            if(bundle != null && bundle.containsKey("gym")) {
                gym = bundle.getParcelable("gym");
                System.out.println("Verret: gym in ViewALLRoutes is: " + gym.getName());
            }else{
                System.out.println("Verret: Called else branch ViewALLRoutes");
                gym = localStore.getGym();
            }

            initializeRecyclerView("");

            routeWallSpinner = view.findViewById(R.id.filter_route_wall_spinner);
            initializeRouteWallSpinner(routeWallSpinner);
            routeWallSpinner.setOnItemSelectedListener(this);
            gymSpinner = view.findViewById(R.id.filter_gym_spinner);
            initializeGymSpinner(gymSpinner);
            gymSpinner.setOnItemSelectedListener(this);
        }

        return view;
    }

    private void initializeRouteWallSpinner(Spinner spinner){
        //filter_route_wall_spinner
        if(gym != null) {
            if (gym.getName().equals("Planet Granite Sunnyvale")) {
                routeWallAdapter = ArrayAdapter.createFromResource(activity,
                        R.array.route_wall_filter_array, android.R.layout.simple_spinner_item);
            } else if (gym.getName().equals("Earth Treks Golden")) {
                routeWallAdapter = ArrayAdapter.createFromResource(activity,
                        R.array.golden_route_wall_filter_array, android.R.layout.simple_spinner_item);
            } else if (gym.getName().equals("Earth Treks Englewood")) {
                routeWallAdapter = ArrayAdapter.createFromResource(activity,
                        R.array.englewood_route_wall_filter_array, android.R.layout.simple_spinner_item);
            }
        }else{
            routeWallAdapter = ArrayAdapter.createFromResource(activity,
                    R.array.all_wall_filter_array, android.R.layout.simple_spinner_item);
        }
        routeWallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(routeWallAdapter);

    }

    public void initializeGymSpinner(final Spinner spinner){
        final ArrayList<String> gymList = new ArrayList<>();
        store.lookupGyms(new Callback<ArrayList<Gym>>() {
            @Override
            public void receive(ArrayList<Gym> strings) {

                if(!strings.isEmpty()) {
                    for (Gym g : strings) {
                        gymList.add(g.getName());
                    }
                }
                ArrayAdapter<Gym> gymAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, gymList);
                gymAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(gymAdapter);
            }
        });
    }

    private void initializeRecyclerView(final String filterSelection) { //Will need to take into account both gym and wall to determine which routes to add to list
        routeList.clear();
        store.lookUpRoutes(gym, new Callback<ArrayList<Route>>() {
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

                mRecyclerView = view.findViewById(R.id.recyclerView);

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
        if(parent.getItemAtPosition(position) instanceof Gym){
            Gym selection = (Gym) parent.getItemAtPosition(position);
            gym = selection;
            //routeWallAdapter.clear();
            initializeRouteWallSpinner(routeWallSpinner);
            initializeRecyclerView(null);
        }else{
            String selection = (String) parent.getItemAtPosition(position);
            initializeRecyclerView(selection);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

