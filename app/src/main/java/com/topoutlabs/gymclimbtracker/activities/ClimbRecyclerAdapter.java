package com.topoutlabs.gymclimbtracker.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.fragments.ViewClimbFragment;
import com.topoutlabs.gymclimbtracker.model.Climb;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.List;

/**
 * Created by aubry on 11/3/2017.
 */

public class ClimbRecyclerAdapter extends RecyclerView.Adapter<ClimbRecyclerAdapter.MyViewHolder> {

    private User user;
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

    public ClimbRecyclerAdapter(Context context, List<Climb> climbsList, User user) {
        this.context = context;
        this.climbsList = climbsList;
        this.user = user;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_climb_item, parent, false);

        return new ClimbRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        store = new FirebaseCloudStore();

        if(!climbsList.isEmpty()){
        final Climb climb = climbsList.get(position);

        store.lookupRouteFromClimb(climb, new Callback<Route>() {
            @Override
            public void receive(final Route route) {
                holder.climbName.setText(route.getName());

                holder.climbName.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        ViewClimbFragment viewClimbFragment = new ViewClimbFragment();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("route", route);
                        bundle.putParcelable("climb", climb);
                        bundle.putParcelable("user", user);

                        viewClimbFragment.setArguments(bundle);

                        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

                        FragmentTransaction transaction = manager.beginTransaction();

                        transaction.replace(R.id.fragment_container, viewClimbFragment);
                        transaction.addToBackStack(null);

                        transaction.commit();

                    }

                });
            }
        });
    }
    }

    @Override
    public int getItemCount() {
        return climbsList.size();
    }
}
