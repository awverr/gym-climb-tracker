package com.topoutlabs.gymclimbtracker.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.model.Climb;
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;

import java.util.List;

public class GymRecyclerAdapter extends RecyclerView.Adapter<GymRecyclerAdapter.MyViewHolder> {

    private CloudStore store;
    private Context context;
    private List<Gym> gymsList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        store = new FirebaseCloudStore();

        if(!gymsList.isEmpty()) {
            final Gym gym = gymsList.get(position);

            holder.gymName.setText(gym.getName());
        }
    }

    @Override
    public int getItemCount() {
        return gymsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView gymName;

        public MyViewHolder(View view) {
            super(view);
            gymName = view.findViewById(R.id.gym_name);
        }
    }

    public GymRecyclerAdapter(List<Gym> gymsList, Context context) {
        this.gymsList = gymsList;
        this.context = context;
    }
}
