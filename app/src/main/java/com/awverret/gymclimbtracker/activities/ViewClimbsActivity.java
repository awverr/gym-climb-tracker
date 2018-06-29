package com.awverret.gymclimbtracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.awverret.gymclimbtracker.R;
import com.awverret.gymclimbtracker.model.Climb;
import com.awverret.gymclimbtracker.model.User;
import com.awverret.gymclimbtracker.store.CloudStore;
import com.awverret.gymclimbtracker.store.FirebaseCloudStore;
import com.awverret.gymclimbtracker.util.Callback;

import java.util.ArrayList;

public class ViewClimbsActivity extends AppCompatActivity {

    CloudStore store;

    User user; //Need to initialize!!

    private RecyclerView mRecyclerView;
    private ClimbRecyclerAdapter recyclerAdapter;

    ArrayList<Climb> climbList = new ArrayList<>(); //For use in recycler view.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_climbs);

        store = new FirebaseCloudStore(this);

        initializeRecyclerView(this);
    }

    private void initializeRecyclerView(final ViewClimbsActivity view) {
        store.lookupClimbsForUser(user, new Callback<ArrayList<Climb>>() {
            @Override
            public void receive(ArrayList<Climb> strings) {

                for(Climb c : strings){
                    climbList.add(c);
                }
                mRecyclerView = (RecyclerView) findViewById(R.id.climb_recycler_view);
                recyclerAdapter = new ClimbRecyclerAdapter(ViewClimbsActivity.this, climbList, user);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}
