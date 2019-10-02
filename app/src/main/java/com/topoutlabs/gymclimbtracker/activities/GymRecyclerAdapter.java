package com.topoutlabs.gymclimbtracker.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.fragments.ViewAllRoutesFragment;
import com.topoutlabs.gymclimbtracker.fragments.ViewClimbFragment;
import com.topoutlabs.gymclimbtracker.model.Climb;
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;
import com.topoutlabs.gymclimbtracker.store.LocalStore;
import com.topoutlabs.gymclimbtracker.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class GymRecyclerAdapter extends RecyclerView.Adapter<GymRecyclerAdapter.MyViewHolder> {

    private CloudStore store;
    private Context context;
    private List<Gym> gymsList;
    LocalStore localStore;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView gymName;

        public MyViewHolder(View view) {
            super(view);
            gymName = view.findViewById(R.id.gym_name);
        }
    }

    public GymRecyclerAdapter(List<Gym> gymsList, Context context, LocalStore localStore) {
        this.gymsList = gymsList;
        this.context = context;
        this.localStore = localStore;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_gym_item, parent, false);

        return new GymRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        store = new FirebaseCloudStore();

        if(!gymsList.isEmpty()) {
            final Gym gym = gymsList.get(position);

                    holder.gymName.setText(gym.getName());

                    holder.gymName.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            localStore.setGym(gym);

                            ViewAllRoutesFragment viewRoutesFragment = new ViewAllRoutesFragment();

                            Bundle bundle = new Bundle();
                            bundle.putParcelable("gym", gym);

                            viewRoutesFragment.setArguments(bundle);

                            FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

                            FragmentTransaction transaction = manager.beginTransaction();

                            transaction.replace(R.id.fragment_container, viewRoutesFragment);
                            transaction.addToBackStack(null);

                            transaction.commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return gymsList.size();
    }





}
