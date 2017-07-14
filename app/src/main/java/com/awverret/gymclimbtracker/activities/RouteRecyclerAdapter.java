package com.awverret.gymclimbtracker.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.Route;

import java.util.List;

/**
 * Created by aubry on 7/7/17. Recycler adapter for list of routes in main activity.
 */

public class RouteRecyclerAdapter extends RecyclerView.Adapter<RouteRecyclerAdapter.MyViewHolder> {

    private List<Route> routesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView routeName;

        public MyViewHolder(View view) {
            super(view);
            routeName = (TextView) view.findViewById(R.id.route_name);
        }
    }

    public RouteRecyclerAdapter(List<Route> routesList) {
     //   System.out.println("VERRET: Created adapter");
        System.out.println("VERRET: Adapter: routesList: " + routesList);
        this.routesList = routesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_route_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Route route = routesList.get(position);
        System.out.println("VERRET: onBindViewHolder" + route.getName());
        holder.routeName.setText(route.getName());
    }

    @Override
    public int getItemCount() {
        return routesList.size();
    }
}
