package com.awverret.gymclimbtracker.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.fragments.ViewClimbFragment;
import com.awverret.gymclimbtracker.fragments.ViewRouteFragment;
import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.Route;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.FirebaseCloudStore;
import com.awverret.gymclimbtracker.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aubry on 11/3/2017.
 */

public class ClimbRecyclerAdapter extends RecyclerView.Adapter<ClimbRecyclerAdapter.MyViewHolder> {

    private CloudStore store;
    private Context context;
    private List<Climb> climbsList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView climbName;

        public MyViewHolder(View view) {
            super(view);
            climbName = view.findViewById(R.id.climb_name);
        }
    }

    public ClimbRecyclerAdapter(Context context, List<Climb> climbsList) {
        this.context = context;
        this.climbsList = climbsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_climb_item, parent, false);

        return new ClimbRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        store = new FirebaseCloudStore(context);
        final Climb climb = climbsList.get(position);

        store.lookupRouteFromClimb(climb, new Callback<Route>() {
            @Override
            public void receive(final Route route) {
                holder.climbName.setText(route.getName());

                holder.climbName.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {

                        ViewClimbFragment viewClimbFragment = new ViewClimbFragment();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("route", route);
                        bundle.putParcelable("climb", climb);

                        viewClimbFragment.setArguments(bundle);

                        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

                        FragmentTransaction transaction = manager.beginTransaction();

                        transaction.replace(R.id.fragment_container, viewClimbFragment);
                        transaction.addToBackStack(null);

                        transaction.commit();

                    }

                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return climbsList.size();
    }
}
