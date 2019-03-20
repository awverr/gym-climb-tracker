package com.topoutlabs.gymclimbtracker.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.activities.RouteRecyclerAdapter;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.store.CloudStore;

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
