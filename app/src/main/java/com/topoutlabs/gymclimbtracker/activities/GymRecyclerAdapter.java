package com.topoutlabs.gymclimbtracker.activities;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.fragments.ViewAllRoutesFragment;
import com.topoutlabs.gymclimbtracker.model.Gym;
import com.topoutlabs.gymclimbtracker.store.LocalStore;

import java.util.List;

public class GymRecyclerAdapter extends RecyclerView.Adapter<GymRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<Gym> gymsList;
    private LocalStore localStore;

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView gymName;

        MyViewHolder(View view) {
            super(view);
            gymName = view.findViewById(R.id.gym_name);
        }
    }

    public GymRecyclerAdapter(List<Gym> gymsList, Context context, LocalStore localStore) {
        this.gymsList = gymsList;
        this.context = context;
        this.localStore = localStore;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_gym_item, parent, false);

        return new GymRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

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
