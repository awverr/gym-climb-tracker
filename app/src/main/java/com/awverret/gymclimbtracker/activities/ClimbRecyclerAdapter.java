package com.awverret.gymclimbtracker.activities;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.awverret.gymclimbtracker.R;
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

    CloudStore store;
    private Context context;
    private List<Climb> climbsList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView climbName;

        public MyViewHolder(View view) {
            super(view);
            climbName = (TextView) view.findViewById(R.id.climb_name);
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

        String routeName = store.lookupRouteName(climb.getRouteId(), new Callback<String>() {
            @Override
            public void receive(String string) {

            }
        });

        holder.climbName.setText(routeName);


    }

    @Override
    public int getItemCount() {
        return climbsList.size();
    }
}
