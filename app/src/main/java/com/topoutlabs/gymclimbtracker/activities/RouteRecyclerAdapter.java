package com.topoutlabs.gymclimbtracker.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.topoutlabs.gymclimbtracker.R;
import com.topoutlabs.gymclimbtracker.fragments.SharedViewModel;
import com.topoutlabs.gymclimbtracker.fragments.ViewRouteFragment;
import com.topoutlabs.gymclimbtracker.model.Climb;
import com.topoutlabs.gymclimbtracker.model.Route;
import com.topoutlabs.gymclimbtracker.model.User;
import com.topoutlabs.gymclimbtracker.store.CloudStore;
import com.topoutlabs.gymclimbtracker.store.FirebaseCloudStore;

import java.util.List;

/**
 * Created by aubry on 7/7/17. Recycler adapter for list of routes in main activity.
 */

public class RouteRecyclerAdapter extends RecyclerView.Adapter<RouteRecyclerAdapter.MyViewHolder> {

    CloudStore store;
    private Context context;
    private List<Route> routesList;
    private User user;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView routeName;
        public Button addToHistoryButton;

        public MyViewHolder(View view) {
            super(view);
            routeName = (TextView) view.findViewById(R.id.route_name);
            addToHistoryButton = (Button) view.findViewById(R.id.add_to_history_button);
        }
    }

    public RouteRecyclerAdapter(List<Route> routesList, User user, Context context) {
     //   System.out.println("VERRET: Created adapter");
        this.routesList = routesList;
        this.user = user;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_route_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(!routesList.isEmpty()) {
            final Route route = routesList.get(position);

            holder.routeName.setText(route.getName());

            holder.routeName.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    ViewRouteFragment viewRouteFragment = new ViewRouteFragment();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("route", route);

                    viewRouteFragment.setArguments(bundle);

                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

                    FragmentTransaction transaction = manager.beginTransaction();

                    transaction.replace(R.id.fragment_container, viewRouteFragment);
                    transaction.addToBackStack(null);

                    transaction.commit();

                    //               Intent intent = new Intent(view.getContext(), ViewRouteActivity.class);
                    //               intent.putExtra("route", route);
                    //               view.getContext().startActivity(intent);


                }

            });

            holder.addToHistoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    store = new FirebaseCloudStore();

                    Climb climb = new Climb(user.getUid(), route.getId());
                    store.saveClimb(climb, user);
                    Toast.makeText(context, "Added to history!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return routesList.size();
    }
}
