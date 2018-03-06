package com.awverret.gymclimbtracker.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.activities.MainActivity;
import com.awverret.gymclimbtracker.activities.RouteRecyclerAdapter;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.LocalStore;
import com.awverret.gymclimbtracker.store.PreferencesLocalStore;
import com.awverret.gymclimbtracker.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aubry on 3/1/2018.
 */

public class ViewAllRoutesFragment extends Fragment {

    CloudStore store;

    ArrayList<Route> routeList = new ArrayList<>(); //For use in recylcer view.

    private RecyclerView mRecyclerView;
    private RouteRecyclerAdapter recyclerAdapter;

    private MainActivity activity;

    View view;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(activity == null && context instanceof MainActivity){
            activity = (MainActivity) context;
        }
    }

    LocalStore localStore = new PreferencesLocalStore(activity);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_view_all_routes, container, false);

        initializeRecyclerView();

        return view;
    }

    private void initializeRecyclerView() {
        store.lookUpRoutes(new Callback<ArrayList<Route>>() {
            @Override
            public void receive(ArrayList<Route> strings) {

                for(Route r : strings){
                    //            routes.add(r.getName());
                    routeList.add(r);
                }
                mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                recyclerAdapter = new RouteRecyclerAdapter(routeList, localStore.getUser().get(), activity);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}

