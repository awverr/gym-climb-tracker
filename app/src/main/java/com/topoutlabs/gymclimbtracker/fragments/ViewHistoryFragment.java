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

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.activities.ClimbRecyclerAdapter;
import com.topoutlabs.gymclimbtracker.activities.MainActivity;
import com.topoutlabs.gymclimbtracker.model.Climb;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.ArrayList;

public class ViewHistoryFragment extends Fragment {

    CloudStore store;

    User user;

    private RecyclerView mRecyclerView;
    private ClimbRecyclerAdapter recyclerAdapter;

    ArrayList<Climb> climbList = new ArrayList<>(); //For use in recycler view.

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
            view = inflater.inflate(R.layout.activity_view_climbs, container, false);

            Bundle bundle=getArguments();
            if(bundle != null) {

                user = bundle.getParcelable("user");

                store = new FirebaseCloudStore();

                initializeRecyclerView();
            }
        }

        return view;
    }

    private void initializeRecyclerView() {
        store.lookupClimbsForUser(user, new Callback<ArrayList<Climb>>() {
            @Override
            public void receive(ArrayList<Climb> strings) {

                climbList.addAll(strings);
                mRecyclerView = view.findViewById(R.id.climb_recycler_view);
                recyclerAdapter = new ClimbRecyclerAdapter(activity, climbList, user);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}
